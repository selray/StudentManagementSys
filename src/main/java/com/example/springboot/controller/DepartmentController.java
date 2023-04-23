package com.example.springboot.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.Student;
import com.example.springboot.entity.department;
import com.example.springboot.service.DepartmentService;
import com.example.springboot.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/department")
//初始url,要和下面拼接形成完整url
public class DepartmentController {

//    @Value("${files.upload.path}")
//    private String filesUploadPath;

    @Resource
    private DepartmentService depService;

//登录
//    @PostMapping("/login")
//    public boolean login(@RequestBody UserDTO userDTO){
//        //新增或者更新
//        String username = userDTO.getUsername();
//        String password = userDTO.getPassword();
//        if(StrUtil.isBlank(username) || StrUtil.isBlank(password)){
//            //校验字符串是否是空
//            return  false;
//        }
//        return depService.login(userDTO);
//    }

    //增加
    @PostMapping
    public boolean save(@RequestBody department dep){
        //新增或者更新
        return depService.saveOrUpdate(dep);
    }
    //    查询所有数据
    @GetMapping
    public List<department> findAll(){
        return depService.list();
    }
    //    删除
    @GetMapping("/{id}")
    public List<department> findOne(@PathVariable Integer id){
        return depService.list();
    }
    @DeleteMapping("/{id}")
    public boolean  delete(@PathVariable Integer id){
        return depService.removeById(id);
    }
//    批量删除接口
    @PostMapping("/del/batch")
    public boolean  deleteBatch(@RequestBody List<Integer> ids){
        return depService.removeByIds(ids);
    }

    //分页查询
//    @GetMapping("/page")  //接口路径
//    public Map<String, Object> findPage(@RequestParam Integer pageNum,
//                                        @RequestParam Integer pageSize,
//                                        @RequestParam String deptname){
//        pageNum = (pageNum -1 ) * pageSize;
//        deptname = "%" + deptname + "%";
//        List<department> data = dep_matter.selectPage(pageNum,pageSize,deptname);
//        Integer total = dep_matter.selectTotal(deptname);
//        Map<String,Object> res = new HashMap<>();
//        res.put("data", data);
//        res.put("total", total);
//        return res;
//    }
    //mybatis plus分页查询
    @GetMapping("/page")  //接口路径,多条件查询
    public IPage<department> findPage(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                       @RequestParam(defaultValue = "") String deptid,
                                        @RequestParam(defaultValue = "") String deptname,
                                       @RequestParam(defaultValue = "") String address,
                                       @RequestParam(defaultValue = "") String phonecode){
        IPage<department> page = new Page<>(pageNum,pageSize);
        QueryWrapper<department> queryWrapper = new QueryWrapper<>();
        if(!"".equals(deptid)){
            queryWrapper.like("deptid",deptid);
        }
        if(!"".equals(deptname)){
            queryWrapper.like("deptname",deptname);
        }
        if(!"".equals(address)){
            queryWrapper.like("address",address);
        }
        if(!"".equals(phonecode)){
            queryWrapper.like("phonecode",phonecode);
        }
        //倒序
        //queryWrapper.orderByDesc("id");

        //测试利用token后台获取用户信息,
        Student currentUser = TokenUtils.getCurrentUser();
        //System.out.println("测试获取当前用户信息----------------------"+currentUser.getNativeplace());


        return depService.page(page,queryWrapper);
    }



//    导出接口
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
        List<department> list = depService.list();
        //从数据库中查询出所有数据
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //在内存操作，写出到浏览器

        //自定义标签别名
//        writer.addHeaderAlias("deptid","学院号");
//        writer.addHeaderAlias("deptname","学院名称");
//        writer.addHeaderAlias("address","学院地址");
//        writer.addHeaderAlias("phonecode","学院号");

        //一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list,true);

        //设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName= URLEncoder.encode("学院表格信息","UTF-8");
        //输出文件名称
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out,true);
        out.close();
        writer.close();
    }


//    导入
    @PostMapping("/import")
    public Boolean imp(MultipartFile file) throws Exception{
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        //通过javabin的方式读取javabin内的对象，但是要求表头必须是中文，且和javabean中的属性要对应起来
        List<department> list = reader.readAll(department.class);
        System.out.println(list);


        //插入到数据库中
        depService.saveBatch(list);
        return true;
    }
}

