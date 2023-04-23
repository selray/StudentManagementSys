package com.example.springboot.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.*;
import com.example.springboot.mapper.LessonMapper;
import com.example.springboot.mapper.LessonchooseMapper;
import com.example.springboot.mapper.SclassMapper;
import com.example.springboot.service.ILessonService;
import com.example.springboot.service.ILessonchooseService;
import com.example.springboot.service.ISclassService;
import com.example.springboot.service.IStudentService;
import com.example.springboot.utils.TokenUtils;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sel
 * @since 2023-04-02
 */
@RestController
@RequestMapping("/lessonchoose")
public class LessonchooseController {


    @Resource
    private ILessonchooseService lessonchooseService;
    @Resource
    private LessonchooseMapper lessonchooseMapper;

    @Resource
    private SclassMapper sclassMapper;
    @Resource
    private ISclassService sclassService;

    @Resource
    private ILessonService lessonService;
    @Resource
    private LessonMapper lessonMapper;
    @Resource
    private IStudentService studentService;

    //新增或更新
    @PostMapping
    public boolean save(@RequestBody StudentScore s){//@RequestBody将前台josn对象转换为后台的java对象
        //新增或者更新
        UpdateWrapper<Lessonchoose> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("snumber",s.getSnumber());
        updateWrapper.eq("lnumber",s.getLnumber());
        updateWrapper.eq("tnumber",s.getTnumber());

        if(s.getPsgrade()!=null&&s.getKsgrade()!=null){
            float psindex=(float)(s.getGradeindex()/10)/10;    //平时分占比，如0.4
            float ksindex=(float)(s.getGradeindex()%10)/10;    //考试分占比，如0.6
            s.setTotalgrade((int)(s.getPsgrade()*psindex+s.getKsgrade()*ksindex));
            if(s.getTotalgrade()>=90&&s.getTotalgrade()<=100)
            {
                s.setGpa(4.0F);
            } else if (s.getTotalgrade() >= 85 && s.getTotalgrade() <= 89) {
                s.setGpa(3.7F);
            } else if (s.getTotalgrade() >= 82 && s.getTotalgrade() <= 84) {
                s.setGpa(3.3F);
            } else if (s.getTotalgrade() >= 78 && s.getTotalgrade() <= 81) {
                s.setGpa(3.0F);
            } else if (s.getTotalgrade() >= 75 && s.getTotalgrade() <= 77) {
                s.setGpa(2.7F);
            } else if (s.getTotalgrade() >= 72 && s.getTotalgrade() <= 74) {
                s.setGpa(2.3F);
            } else if (s.getTotalgrade() >= 68 && s.getTotalgrade() <= 71) {
                s.setGpa(2.0F);
            }else if (s.getTotalgrade() >= 66 && s.getTotalgrade() <= 67) {
                s.setGpa(1.7F);
            }else if (s.getTotalgrade() >= 64 && s.getTotalgrade() <= 65) {
                s.setGpa(1.5F);
            }else if (s.getTotalgrade() >= 60 && s.getTotalgrade() <= 63) {
                s.setGpa(1.0F);
            }else if (s.getTotalgrade() < 60) {
                s.setGpa((float) 0);
            }
        }

        Lessonchoose lessonchoose=new Lessonchoose(s.getSnumber(),s.getLnumber(),s.getTnumber(),s.getPsgrade(),s.getKsgrade(),s.getTotalgrade(),s.getGpa(),s.getSemester());
        return lessonchooseService.saveOrUpdate(lessonchoose,updateWrapper);
    }

