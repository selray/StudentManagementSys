package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.springboot.config.interceptor.MD5TypeHandler;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.Serializable;
import java.time.LocalDate;

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

    @TableField(exist = false)
    private String departName;

    private String status;

    private String logn;
    private String avatarurl;
// @TableField(value = "pswd", typeHandler = MD5TypeHandler.class)
    private String pswd;
    private String Sprofession;

    public void setPassword(String pswd) {
        this.pswd = DigestUtils.md5Hex(pswd); // 使用 Apache Commons Codec 提供的 DigestUtils 类进行 MD5 加密
    }

}
