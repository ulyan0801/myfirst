package com.my.springboot.controller;

import com.my.springboot.empty.Shijian;
import com.my.springboot.service.impl.ShijianServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Api(tags = "数据类接口")
public class ShiJianController {

    @Autowired
    ShijianServiceImpl shijianService;

    @ApiOperation("添加一条数据 预约时间段")
    @PostMapping("/addShiJianData")
    public ResponseEntity addShiJianData(@RequestBody Shijian bean) {
        return shijianService.addShiJianData(bean);
    }

    @ApiOperation("查预约时间段数据")
    @PostMapping("/getShiJianById")
    public ResponseEntity getShiJianById(@RequestParam("Id") Long Id) {
        return shijianService.getShiJianById(Id);
    }


    @ApiOperation("根据id删除 预约时间段")
    @PostMapping("/delShiJianByTid")
    public ResponseEntity delShiJianByTid(@RequestParam("Id") Long Id) {
        return shijianService.delShiJianByTid(Id);
    }


}
