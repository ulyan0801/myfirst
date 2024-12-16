package com.my.springboot.controller;

import com.my.springboot.empty.Yuyue;
import com.my.springboot.service.impl.YuyueServiceImpl;
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
public class YuYueController {

    @Autowired
    YuyueServiceImpl yuyueService;

    @ApiOperation("添加一条数据 预约信息")
    @PostMapping("/addYuYueData")
    public ResponseEntity addYuYueData(@RequestBody Yuyue bean) {
        return yuyueService.addYuYueData(bean);
    }

    @ApiOperation("查预约数据")
    @PostMapping("/getYuYueDataByTid")
    public ResponseEntity getYuYueDataByTid(@RequestParam("Id") Long Id) {
        return yuyueService.getYuYueDataByTid(Id);
    }

    @ApiOperation("查我的预约 根据账号")
    @PostMapping("/getWoDeYuYue")
    public ResponseEntity getWoDeYuYue() {
        return yuyueService.getWoDeYuYue();
    }

    @ApiOperation("更新 预约信息")
    @PostMapping("/updateYuYue")
    public ResponseEntity updateYuYue(@RequestBody Yuyue bean) {
        return yuyueService.updateYuYue(bean);
    }


    @ApiOperation("根据id删除数据 ")
    @PostMapping("/delYuYueByTidAndShiJianid")
    public ResponseEntity delYuYueByTidAndShiJianid(@RequestParam("tid") Long tid, @RequestParam("shijianid") Long shijianid, @RequestParam("riqi") String riqi) {
        return yuyueService.delYuYueByTidAndShiJianid(tid, shijianid, riqi);
    }


    @ApiOperation("根据id删除数据 ")
    @PostMapping("/delYuYueByTid")
    public ResponseEntity delYuYueByTid(@RequestParam("tid") Long tid) {
        return yuyueService.delYuYueByTid(tid);
    }

    @ApiOperation("根据条件查预约数据")
    @PostMapping("/getYuYueDataByTidAndShijianidAndRiqi")
    public ResponseEntity getYuYueDataByTidAndShijianidAndRiqi(@RequestParam("tid") Long tid, @RequestParam("shijianid") Long shijianid, @RequestParam("riqi") String riqi) {
        return yuyueService.getYuYueDataByTidAndShijianidAndRiqi(tid, shijianid, riqi);
    }


    @ApiOperation("根据条件查预约数据")
    @PostMapping("/getAllYuYue")
    public ResponseEntity getAllYuYue(@RequestParam("tid") Long tid, @RequestParam("shijianid") Long shijianid, @RequestParam("riqi") String riqi) {
        return yuyueService.getAllYuYue(tid, shijianid, riqi);
    }


    @ApiOperation("根据id删除数据")
    @PostMapping("/delYuYueByid")
    public ResponseEntity delYuYueByid(@RequestParam("Id") Long id) {
        return yuyueService.delYuYueByid(id);
    }

}
