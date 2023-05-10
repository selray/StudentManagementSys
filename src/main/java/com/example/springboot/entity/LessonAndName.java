package com.example.springboot.entity;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
@Data
public class LessonAndName {
    @TableId(type = IdType.AUTO)
    private Integer lnumber;

    private String lname;

    private Integer lcredit;

    private Integer lcollege;

    private String deptname;
    public LessonAndName(Lesson lesson,String deptname)
    {
        this.lnumber=lesson.getLnumber();
        this.lname=lesson.getLname();
        this.lcollege=lesson.getLcollege();
        this.lcredit=lesson.getLcredit();
        this.deptname=deptname;
    }

}
