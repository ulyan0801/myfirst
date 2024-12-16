package com.my.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.springboot.empty.UserRoles;
import com.my.springboot.mapper.UserRolesMapper;
import com.my.springboot.service.UserRolesService;
import org.springframework.stereotype.Service;


@Service
public class UserRolesServiceImpl extends ServiceImpl<UserRolesMapper, UserRoles>
        implements UserRolesService {

}




