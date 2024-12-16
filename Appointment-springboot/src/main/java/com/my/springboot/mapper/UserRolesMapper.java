package com.my.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.my.springboot.empty.UserRoles;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface UserRolesMapper extends BaseMapper<UserRoles> {
    @Select("select name from user_roles left join role on roles_id=role.id where user_id=#{id} having name is not null")
    List<String> findUserRolesById(@Param("id") Long id);


}




