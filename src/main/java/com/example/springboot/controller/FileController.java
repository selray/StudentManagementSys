package com.example.springboot.controller;


//文件上传相关接口

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Files;
import com.example.springboot.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${files.upload.path}")//引用application中的路径
    private String fileuploadPAth;

    @Resource
    private FileMapper fileMapper;

    //文件上传接口
    //前端传过来的文件
    //对于重复
    @PostMapping("/upload")
    public String  upload(@RequestParam MultipartFile file) throws IOException {
        String orginalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(orginalFilename);
        long size = file.getSize();

        //判断配置文件目录是否存在，若不存在则创建一个新的文件目录

        //定义一个文件唯一的标识码
        String uuid = IdUtil.fastSimpleUUID();
        String fileUUid = uuid + StrUtil.DOT +type;
        File uploadFile = new File(fileuploadPAth + fileUUid);

        File parentFile = uploadFile.getParentFile();
        if(!parentFile.exists()){
            parentFile.mkdirs();
        }

        //实现：对于相同内容不同文件名的文件，因为md5一样，在数据库中每个有一个记录，但是在磁盘中，只会存在一个最新的文件
        String url;
        String md5;
        //上传文件到磁盘
        file.transferTo(uploadFile);
        //获取文件的md5
        md5 = SecureUtil.md5(uploadFile);
        //数据库查询是否存在相同的记录
        Files dbFiles = getFileByMd5(md5);
        if (dbFiles != null){
            url = dbFiles.getUrl();
            //删除之前已存在的重复文件，以便于上传最新版文件
            uploadFile.delete();
        }else {
            //数据库不存在重复的文件
            //把获取到的文件存储到磁盘目录
            url = "http://124.71.166.37:9090/file/"+fileUUid;
        }
        //获取文件url
        //把获取到的文件存储到磁盘目录中

        //文件路径
        //存储数据库
        Files saveFile = new Files();
        saveFile.setName(orginalFilename);
        saveFile.setType(type);
        saveFile.setSize(size/1024); //最后存储单位是kb
        saveFile.setUrl(url);
        saveFile.setMd5(md5);
        fileMapper.insert(saveFile);
        return url; //文件下载链接
        //上传成功后返回url
    }

    //文件下载接口：http://124.71.166.37:9090/file/{fileUUid}
    //下载接口
    @GetMapping("/{fileUUid}")
    public void download(@PathVariable String fileUUid , HttpServletResponse response) throws IOException {

        //根据文件的唯一标识码获取文件
        File uploadFile = new File(fileuploadPAth + fileUUid);
        //文件上传路径
        //设置输出流格式
        ServletOutputStream os =response.getOutputStream();
        response.addHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileUUid,"UTF-8"));
        response.setContentType("application/octet-stream");


        //读取文件的字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }

    //通过文件的md5查询文件
    private Files getFileByMd5(String md5){
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5",md5);
        List<Files> filesList = fileMapper.selectList(queryWrapper);
        //获取第一个，因为有可能重名，多条记录
        return filesList.size() == 0 ? null: filesList.get(0);
    }


    @PostMapping("/update")
    public Result save(@RequestBody Files files){
        //新增或者更新
        return Result.success(fileMapper.updateById(files));
    }
    @DeleteMapping("/{id}")
    public Result  delete(@PathVariable Integer id){
        Files files = fileMapper.selectById(id);
        files.setIsDelete(true);
        fileMapper.updateById(files);
        return Result.success();
    }
    //    批量删除接口
    @PostMapping("/del/batch")
    public Result  deleteBatch(@RequestBody List<Integer> ids){
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",ids);
        List<Files> files = fileMapper.selectList(queryWrapper);
        for(Files file : files){
            file.setIsDelete(true);
            fileMapper.updateById(file);
        }
        return Result.success();
    }



    //分页查询
    @GetMapping("/page")  //接口路径,多条件查询
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name){
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        //查询未删除的记录
        queryWrapper.eq("is_delete",false);
        queryWrapper.orderByDesc("id");
        if(!"".equals(name)){
            queryWrapper.like("name",name);
        }
        return Result.success(fileMapper.selectPage(new Page<>(pageNum,pageSize),queryWrapper));
    }
}
