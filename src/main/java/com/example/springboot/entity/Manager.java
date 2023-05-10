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
  @ApiModel(value = "Manager对象", description = "")
public class Manager implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
      private String mnumber;

    private String musername;
//    @TableField(value = "mpassword", typeHandler = MD5TypeHandler.class)
    private String mpassword;

    private String mname;
    private String avatarurl;

    public void setPassword(String mpassword) {
        this.mpassword = DigestUtils.md5Hex(mpassword); // 使用 Apache Commons Codec 提供的 DigestUtils 类进行 MD5 加密
    }
}
