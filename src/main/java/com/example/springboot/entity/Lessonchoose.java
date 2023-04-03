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
 * @since 2023-04-02
 */
@Getter
@Setter
  @ApiModel(value = "Lessonchoose对象", description = "")
public class Lessonchoose implements Serializable {

    private static final long serialVersionUID = 1L;

      private Integer snumber;

      private Integer lnumber;

      private Integer tnumber;

    private Integer psgrade;

    private Integer ksgrade;

    private Integer totalgrade;

    private Float gpa;

      private String semester;


}
