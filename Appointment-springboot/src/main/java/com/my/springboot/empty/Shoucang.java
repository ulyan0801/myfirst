package com.my.springboot.empty;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName shoucang
 */
@TableName(value = "shoucang")
@Data
public class Shoucang implements Serializable {
    private Long id;

    private String uname;

    private Long tid;

    private static final long serialVersionUID = 1L;
}