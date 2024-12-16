package com.my.springboot.controller;

import com.my.springboot.service.impl.ShoucangServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Api(tags = "收藏接口")
public class ShouCangController {

    @Autowired
    ShoucangServiceImpl shoucangService;

    @ApiOperation("是否收藏")
    @PostMapping("/isShouCang")
    public ResponseEntity isShouCang(@RequestParam("username") String username, @RequestParam("tid") Long tid) {
        return shoucangService.isShouCang(username, tid);
    }

    @ApiOperation("用户获取收藏的记录")
    @PostMapping("/getUserShouCang")
    public ResponseEntity getUserShouCang() {
        return shoucangService.getUserShouCang();
    }

    @ApiOperation("用户添加或者移除收藏")
    @PostMapping("/addAndRemoveShouCang")
    public ResponseEntity addAndRemoveShouCang(@RequestParam("username") String username, @RequestParam("tid") Long tid) {
        return shoucangService.addAndRemoveShouCang(username, tid);
    }

    @ApiOperation("删收藏")
    @PostMapping("/delShouCangTid")
    public ResponseEntity delShouCangTid(@RequestParam("tid") Long tid) {
        return shoucangService.delShouCangTid(tid);
    }
}
