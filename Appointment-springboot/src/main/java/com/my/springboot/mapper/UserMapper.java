package com.my.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.my.springboot.empty.User;
import com.my.springboot.empty.UserRegisterCountByCreateTime;
import com.my.springboot.empty.UserSexCountBySex;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface UserMapper extends BaseMapper<User> {
    @Select("select DATE_FORMAT(create_time,'%m') as month,count(*) as cnt from user  group by DATE_FORMAT(create_time,'%m');")
    List<UserRegisterCountByCreateTime> findUserRegisterContGroupByCreateTime();

    @Select("select sex, count(*) as cnt from user  group by sex;")
    List<UserSexCountBySex> findUserSexCountContGroupBySex();
}




