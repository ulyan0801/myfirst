package com.my.springboot.empty;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName recover
 */
@TableName(value = "recover")
@Data
public class Recover implements Serializable {
    private Long id;

    private Long pid;

    private String time;

    private String name;

    private String content;
    @TableField(exist = false)
    private String userNickname;
    @TableField(exist = false)
    private String avatar;
    private static final long serialVersionUID = 1L;
}