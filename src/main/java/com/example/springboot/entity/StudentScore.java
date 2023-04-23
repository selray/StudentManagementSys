package com.example.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentScore {
    private Integer snumber;

    private String sname;       //

    private Integer lnumber;
    private String lname;      //课程名
    private Integer tnumber;

    private Integer psgrade;

    private Integer ksgrade;

    private Integer totalgrade;

    private Float gpa;

    /**
     * 90～100 A 4.0
     *
     * 85～89.9 A- 3.7
     *
     * 82～84.9 B+ 3.3
     *
     * 78～81.9 B 3.0
     *
     * 75～77.9 B- 2.7
     *
     * 72～74.9 C+ 2.3
     *
     * 68～71.9 C 2.0
     *
     * 66～67.9 C- 1.7
     *
     * 64～65.9  D 1.5
     *
     * 60～63.9 D- 1.0
     *
     * ＜60 F 0
     */

    private String semester;

    private Integer gradeindex;    //平时分考试分成绩比

    public StudentScore(Lessonchoose lessonchoose,String sname,String lname,Integer gradeindex)
    {
        this.snumber=lessonchoose.getSnumber();
        this.sname=sname;
        this.lnumber=lessonchoose.getLnumber();
        this.lname=lname;
        this.tnumber=lessonchoose.getTnumber();
        this.psgrade=lessonchoose.getPsgrade();
        this.ksgrade=lessonchoose.getKsgrade();
        this.totalgrade=lessonchoose.getTotalgrade();
        this.gpa=lessonchoose.getGpa();
        this.semester=lessonchoose.getSemester();
        this.gradeindex=gradeindex;
    }


}
