package com.my.springboot.controller;

import com.my.springboot.empty.User;
import com.my.springboot.empty.UserPagetQueryAgs;
import com.my.springboot.empty.UserRegisterCountByCreateTime;
import com.my.springboot.empty.UserSexCountBySex;
import com.my.springboot.service.impl.UserServiceImpl;
import com.my.springboot.utils.Result.ResponseJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@Api(tags = "用户接口")
public class UserController {
    @Autowired
    UserServiceImpl userService;
    //静态资源目录
    @Value("${windows.resources.path}")
    private String windowsResourcesPath;
    //静态资源目录
    @Value("${linux.resources.path}")
    private String linuxResourcesPath;

    @ApiOperation("注册")
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody User user, HttpServletRequest request) {
        return userService.register(user, request);
    }

    @ApiOperation("找回密码")
    @PostMapping("/findpwd")
    public ResponseEntity findPwd(@Validated User user) {
        return userService.findPwd(user);
    }


    @ApiOperation("修改密码")
    @PostMapping("/modifypwd")
    public ResponseEntity modifyPwd(@RequestParam("username") String username, @RequestParam("oldpassword") String oldpassword, @RequestParam("newpassword") String newpassword) {

        return userService.modifyPwd(username, oldpassword, newpassword);
    }

    @ApiOperation("用户获取自己的个人信息")
    @PostMapping("/userinfo")
    public ResponseEntity getUserInfo(HttpServletRequest request) {

        return userService.getUserInfo(request);
    }

    @ApiOperation("用户获取自己的个人信息")
    @PostMapping("/getUserByUserName")
    public ResponseEntity getUserByUserName(@RequestParam("username") String username) {

        return userService.getUserByUserName(username);
    }


    @ApiOperation("用户是否存在账号是否被注册")
    @PostMapping("/isregister")
    public ResponseEntity IsRegister(@RequestParam("username") String username) {

        return userService.IsRegister(username);
    }

    @ApiOperation("用户更新自己的个人信息")
    @PostMapping("/updatefields")
    public ResponseEntity updateFields(@RequestBody User user) {
        return userService.updateFields(user);
    }


    @PostMapping("/updateavatar")
    @ResponseBody
    @ApiOperation("用户上传上传头像")
    public ResponseEntity updateAvatar(@RequestParam("file") MultipartFile file) {
        return userService.updateAvatar(file);
    }

    @ApiOperation("管理员获取所有用户信息")
    @Secured({"ROLE_ADMIN"})
    @PostMapping("/getalluser")
    public ResponseEntity getAllUser(@RequestBody UserPagetQueryAgs userPagetQueryAgs) {

        return userService.getAllUser(userPagetQueryAgs);
    }

    @ApiOperation("管理员获取所有用户数量")
    @Secured({"ROLE_ADMIN"})
    @PostMapping("/getusercnt")
    public ResponseEntity getusercnt() {

        return ResponseJson.success(userService.list());
    }

    @ApiOperation("管理员获取男女比例")
    @Secured({"ROLE_ADMIN"})
    @PostMapping("/findUserSexCountContGroupBySex")
    public ResponseEntity findUserSexCountContGroupBySex() {
        Map<String, Integer> map = new HashMap<>();
        map.put("男", 0);
        map.put("女", 0);
        List<UserSexCountBySex> userSexCountContGroupBySex = userService.getBaseMapper().findUserSexCountContGroupBySex();
        if (userSexCountContGroupBySex != null) {
            if (userSexCountContGroupBySex.size() > 0) {
                for (UserSexCountBySex sex : userSexCountContGroupBySex) {
                    if ("1".equals(sex.getSex())) {
                        map.put("男", sex.getCnt());
                    }
                    if ("2".equals(sex.getSex())) {
                        map.put("女", sex.getCnt());
                    }

                }
            }
        }

        List<Integer> list = new ArrayList<>();
        list.add(map.get("男"));
        list.add(map.get("女"));
        return ResponseJson.success(list);
    }

    @ApiOperation("按照月份分组统计用户注册数量")
//    @Secured({"ROLE_ADMIN"})
    @PostMapping("/findUserRegisterContGroupByCreateTime")
    public ResponseEntity findUserRegisterContGroupByCreateTime() {
        List<UserRegisterCountByCreateTime> userRegisterContGroupByCreateTime = userService.getBaseMapper().findUserRegisterContGroupByCreateTime();
        Map<String, Integer> map = new HashMap<>();
        map.put("01", 0);
        map.put("02", 0);
        map.put("03", 0);
        map.put("04", 0);
        map.put("05", 0);
        map.put("06", 0);
        map.put("07", 0);
        map.put("08", 0);
        map.put("09", 0);
        map.put("10", 0);
        map.put("11", 0);
        map.put("12", 0);
        if (userRegisterContGroupByCreateTime != null) {
            if (userRegisterContGroupByCreateTime.size() > 0) {
                for (UserRegisterCountByCreateTime userRegisterCountByCreateTime : userRegisterContGroupByCreateTime) {
                    map.put(userRegisterCountByCreateTime.getMonth(), userRegisterCountByCreateTime.getCnt());
                }
            }
        }
        List<Integer> list = new ArrayList<>();
        list.add(map.get("01"));
        list.add(map.get("02"));
        list.add(map.get("03"));
        list.add(map.get("04"));
        list.add(map.get("05"));
        list.add(map.get("06"));
        list.add(map.get("07"));
        list.add(map.get("08"));
        list.add(map.get("09"));
        list.add(map.get("10"));
        list.add(map.get("11"));
        list.add(map.get("12"));
        return ResponseJson.success(list);
    }

    @ApiOperation("管理员修改用户信息")
    @Secured({"ROLE_ADMIN"})
    @PostMapping("/updateuser")
    public ResponseEntity updateUser(@ApiParam("username") @RequestParam("username") String username,
                                     @ApiParam("sex") @RequestParam("sex") int sex,
                                     @ApiParam("avatar") @RequestParam("avatar") String avatar,
                                     @ApiParam("userStatus") @RequestParam("userStatus") int userStatus,
                                     @ApiParam("userNickname") @RequestParam("userNickname") String userNickname,
                                     MultipartFile fileName) {

        return userService.updateUser(username, sex, avatar, userStatus, userNickname, fileName);
    }

    @ApiOperation("获取用户拥有的权限")
    @PostMapping("/getuserroles")
    public ResponseEntity getUserRoles() {

        return userService.getUserRoles();
    }

    @ApiOperation("退出")
    @PostMapping("/logout")
    public ResponseEntity logout() {
        return ResponseJson.success();
    }


}
