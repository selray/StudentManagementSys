package com.example.springboot.entity;

import java.io.Serializable;
import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author sel
 * @since 2023-03-29
 */
@Getter
@Setter
  @ApiModel(value = "student", description = "")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
      private String studentid;

    private String name;

    private String sex;

    private Integer age;

    private LocalDate dataofbirth;

    private String nativeplace;

    private String mobilephone;

    private String deptid;

    private String sdept;

    private String status;

    private String logn;

    private String pswd;


}
