package com.my.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.my.springboot.empty.Dianzan;
import org.springframework.http.ResponseEntity;


public interface DianzanService extends IService<Dianzan> {

    ResponseEntity isDianZan(String username, Long tid);

    ResponseEntity getItemDianZan(Long tid);

    ResponseEntity addAndRemoveDianZan(String username, Long tid);

    ResponseEntity delDianZanTid(Long tid);
}