    @GetMapping("/xk")
    public boolean chooseClass(@RequestParam(defaultValue = "") Integer lnumber,
                               @RequestParam(defaultValue = "") Integer tnumber,
                               @RequestParam(defaultValue = "") String semester,
                               @RequestParam(defaultValue = "") Integer snumber){
        System.out.println("========后台接收到传入信息："+lnumber+tnumber+semester+snumber);
        QueryWrapper<Lessonchoose> queryWrapper = new QueryWrapper<>();
        if(!"".equals(lnumber)){
            queryWrapper.eq("lnumber",lnumber);
        }
        if(!"".equals(tnumber)){
            queryWrapper.eq("tnumber",tnumber);
        }
        if(!"".equals(semester)){
            queryWrapper.eq("semester",semester);
        }
        if(!"".equals(snumber)){
            queryWrapper.eq("snumber",snumber);
        }
        //判断查询是否为空
        //判断这个课程是否在本学期已经选修过了，选修过就不能再次选修


        Page Page=new Page(1,1);
        IPage iPage=lessonchooseMapper.selectPage(Page,queryWrapper);
        List row = iPage.getRecords();
//判断查询结果是否为空
        if(row== null || row.size() ==0){
            //执行选课成功，在lessonchoose表中执行插入
            Lessonchoose lessonchoose = new Lessonchoose();
            lessonchoose.setLnumber(lnumber);
            lessonchoose.setTnumber(tnumber);
            lessonchoose.setSemester(semester);
            lessonchoose.setSnumber(Integer.valueOf(snumber));



            //在class表中添加人数
            QueryWrapper<Sclass> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("tnumber",tnumber);
            //queryWrapper2.eq("snumber",snumber);
            queryWrapper2.eq("lnumber",lnumber);
            queryWrapper2.eq("semester",semester);
            //临时存储原先的数据
            Sclass sclass = sclassMapper.selectOne(queryWrapper2);


            //判断当前人数是否满额
            if(sclass.getCurrentsize()>=sclass.getMaxsize()){
                return false;
            }
            //删除旧的课表信息
            sclassMapper.delete(queryWrapper2);
            sclass.setCurrentsize(sclass.getCurrentsize()+1);
            //人数加一
            sclassService.save(sclass);
            //在lessonchoose表中添加记录
            lessonchooseService.save(lessonchoose);
            return true;
        }else{
            return false;
        }


        //lessonchooseService.saveOrUpdate(lessonchoose);
    }
    @GetMapping("/tk")
    public boolean CancelClass(@RequestParam(defaultValue = "") Integer lnumber,
                               @RequestParam(defaultValue = "") Integer tnumber,
                               @RequestParam(defaultValue = "") String semester,
                               @RequestParam(defaultValue = "") Integer snumber){
        System.out.println("========后台接收到传入信息："+lnumber+tnumber+semester+snumber);
        QueryWrapper<Lessonchoose> queryWrapper = new QueryWrapper<>();
        if(!"".equals(lnumber)){
            queryWrapper.eq("lnumber",lnumber);
        }
        if(!"".equals(tnumber)){
            queryWrapper.eq("tnumber",tnumber);
        }
        if(!"".equals(semester)){
            queryWrapper.eq("semester",semester);
        }
        if(!"".equals(snumber)){
            queryWrapper.eq("snumber",snumber);
        }
        //判断查询是否为空
        Page Page=new Page(1,1);
        IPage iPage=lessonchooseMapper.selectPage(Page,queryWrapper);
        List row = iPage.getRecords();
        //判断查询结果是否为空
        if(row== null || row.size() ==0){
            //没有查到记录，也就没法退课
            return false;
        }else{
            //执行退课操作
            Lessonchoose lessonchoose = new Lessonchoose();
            lessonchoose.setLnumber(lnumber);
            lessonchoose.setTnumber(tnumber);
            lessonchoose.setSemester(semester);
            lessonchoose.setSnumber(Integer.valueOf(snumber));



            //在class表中减少人数
            QueryWrapper<Sclass> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("tnumber",tnumber);
            //queryWrapper2.eq("snumber",snumber);
            queryWrapper2.eq("lnumber",lnumber);
            queryWrapper2.eq("semester",semester);
            //临时存储原先的数据
            Sclass sclass = sclassMapper.selectOne(queryWrapper2);


            //判断当前人数是否为0
            if(sclass.getCurrentsize()<=0){
                return false;
            }
            //删除旧的课表信息
            sclassMapper.delete(queryWrapper2);
            sclass.setCurrentsize(sclass.getCurrentsize()-1);
            //人数加一
            sclassService.save(sclass);
            //在lessonchoose表中添加记录
            lessonchooseMapper.delete(queryWrapper);
            //lessonchooseService.save(lessonchoose);
            return true;

        }


        //lessonchooseService.saveOrUpdate(lessonchoose);
    }
    //删除
    @DeleteMapping("/{id}")
    public boolean  delete(@PathVariable Integer id){
        return lessonchooseService.removeById(id);
    }


    @PostMapping("/del/batch")
    public boolean  deleteBatch(@RequestBody List<Integer> ids){
        return lessonchooseService.removeByIds(ids);
    }


    //查询所有
    @GetMapping
    public List<Lessonchoose> findAll(){
        return lessonchooseService.list();
    }

    //根据id查询
    @GetMapping("/{id}")
    public Lessonchoose findOne(@PathVariable Integer id){
        return lessonchooseService.getById(id);
    }

