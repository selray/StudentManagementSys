package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author sel
 * @since 2023-04-02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Lessonchoose对象", description = "")
public class Lessonchoose implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(type = IdType.AUTO)
      private Integer snumber;

      private Integer lnumber;

      private Integer tnumber;

    private Integer psgrade;

    private Integer ksgrade;

    private Integer totalgrade;

    private Float gpa;

      private String semester;


}
