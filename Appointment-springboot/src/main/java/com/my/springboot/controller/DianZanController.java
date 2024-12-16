package com.my.springboot.controller;

import com.my.springboot.service.impl.DianzanServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Api(tags = "点赞接口")
public class DianZanController {

    @Autowired
    DianzanServiceImpl dianzanService;

    @ApiOperation("是否点赞")
    @PostMapping("/isDianZan")
    public ResponseEntity isDianZan(@RequestParam("username") String username, @RequestParam("tid") Long tid) {
        return dianzanService.isDianZan(username, tid);
    }

    @ApiOperation("点赞数量")
    @PostMapping("/getItemDianZan")
    public ResponseEntity getItemDianZan(@RequestParam("tid") Long tid) {
        return dianzanService.getItemDianZan(tid);
    }

    @ApiOperation("用户添加或者移除点赞")
    @PostMapping("/addAndRemoveDianZan")
    public ResponseEntity addAndRemoveDianZan(@RequestParam("username") String username, @RequestParam("tid") Long tid) {
        return dianzanService.addAndRemoveDianZan(username, tid);
    }

    @ApiOperation("删点赞")
    @PostMapping("/delDianZanTid")
    public ResponseEntity delDianZanTid(@RequestParam("tid") Long tid) {
        return dianzanService.delDianZanTid(tid);
    }

}
