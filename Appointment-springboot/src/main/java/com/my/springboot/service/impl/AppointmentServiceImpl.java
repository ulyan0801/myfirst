package com.my.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.springboot.empty.Appointment;
import com.my.springboot.mapper.AppointmentMapper;
import com.my.springboot.service.AppointmentService;
import com.my.springboot.utils.Result.ResponseJson;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment>
        implements AppointmentService {

    @Override
    public ResponseEntity addData(Appointment bean) {
        bean.setShijian(System.currentTimeMillis());
        if (save(bean)) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", bean.getId());
            List<Object> dataList = new ArrayList<>();
            dataList.add(map);
            return ResponseJson.success(dataList);
        } else {
            return ResponseJson.fail(400, "新增失败");
        }
    }

    @Override
    public ResponseEntity getAllData() {
        LambdaQueryWrapper<Appointment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Appointment::getShijian);
        List<Appointment> list = list(lambdaQueryWrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("datalist", list);
        List<Object> dataList = new ArrayList<>();
        dataList.add(map);
        return ResponseJson.success(dataList);
    }

    @Override
    public ResponseEntity updateDataById(Appointment bean) {
        LambdaUpdateWrapper<Appointment> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Appointment::getId, bean.getId());
        lambdaUpdateWrapper.set(Appointment::getShipin, bean.getShipin());
        lambdaUpdateWrapper.set(Appointment::getQita, bean.getQita());
        lambdaUpdateWrapper.set(Appointment::getXingqi, bean.getXingqi());
        lambdaUpdateWrapper.set(Appointment::getLeixing, bean.getLeixing());
        lambdaUpdateWrapper.set(Appointment::getContent, bean.getContent());
        lambdaUpdateWrapper.set(Appointment::getShijian, System.currentTimeMillis());
        lambdaUpdateWrapper.set(Appointment::getLeixingf, bean.getLeixingf());
        lambdaUpdateWrapper.set(Appointment::getXingming, bean.getXingming());


        if (update(lambdaUpdateWrapper)) {
            return ResponseJson.success("修改成功");
        }
        return ResponseJson.fail(400, "修改失败");
    }

    @Override
    public ResponseEntity getDataById(Long id) {
        LambdaQueryWrapper<Appointment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Appointment::getId, id);
        List<Appointment> list = list(lambdaQueryWrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("datalist", list);
        List<Object> dataList = new ArrayList<>();
        dataList.add(map);
        return ResponseJson.success(dataList);
    }

    @Override
    public ResponseEntity delDataById(Long id) {
        if (getBaseMapper().deleteById(id) > 0) {
            return ResponseJson.success("删除成功");
        } else {
            return ResponseJson.fail(400, "删除失败");
        }
    }

    @Override
    public ResponseEntity getDataByLeiXing(String leixing) {
        LambdaQueryWrapper<Appointment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Appointment::getShijian);
        lambdaQueryWrapper.eq(Appointment::getLeixing, leixing);
        List<Appointment> list = list(lambdaQueryWrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("datalist", list);
        List<Object> dataList = new ArrayList<>();
        dataList.add(map);
        return ResponseJson.success(dataList);
    }
}




