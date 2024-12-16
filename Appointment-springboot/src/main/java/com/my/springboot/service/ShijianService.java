package com.my.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.my.springboot.empty.Shijian;
import org.springframework.http.ResponseEntity;


public interface ShijianService extends IService<Shijian> {

    ResponseEntity addShiJianData(Shijian bean);

    ResponseEntity getShiJianById(Long id);

    ResponseEntity delShiJianByTid(Long id);
}
