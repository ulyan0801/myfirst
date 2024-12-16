package com.my.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.my.springboot.empty.Yuyue;
import org.springframework.http.ResponseEntity;


public interface YuyueService extends IService<Yuyue> {

    ResponseEntity getYuYueDataByTid(Long id);

    ResponseEntity addYuYueData(Yuyue bean);

    ResponseEntity delYuYueByTidAndShiJianid(Long tid, Long shijianid, String riqi);

    ResponseEntity getWoDeYuYue();

    ResponseEntity delYuYueByTid(Long tid);

    ResponseEntity getYuYueDataByTidAndShijianidAndRiqi(Long tid, Long shijianid, String riqi);

    ResponseEntity updateYuYue(Yuyue bean);

    ResponseEntity getAllYuYue(Long tid, Long shijianid, String riqi);

    ResponseEntity delYuYueByid(Long id);
}
