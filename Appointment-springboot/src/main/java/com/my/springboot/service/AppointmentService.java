package com.my.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.my.springboot.empty.Appointment;
import org.springframework.http.ResponseEntity;


public interface AppointmentService extends IService<Appointment> {

    ResponseEntity addData(Appointment bean);

    ResponseEntity getAllData();

    ResponseEntity updateDataById(Appointment bean);

    ResponseEntity getDataById(Long id);

    ResponseEntity delDataById(Long id);

    ResponseEntity getDataByLeiXing(String leixing);
}
