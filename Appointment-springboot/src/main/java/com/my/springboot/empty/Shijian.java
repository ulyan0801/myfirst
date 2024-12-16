package com.my.springboot.empty;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName shijian
 */
@TableName(value = "shijian")
@Data
public class Shijian implements Serializable {
    private Long id;

    private Long tid;

    private Integer bianhao;

    private String jiage;

    private String yuyueshijian;
    private Integer renshu;

    private static final long serialVersionUID = 1L;
    @TableField(exist = false)
    private Integer shengyurenshu;
}