package com.my.springboot.controller;

import com.my.springboot.empty.Recover;
import com.my.springboot.service.impl.RecoverServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Api(tags = "获取内容数据类接口")
public class RecoverController {

    @Autowired
    RecoverServiceImpl recoverService;

    @ApiOperation("添加一条数据")
    @PostMapping("/addRecoverData")
    public ResponseEntity addRecoverData(@RequestBody Recover recover) {
        return recoverService.addRecoverData(recover);
    }


    @ApiOperation("查数据")
    @PostMapping("/getAllRecoverData")
    public ResponseEntity getAllRecoverData(@RequestParam("pid") Long pid) {
        return recoverService.getAllRecoverData(pid);
    }


    @ApiOperation("根据ID删数据")
    @PostMapping("/delLiuYanTid")
    public ResponseEntity delRecoverById(@RequestParam("tid") Long tid) {
        return recoverService.delRecoverById(tid);
    }


}
