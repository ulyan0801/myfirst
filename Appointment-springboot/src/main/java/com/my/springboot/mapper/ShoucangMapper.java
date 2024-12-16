package com.my.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.my.springboot.empty.Appointment;
import com.my.springboot.empty.Shoucang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface ShoucangMapper extends BaseMapper<Shoucang> {
    @Select("SELECT Appointment.id as id,xingming,xingqi,content,leixing,qita,leixingf,shoucang.id as shoucangid  FROM Appointment  left JOIN shoucang ON shoucang.tid=Appointment.id where shoucang.uname=#{username}")
    List<Appointment> getUserShouCang(@Param("username") String username);
}




