package com.my.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.springboot.empty.*;
import com.my.springboot.mapper.YuyueMapper;
import com.my.springboot.service.YuyueService;
import com.my.springboot.utils.Result.ResponseJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class YuyueServiceImpl extends ServiceImpl<YuyueMapper, Yuyue>
        implements YuyueService {
    @Autowired
    ShijianServiceImpl shijianService;

    @Autowired
    AppointmentServiceImpl appointmentService;
    @Autowired
    UserServiceImpl userService;

    @Override
    public ResponseEntity getYuYueDataByTid(Long id) {
        LambdaQueryWrapper<Yuyue> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Yuyue::getTid, id);
        List<Yuyue> list = list(lambdaQueryWrapper);
        System.out.println("-----------" + list.size());
        Map<String, Object> map = new HashMap<>();
        map.put("datalist", list);
        List<Object> dataList = new ArrayList<>();
        dataList.add(map);
        return ResponseJson.success(dataList);
    }

    @Override
    public ResponseEntity addYuYueData(Yuyue bean) {

        if (save(bean)) {
            return ResponseJson.success("新增成功");
        } else {
            return ResponseJson.fail(400, "新增失败");
        }
    }

    @Override
    public ResponseEntity delYuYueByTidAndShiJianid(Long tid, Long shijianid, String riqi) {
        LambdaQueryWrapper<Yuyue> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Yuyue::getTid, tid);
        lambdaQueryWrapper.eq(Yuyue::getShijianid, shijianid);
        lambdaQueryWrapper.eq(Yuyue::getRiqi, riqi);
        if (getBaseMapper().delete(lambdaQueryWrapper) > 0) {
            return ResponseJson.success("删除成功");
        } else {
            return ResponseJson.fail(400, "删除失败");
        }
    }

    @Override
    public ResponseEntity getWoDeYuYue() {
        String userName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LambdaQueryWrapper<Yuyue> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Yuyue::getRiqi);
        lambdaQueryWrapper.eq(Yuyue::getUsername, userName);
        List<Yuyue> yuyueList = list(lambdaQueryWrapper);
        List<WoDeYuYueBean> woDeYuYueBeanList = new ArrayList<>();
        for (Yuyue yuyue : yuyueList) {
            WoDeYuYueBean bean = new WoDeYuYueBean();
            bean.setYuyueid(yuyue.getId());
            bean.setTid(yuyue.getTid());
            bean.setRiqi(yuyue.getRiqi());
            bean.setShijianid(yuyue.getShijianid());
            bean.setHaoma(yuyue.getHaoma());
            woDeYuYueBeanList.add(bean);
        }

        for (int i = 0; i < woDeYuYueBeanList.size(); i++) {
            Shijian shijian = shijianService.getBaseMapper().selectById(woDeYuYueBeanList.get(i).getShijianid());
            if (shijian != null) {
                woDeYuYueBeanList.get(i).setYuyueshijian(shijian.getYuyueshijian());
                woDeYuYueBeanList.get(i).setJiage(shijian.getJiage());
            }
            Appointment appointment = appointmentService.getBaseMapper().selectById(woDeYuYueBeanList.get(i).getTid());
            if (appointment != null) {
                woDeYuYueBeanList.get(i).setId(appointment.getId());
                woDeYuYueBeanList.get(i).setLeixing(appointment.getLeixing());
                woDeYuYueBeanList.get(i).setYishi(appointment.getXingming());

            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("datalist", woDeYuYueBeanList);
        List<Object> dataList = new ArrayList<>();
        dataList.add(map);
        return ResponseJson.success(dataList);

    }

    @Override
    public ResponseEntity delYuYueByTid(Long tid) {
        LambdaQueryWrapper<Yuyue> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Yuyue::getTid, tid);
        if (getBaseMapper().delete(lambdaQueryWrapper) > 0) {
            return ResponseJson.success("删除成功");
        } else {
            return ResponseJson.fail(400, "删除失败");
        }
    }

    @Override
    public ResponseEntity getYuYueDataByTidAndShijianidAndRiqi(Long tid, Long shijianid, String riqi) {
        LambdaQueryWrapper<Yuyue> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Yuyue::getTid, tid);
        lambdaQueryWrapper.eq(Yuyue::getShijianid, shijianid);
        lambdaQueryWrapper.eq(Yuyue::getRiqi, riqi);
        List<Yuyue> yuyueList = list(lambdaQueryWrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("datalist", yuyueList);
        List<Object> dataList = new ArrayList<>();
        dataList.add(map);
        return ResponseJson.success(dataList);


    }

    @Override
    public ResponseEntity updateYuYue(Yuyue bean) {
        LambdaUpdateWrapper<Yuyue> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Yuyue::getTid, bean.getTid());
        lambdaUpdateWrapper.eq(Yuyue::getShijianid, bean.getShijianid());
        lambdaUpdateWrapper.eq(Yuyue::getRiqi, bean.getRiqi());
        lambdaUpdateWrapper.eq(Yuyue::getUsername, bean.getUsername());
        lambdaUpdateWrapper.set(Yuyue::getHaoma, bean.getHaoma());

        if (update(lambdaUpdateWrapper)) {
            return ResponseJson.success("修改成功");
        }
        return ResponseJson.fail(400, "修改失败");
    }

    @Override
    public ResponseEntity getAllYuYue(Long tid, Long shijianid, String riqi) {
        LambdaQueryWrapper<Yuyue> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Yuyue::getRiqi);

        lambdaQueryWrapper.eq(Yuyue::getTid, tid);
        lambdaQueryWrapper.eq(Yuyue::getShijianid, shijianid);
        lambdaQueryWrapper.eq(Yuyue::getRiqi, riqi);
        List<Yuyue> yuyueList = list(lambdaQueryWrapper);
        List<WoDeYuYueBean> woDeYuYueBeanList = new ArrayList<>();
        for (Yuyue yuyue : yuyueList) {
            WoDeYuYueBean bean = new WoDeYuYueBean();
            bean.setYuyueid(yuyue.getId());
            bean.setTid(yuyue.getTid());
            bean.setRiqi(yuyue.getRiqi());
            bean.setShijianid(yuyue.getShijianid());
            bean.setHaoma(yuyue.getHaoma());
            bean.setUsername(yuyue.getUsername());

            woDeYuYueBeanList.add(bean);
        }

        for (int i = 0; i < woDeYuYueBeanList.size(); i++) {
            Shijian shijian = shijianService.getBaseMapper().selectById(woDeYuYueBeanList.get(i).getShijianid());
            if (shijian != null) {
                woDeYuYueBeanList.get(i).setJiage(shijian.getJiage());
            }

            LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userLambdaQueryWrapper.eq(User::getUsername, woDeYuYueBeanList.get(i).getUsername());
            User user = userService.getBaseMapper().selectOne(userLambdaQueryWrapper);

            if (user != null) {
                woDeYuYueBeanList.get(i).setAvatar(user.getAvatar());
                woDeYuYueBeanList.get(i).setPhone(user.getPhone());
                woDeYuYueBeanList.get(i).setUserNickname(user.getUserNickname());
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("datalist", woDeYuYueBeanList);
        List<Object> dataList = new ArrayList<>();
        dataList.add(map);
        return ResponseJson.success(dataList);
    }

    @Override
    public ResponseEntity delYuYueByid(Long id) {
        LambdaQueryWrapper<Yuyue> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Yuyue::getId, id);
        if (getBaseMapper().delete(lambdaQueryWrapper) > 0) {
            return ResponseJson.success("删除成功");
        } else {
            return ResponseJson.fail(400, "删除失败");
        }
    }
}




