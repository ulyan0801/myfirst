package com.my.springboot.empty;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName yuyue
 */
@TableName(value = "yuyue")
@Data
public class Yuyue implements Serializable {
    private Long id;

    private Long tid;

    private Long shijianid;

    private String username;

    private String riqi;
    private Integer haoma;

    private static final long serialVersionUID = 1L;
}