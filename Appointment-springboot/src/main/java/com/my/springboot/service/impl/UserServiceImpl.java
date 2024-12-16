package com.my.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.springboot.empty.User;
import com.my.springboot.empty.UserPagetQueryAgs;
import com.my.springboot.empty.UserRoles;
import com.my.springboot.exception.GlobalException;
import com.my.springboot.mapper.UserMapper;
import com.my.springboot.security.UserDetailsBean;
import com.my.springboot.service.UserService;
import com.my.springboot.utils.Ip.GetUserIp;
import com.my.springboot.utils.Result.ResponseJson;
import com.my.springboot.utils.system.JudgeSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Autowired
    UserRolesServiceImpl userRolesService;

    //静态资源目录
    @Value("${windows.resources.path}")
    private String windowsResourcesPath;
    //静态资源目录
    @Value("${linux.resources.path}")
    private String linuxResourcesPath;

    @Value("${spring.mvc.static-path-pattern}")
    private String statics;

    @Override
    public ResponseEntity register(User user, HttpServletRequest request) {

        LambdaQueryWrapper<User> userQueryWrapperr = new LambdaQueryWrapper<>();
        userQueryWrapperr.eq(User::getUsername, user.getUsername());

        List<User> list = this.getBaseMapper().selectList(userQueryWrapperr);
        if (!Objects.isNull(list) && !list.isEmpty())
            throw new GlobalException("注册失败,用户已存在");
        //密码加密
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        String ipAddress = GetUserIp.getIpAddress(request);
        if (ipAddress != null)
            user.setLastLoginIp(ipAddress);

        if (user.getUserNickname() == null) {
            user.setUserNickname("用户" + user.getUsername());
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        System.out.println(formatter.format(date));
        user.setLastLoginTime(formatter.format(date));
        user.setCreateTime(formatter.format(date));
        //插入用户表
        this.save(user);
        //设置注册用户默认权限为普通权限
        UserRoles userRoles = new UserRoles();
        userRoles.setUserId(user.getId());
        userRoles.setRolesId(2);
        //插入权限表
        boolean save = userRolesService.save(userRoles);
        if (save) {
            return ResponseJson.success("注册成功");
        } else {
            throw new GlobalException("注册失败,未知错误");
        }
    }


    @Override
    public UserDetailsBean findUserDetailsBeanByName(String username) {
        //查找用户是否存在
        LambdaQueryWrapper<User> userQueryWrapperr = new LambdaQueryWrapper<>();
        userQueryWrapperr.eq(User::getUsername, username);
        User userServiceOne = this.getOne(userQueryWrapperr);
        if (Objects.isNull(userServiceOne)) {
            throw new UsernameNotFoundException("账号或者密码错误");
        }
//        设置UserDetailsBean的密码
        UserDetailsBean userDetailsBean = new UserDetailsBean();
        userDetailsBean.setUsername(username);
        userDetailsBean.setPassword(userServiceOne.getPassword());
        userDetailsBean.setStatus(userServiceOne.getUserStatus());
//        设置UserDetailsBean的权限
        List<String> userRolesById = userRolesService.getBaseMapper().findUserRolesById(userServiceOne.getId());
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (userRolesById.size() != 0) {
            for (String s : userRolesById) {
                authorities.add(new SimpleGrantedAuthority(s));
            }
        } else {
            throw new UsernameNotFoundException("服务器错误，用户权限异常");
        }
        userDetailsBean.setAuthorities(authorities);
        return userDetailsBean;
    }

    @Override
    public User findUserByName(String username) {
        LambdaQueryWrapper<User> userQueryWrapperr = new LambdaQueryWrapper<>();
        userQueryWrapperr.eq(User::getUsername, username);
        User userServiceOne = this.getOne(userQueryWrapperr);
        if (Objects.isNull(userServiceOne)) {
            throw new GlobalException(401, "无效token");
        }
        return userServiceOne;
    }


    @Override
    public ResponseEntity updateFields(User user) {

        String userName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userByName = findUserByName(userName);
        LambdaUpdateWrapper<User> userLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        userLambdaUpdateWrapper.eq(User::getUsername, userName);
        if (user.getPassword() != null) {
            System.out.println("---------1----------");
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            userLambdaUpdateWrapper.set(User::getPassword, bCryptPasswordEncoder.encode(user.getPassword()));
        }
        if (user.getUserNickname() != null) {
            System.out.println("---------2----------");
            userLambdaUpdateWrapper.set(User::getUserNickname, user.getUserNickname());
        }
        if (user.getUserStatus() != null) {
            System.out.println("---------3----------");

            userLambdaUpdateWrapper.set(User::getUserStatus, user.getUserStatus());
        }

        if (user.getBirthday() != null) {
            System.out.println("---------5----------");
            userLambdaUpdateWrapper.set(User::getBirthday, user.getBirthday());
        }
        if (user.getSex() != null) {
            System.out.println("---------6----------");
            userLambdaUpdateWrapper.set(User::getSex, user.getSex());
        }
        if (user.getUserStatus() != null) {
            System.out.println("---------7----------");
            userLambdaUpdateWrapper.set(User::getUserStatus, user.getUserStatus());
        }
        if (user.getAddr() != null) {
            System.out.println("---------8----------");
            userLambdaUpdateWrapper.set(User::getAddr, user.getAddr());
        }

        if (user.getAvatar() != null) {
            System.out.println("---------8----------");
            userLambdaUpdateWrapper.set(User::getAvatar, user.getAvatar());
        }

        if (user.getPhone() != null) {
            System.out.println("---------8----------");
            userLambdaUpdateWrapper.set(User::getPhone, user.getPhone());
        }

        if (user.getScore() != null) {
            System.out.println("---------9----------");
            userLambdaUpdateWrapper.set(User::getScore, user.getScore());
        }
        if (user.getCoin() != null) {
            System.out.println("---------10----------");
            Integer coin = userByName.getCoin();
            userLambdaUpdateWrapper.set(User::getCoin, user.getCoin() + coin);
        }
        if (user.getBalance() != null) {
            System.out.println("---------11----------");
            BigDecimal balance = userByName.getBalance();
            BigDecimal balance1 = user.getBalance();
            userLambdaUpdateWrapper.set(User::getBalance, balance.add(balance1));
        }

        if (user.getLastLoginIp() != null) {
            System.out.println("---------11----------");
            userLambdaUpdateWrapper.set(User::getLastLoginIp, user.getLastLoginIp());
        }
        if (user.getLastLoginTime() != null) {
            System.out.println("---------11----------");
            userLambdaUpdateWrapper.set(User::getLastLoginTime, user.getLastLoginTime());
        }

        if (this.update(userLambdaUpdateWrapper)) {
            return ResponseJson.success();
        } else {
            return ResponseJson.fail(400, "更新失败");
        }

    }

    @Override
    public ResponseEntity getUserByUserName(String username) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername, username);
        User user = getBaseMapper().selectOne(lambdaQueryWrapper);
        List<User> list = new ArrayList<>();
        list.add(user);
        return ResponseJson.success(list);
    }

    @Override
    public ResponseEntity updateUser(String username, int sex, String avatar, int userStatus, String userNickname, MultipartFile fileName) {
        if (fileName == null) {
            LambdaUpdateWrapper<User> userLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            userLambdaUpdateWrapper.eq(User::getUsername, username);
            userLambdaUpdateWrapper.set(User::getAvatar, avatar);
            userLambdaUpdateWrapper.set(User::getUserNickname, userNickname);
            userLambdaUpdateWrapper.set(User::getUserStatus, userStatus);
            userLambdaUpdateWrapper.set(User::getSex, sex);
            if (this.update(userLambdaUpdateWrapper)) {
                return ResponseJson.success();
            } else {
                return ResponseJson.fail(400, "更新失败");
            }
        } else {
            String Name = fileName.getOriginalFilename();
            String ext = Name.substring(Name.lastIndexOf("."));
            String newFileName = new SimpleDateFormat("yyyyMMddHHmm").format(new Date()) + "-" + UUID.randomUUID() + ext;

            //判断系统类型
            File upload = null;
            File path = null;
            if (JudgeSystem.isLinux()) {
                path = new File(linuxResourcesPath);
            } else if (JudgeSystem.isWindows()) {
                path = new File(windowsResourcesPath);
            }

            upload = new File(path.getAbsolutePath());
            if (null == upload) {
                return ResponseJson.fail(400, "上传失败");
            }
            if (!upload.exists()) upload.mkdirs();
//添加分隔符
            String uploadPath = upload + File.separator;
            try {
                fileName.transferTo(new File(uploadPath + newFileName));
                LambdaUpdateWrapper<User> userLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
                userLambdaUpdateWrapper.eq(User::getUsername, username);
                if (statics.contains("*")) {
                    statics = statics.replace("*", "");
                }
                userLambdaUpdateWrapper.set(User::getAvatar, statics + newFileName);
                userLambdaUpdateWrapper.set(User::getUserNickname, userNickname);
                userLambdaUpdateWrapper.set(User::getUserStatus, userStatus);
                userLambdaUpdateWrapper.set(User::getSex, sex);
                if (this.update(userLambdaUpdateWrapper)) {
                    return ResponseJson.success();
                } else {
                    return ResponseJson.fail(400, "更新失败");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return ResponseJson.fail(400, "失败");

            }
        }
    }


    @Override
    public ResponseEntity getAllUser(UserPagetQueryAgs userPagetQueryAgs) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if (userPagetQueryAgs.getAddr() != null) {
            if (!"".equals(userPagetQueryAgs.getAddr())) {
                queryWrapper.like(User::getAddr, userPagetQueryAgs.getAddr());
            }
        }
        if (userPagetQueryAgs.getSex() != null) {
            if (!"".equals(userPagetQueryAgs.getSex())) {
                if ("男".equals(userPagetQueryAgs.getSex()))
                    queryWrapper.eq(User::getSex, 1);
                if ("女".equals(userPagetQueryAgs.getSex()))
                    queryWrapper.eq(User::getSex, 2);
            }
        }

        if (userPagetQueryAgs.getStatus() != null) {
            if (!"".equals(userPagetQueryAgs.getStatus())) {
                if ("禁用".equals(userPagetQueryAgs.getStatus()))
                    queryWrapper.eq(User::getUserStatus, 0);
                if ("正常".equals(userPagetQueryAgs.getStatus()))
                    queryWrapper.eq(User::getUserStatus, 1);
            }
        }
        if (userPagetQueryAgs.getAccountOrNickName() != null) {
            if (!"".equals(userPagetQueryAgs.getAccountOrNickName())) {
                queryWrapper.like(User::getUsername, userPagetQueryAgs.getAccountOrNickName()).
                        or().
                        like(User::getUserNickname, userPagetQueryAgs.getAccountOrNickName());
            }
        }
        if (userPagetQueryAgs.getRegisterTime() != null) {
            if (userPagetQueryAgs.getRegisterTime().get(0).equals(userPagetQueryAgs.getRegisterTime().get(1))) {
                queryWrapper.eq(User::getCreateTime, userPagetQueryAgs.getRegisterTime().get(0));
            } else {
                queryWrapper.ge(User::getCreateTime, userPagetQueryAgs.getRegisterTime().get(0));
                queryWrapper.le(User::getCreateTime, userPagetQueryAgs.getRegisterTime().get(1));
            }
        }

        //设置分页参数 第几页 多少条
        Page<User> page = new Page<>(userPagetQueryAgs.getPage(), userPagetQueryAgs.getLimit());
        this.getBaseMapper().selectPage(page, queryWrapper);
        //获取分页数据
        List<User> list = page.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("total", page.getTotal());
        map.put("hasPrevious", page.hasPrevious());//是否有上一页
        map.put("hasNext", page.hasNext()); //是否有下一页
        map.put("datalist", list);
        List<Object> dataList = new ArrayList<>();
        dataList.add(map);

        return ResponseJson.success(dataList);
    }

    @Override
    public ResponseEntity getUserRoles() {
        String userName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = findUserByName(userName);
        List<String> userRoles = userRolesService.getBaseMapper().findUserRolesById(user.getId());
        Map<String, Object> map = new HashMap<>();
        map.put("username", user.getUsername());
        map.put("name", user.getUserNickname());
        map.put("avatar", user.getAvatar());
        map.put("roles", userRoles);
        List<Map> mapList = new ArrayList<>();
        mapList.add(map);
        return ResponseJson.success(mapList);
    }

    @Override
    public ResponseEntity getUserInfo(HttpServletRequest request) {
        String userName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String ipAddress = GetUserIp.getIpAddress(request);

        User user = new User();
        user.setUsername(userName);
        if (ipAddress != null)
            user.setLastLoginIp(ipAddress);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        user.setLastLoginTime(formatter.format(date));
        updateFields(user);

        User userByName = findUserByName(userName);
        List<User> list = new ArrayList<>();
        list.add(userByName);
        return ResponseJson.success(list);
    }

    @Override
    public ResponseEntity IsRegister(String username) {
        LambdaQueryWrapper<User> userQueryWrapperr = new LambdaQueryWrapper<>();
        userQueryWrapperr.eq(User::getUsername, username);
        User userServiceOne = this.getOne(userQueryWrapperr);
        if (Objects.isNull(userServiceOne)) {
            return ResponseJson.fail(400, "用户不存在");
        }

        return ResponseJson.success("账号被注册");
    }

    @Override
    public ResponseEntity updateAvatar(MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseJson.fail(400, "文件不能为空");
        }
        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf("."));
        String newFileName = new SimpleDateFormat("yyyyMMddHHmm").format(new Date()) + "-" + UUID.randomUUID() + ext;

        //判断系统类型
        File upload = null;
        File path = null;
        if (JudgeSystem.isLinux()) {
            path = new File(linuxResourcesPath);
        } else if (JudgeSystem.isWindows()) {
            path = new File(windowsResourcesPath);
        }

        upload = new File(path.getAbsolutePath());
        if (null == upload) {
            return ResponseJson.fail(400, "上传失败");
        }
        if (!upload.exists()) upload.mkdirs();
