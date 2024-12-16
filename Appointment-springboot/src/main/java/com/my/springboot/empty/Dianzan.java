package com.my.springboot.empty;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName dianzan
 */
@TableName(value = "dianzan")
@Data
public class Dianzan implements Serializable {
    private Long id;

    private String uname;

    private Long tid;

    private static final long serialVersionUID = 1L;
}