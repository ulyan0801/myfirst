package com.my.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.my.springboot.empty.Shoucang;
import org.springframework.http.ResponseEntity;


public interface ShoucangService extends IService<Shoucang> {
    ResponseEntity isShouCang(String username, Long tid);

    ResponseEntity getUserShouCang();

    ResponseEntity addAndRemoveShouCang(String username, Long tid);

    ResponseEntity delShouCangTid(Long tid);
}