    //分页查询
    @GetMapping("/page")
    public IPage<StudentScore> findPage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam (defaultValue = "")String snumber,
                                       @RequestParam (defaultValue = "")String sname,
                                       @RequestParam (defaultValue = "")String lnumber,
                                       @RequestParam (defaultValue = "")String lname,
                                       @RequestParam(defaultValue = "") String semester){
        QueryWrapper<Lessonchoose> queryWrapper= new QueryWrapper<>();
        if(!"".equals(snumber)){
            queryWrapper.like("snumber",snumber);
        }
        if(!"".equals(sname)){
            QueryWrapper<Student> studentQueryWrapper=new QueryWrapper<>();
            if("".equals(snumber)){     //如果学号参数为空进行覆盖
                studentQueryWrapper.like("name",sname);
                Student student=studentService.getOne(studentQueryWrapper);
                //找到学生名对应的学号
                queryWrapper.like("snumber",student.getStudentid());
            }
        }
        if(!"".equals(lnumber)){
            queryWrapper.like("lnumber",lnumber);
        }
        if(!"".equals(lname)){
            QueryWrapper<Lesson> lessonQueryWrapper = new QueryWrapper<>();
            if("".equals(lnumber)){//如果参数课程号为空，覆盖课程号，否则不覆盖
                lessonQueryWrapper.like("lname",lname);
                Lesson lesson=lessonService.getOne(lessonQueryWrapper);
                //找到某一个课程名对应的课程号
                queryWrapper.like("lnumber",lesson.getLnumber());
            }
        }
        if(!"".equals(semester)){
            queryWrapper.like("semester",semester);
        }
        queryWrapper.orderByAsc("lnumber");
        //根据当前老师登录信息查询该老师名下选课学生，必有的
        Teacher currentTeacher= TokenUtils.getCurrentTeacher();
        queryWrapper.like("tnumber", currentTeacher != null ? currentTeacher.getTnumber() : "");
        //根据条件构造器去查询
        List<Lessonchoose> lessonchooses=lessonchooseService.list(queryWrapper);

        List<StudentScore> result = new ArrayList<>();
        Lesson lesson;
        Student student;
        //随后，遍历查找到的数据，针对每一行，也就是每一个课程找到课程的课程名称和课程学分还有学生的姓名
        for(Lessonchoose o:lessonchooses){
            //根据课号查询课名、分数比等
            QueryWrapper<Lesson> lessonQueryWrapper = new QueryWrapper<>();
            lessonQueryWrapper.eq("lnumber",o.getLnumber());
            lesson=lessonService.getOne(lessonQueryWrapper);

            //根据学生号查询姓名
            QueryWrapper<Student> studentQueryWrapper=new QueryWrapper<>();
            studentQueryWrapper.eq("studentid",o.getSnumber());
            student=studentService.getOne(studentQueryWrapper);

            StudentScore studentScore=new StudentScore(o,student.getName(),lesson.getLname(),lesson.getGradeindex());

            result.add(studentScore);
        }

        IPage<StudentScore> page;
        page=listToPage(result,pageNum,pageSize);

        return page;
    }



    public static IPage listToPage(List list, int pageNum, int pageSize){
        List pageList = new ArrayList<>();
        int curIdx = pageNum > 1 ? (pageNum - 1) * pageSize : 0;
        for (int i = 0; i < pageSize && curIdx + i < list.size(); i++) {
            pageList.add(list.get(curIdx + i));
        }
        IPage page = new Page<>(pageNum, pageSize);
        page.setRecords(pageList);
        page.setTotal(list.size());
        return page;
    }

    /**
     * 导出接口
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response, @RequestParam("tableData") String tableDataStr) throws Exception{
        //Gson 对象用于将 JSON 字符串转换为 Java 对象
        List<StudentScore> tableData = new Gson().fromJson(tableDataStr, new TypeToken<List<StudentScore>>() {}.getType());

        QueryWrapper<Lessonchoose> queryWrapper= new QueryWrapper<>();

        //找到对应课程号
        queryWrapper.like("lnumber",tableData.get(0).getLnumber());

        queryWrapper.orderByAsc("lnumber");
        //根据当前老师登录信息查询该老师名下选课学生，必有的
        queryWrapper.like("tnumber", tableData.get(0).getTnumber());
        //根据条件构造器去查询
        List<Lessonchoose> lessonchooses=lessonchooseService.list(queryWrapper);
        List<StudentScore> result = new ArrayList<>();            //要导出的列表
        Lesson lesson;
        Student student;
        //随后，遍历查找到的数据，针对每一行，也就是每一个课程找到课程的课程名称和课程学分还有学生的姓名
        for(Lessonchoose o:lessonchooses){
            //根据课号查询课名、分数比等
            QueryWrapper<Lesson> lessonQueryWrapper = new QueryWrapper<>();
            lessonQueryWrapper.eq("lnumber",o.getLnumber());
            lesson=lessonService.getOne(lessonQueryWrapper);

            //根据学生号查询姓名
            QueryWrapper<Student> studentQueryWrapper=new QueryWrapper<>();
            studentQueryWrapper.eq("studentid",o.getSnumber());
            student=studentService.getOne(studentQueryWrapper);

            StudentScore studentScore=new StudentScore(o,student.getName(),lesson.getLname(),lesson.getGradeindex());

            result.add(studentScore);
        }

        //通过工具类创建writer 写出到磁盘路径
        //ExcelWriter writer= ExcelUtil.getWriter(fileUploadPath + "/用户信息.xlsx");
        //在内存操作，写出到浏览器
        ExcelWriter writer= ExcelUtil.getWriter(true);
        //自定义标题列名
//        writer.addHeaderAlias("deptid","学院号");
//        writer.addHeaderAlias("deptname","学院名");
//        writer.addHeaderAlias("address","地址");
//        writer.addHeaderAlias("phonecode","电话号码");

        //一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(result,true);

        //设置浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName= URLEncoder.encode("学生成绩","UTF-8");
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");

        ServletOutputStream out= response.getOutputStream();
        writer.flush(out,true);
        out.close();
        writer.close();
    }

}

