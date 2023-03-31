package com.example.springboot.entity;

import java.io.Serializable;
import java.time.LocalDate;
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
 * @since 2023-03-31
 */
@Getter
@Setter
  @ApiModel(value = "Teacher对象", description = "")
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

      private Integer tnumber;

    private String tname;

    private String tsex;

    private LocalDate tbirthday;

    private String tstatus;

    private String twage;

    private Integer tcollege;

    private String tusername;

    private String tpassword;


}
