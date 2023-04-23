package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class SclassAndName {
    @TableId(type = IdType.AUTO)
    private Integer tnumber;

    private Integer lnumber;

    private String semester;

    private String lessontime;

    private String classroom;

    private Integer maxsize;

    private Integer currentsize;
    private String classname;

    private Integer lcredit;

        public SclassAndName(Sclass sclass, String classname, Integer lcredit){
            this.tnumber = sclass.getTnumber();
            this.lnumber = sclass.getLnumber();
            this.semester = sclass.getSemester();
            this.lessontime = sclass.getLessontime();
            this.classroom = sclass.getClassroom();
            this.maxsize = sclass.getMaxsize();
            this.currentsize = sclass.getCurrentsize();
            this.lcredit = lcredit;
            this.classname = classname;
    }
}
