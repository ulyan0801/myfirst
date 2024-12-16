//package com.my.springboot.empty;
//
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableField;
//import com.baomidou.mybatisplus.annotation.TableId;
//import com.baomidou.mybatisplus.annotation.TableName;
//import java.io.Serializable;
//import java.math.BigDecimal;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.Data;
//
///**
// * @TableName ordinary
// */
//@TableName(value ="user")
//@Data
//public class User implements Serializable {
//    private Long id;
//
//    private String username;
//@JsonIgnore
//    private String password;
//
//    private Integer sex;
//
//    private String birthday;
//   
//    private String lastLoginTime;
//
//    private Integer score;
//
//    private Integer coin;
//
//    private BigDecimal balance;
//   
//    private String createTime;
//
//    private Integer userStatus;
//
//    private String userNickname;
//
//    private String avatar;
//
//    private String addr;
//   
//    private String lastLoginIp;
//
//    private String more;
//
//    private static final long serialVersionUID = 1L;
//}

package com.my.springboot.empty;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @TableName ordinary
 */
@TableName(value = "user")
@Data
public class User implements Serializable {
    private Long id;

    private String username;

    private String password;

    private Integer sex;

    private String birthday;

    private String lastLoginTime;

    private Integer score;

    private Integer coin;

    private BigDecimal balance;

    private String createTime;

    private Integer userStatus;

    private String userNickname;

    private String avatar;

    private String addr;

    private String lastLoginIp;

    private String more;

    private Integer guanli;
    private String phone;

    private static final long serialVersionUID = 1L;
}