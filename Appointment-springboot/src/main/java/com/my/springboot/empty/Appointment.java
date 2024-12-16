package com.my.springboot.empty;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName gymnasium
 */
@TableName(value = "appointment")
@Data
public class Appointment implements Serializable {
    private Long id;

    private String leixing;


    private String content;

    private Long shijian;

    private String xingqi;


    private String qita;

    private String shipin;

    private String leixingf;
    private String xingming;


    private static final long serialVersionUID = 1L;
}