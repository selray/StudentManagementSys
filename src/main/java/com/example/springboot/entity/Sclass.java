package com.example.springboot.entity;

import java.io.Serializable;

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
 * @since 2023-04-05
 */
@Getter
@Setter
  @ApiModel(value = "Sclass对象", description = "")
public class Sclass implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
      private Integer tnumber;

      private Integer lnumber;

      private String semester;

    private String lessontime;

    private String classroom;

    private Integer maxsize;

    private Integer currentsize;

}
