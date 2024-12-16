package com.my.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.my.springboot.empty.Recover;
import org.springframework.http.ResponseEntity;

public interface RecoverService extends IService<Recover> {
    ResponseEntity addRecoverData(Recover recover);

    ResponseEntity getAllRecoverData(Long pid);

    ResponseEntity delRecoverById(Long tid);
}
