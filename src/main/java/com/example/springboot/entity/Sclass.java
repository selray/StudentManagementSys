package com.example.springboot.entity;

import java.io.Serializable;
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
 * @since 2023-04-04
 */
@Getter
@Setter
  @ApiModel(value = "Sclass对象", description = "")
public class Sclass implements Serializable {

    private static final long serialVersionUID = 1L;

      private Integer tnumber;

      private Integer lnumber;

      private String semester;

    private String lessontime;

    private String classroom;

    private Integer maxsize;


}
