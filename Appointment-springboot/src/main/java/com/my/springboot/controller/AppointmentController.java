package com.my.springboot.controller;

import com.my.springboot.empty.Appointment;
import com.my.springboot.service.impl.AppointmentServiceImpl;
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
public class AppointmentController {

    @Autowired
    AppointmentServiceImpl appointmentService;

    @ApiOperation("添加一条数据")
    @PostMapping("/addData")
    public ResponseEntity addData(@RequestBody Appointment bean) {
        return appointmentService.addData(bean);
    }

    @ApiOperation("获取所有数据")
    @PostMapping("/getAllData")
    public ResponseEntity getAllData() {
        return appointmentService.getAllData();
    }

    @ApiOperation("修改数据")
    @PostMapping("/updateDataById")
    public ResponseEntity updateDataById(@RequestBody Appointment bean) {
        return appointmentService.updateDataById(bean);
    }

    @ApiOperation("根据ID查数据")
    @PostMapping("/getDataById")
    public ResponseEntity getDataById(@RequestParam("Id") Long Id) {
        return appointmentService.getDataById(Id);
    }


    @ApiOperation("根据ID删数据")
    @PostMapping("/delDataById")
    public ResponseEntity delDataById(@RequestParam("Id") Long Id) {
        return appointmentService.delDataById(Id);
    }

    @ApiOperation("根据分类查询")
    @PostMapping("/getDataByLeiXing")
    public ResponseEntity getDataByLeiXing(@RequestParam("leixing") String leixing) {
        return appointmentService.getDataByLeiXing(leixing);
    }
}
