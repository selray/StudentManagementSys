package com.example.springboot.entity;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

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
  @ApiModel(value = "Lesson对象", description = "")
public class Lesson implements Serializable {

    private static final long serialVersionUID = 1L;

      private Integer lnumber;

    private String lname;

    private Integer lcredit;

    private Integer lcollege;

    private Integer gradeindex;


}
