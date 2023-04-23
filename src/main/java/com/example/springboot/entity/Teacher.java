package com.example.springboot.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 
 * </p>
 *
 * @author sel
 * @since 2023-03-31
 */
@Getter
@Setter
  @ApiModel(value = "Teacher对象", description = "")
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
      private Integer tnumber;

    @Alias("name")
    private String tname;

    private String tsex;

    private LocalDate tbirthday;

    private String tstatus;

    private String twage;

    private Integer tcollege;

    @Alias("username")       //设置别名,和userDTO对应
    private String tusername;

    @Alias("password")
    private String tpassword;

    private String avatarurl;
}
