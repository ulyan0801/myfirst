package com.my.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.my.springboot.empty.User;
import com.my.springboot.empty.UserPagetQueryAgs;
import com.my.springboot.security.UserDetailsBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


public interface UserService extends IService<User> {
    ResponseEntity register(User user, HttpServletRequest request);

    UserDetailsBean findUserDetailsBeanByName(String username);

    ResponseEntity findPwd(User user);

    User findUserByName(String username);

    ResponseEntity updateFields(User user);

    ResponseEntity updateUser(String username, int sex, String avatar, int userStatus, String userNickname, MultipartFile fileName);

    ResponseEntity getAllUser(UserPagetQueryAgs userPagetQueryAgs);

    ResponseEntity getUserRoles();

    ResponseEntity getUserInfo(HttpServletRequest request);

    ResponseEntity IsRegister(String username);

    ResponseEntity updateAvatar(MultipartFile file);

    ResponseEntity modifyPwd(String username, String oldpwd, String newpwd);

    ResponseEntity getUserByUserName(String username);
}