//添加分隔符
        String uploadPath = upload + File.separator;
        try {
            file.transferTo(new File(uploadPath + newFileName));
            String userName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            LambdaUpdateWrapper<User> userLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            userLambdaUpdateWrapper.eq(User::getUsername, userName);
            if (statics.contains("*")) {
                statics = statics.replace("*", "");
            }
            userLambdaUpdateWrapper.set(User::getAvatar, statics + newFileName);
            if (this.update(userLambdaUpdateWrapper)) {
                return ResponseJson.success();
            } else {
                return ResponseJson.fail(400, "更新失败");
            }

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseJson.fail(400, "失败");

        }
    }

    @Override
    public ResponseEntity modifyPwd(String username, String oldpwd, String newpwd) {
        User userByName = findUserByName(username);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean matches = bCryptPasswordEncoder.matches(oldpwd, userByName.getPassword());
        if (!matches) {
            return ResponseJson.fail(400, "原始密码错误");
        }

        LambdaUpdateWrapper<User> userLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        userLambdaUpdateWrapper.eq(User::getUsername, username);

        String encode = bCryptPasswordEncoder.encode(newpwd);
        userLambdaUpdateWrapper.set(User::getPassword, encode);
        boolean update = update(userLambdaUpdateWrapper);
        if (update) {
            return ResponseJson.fail(200, "密码修改成功");
        } else {
            return ResponseJson.fail(400, "密码修改失败");
        }

    }


    public ResponseEntity findPwd(User user) {
        LambdaQueryWrapper<User> userQueryWrapperr = new LambdaQueryWrapper<>();
        userQueryWrapperr.eq(User::getUsername, user.getUsername());
        User userServiceOne = this.getOne(userQueryWrapperr);
        if (Objects.isNull(userServiceOne)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        //密码加密
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        userServiceOne.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        boolean b = this.updateById(userServiceOne);
        if (b) {
            return ResponseJson.success("密码找回成功");
        } else {
            return ResponseJson.success("密码找回失败");
        }

    }
}




