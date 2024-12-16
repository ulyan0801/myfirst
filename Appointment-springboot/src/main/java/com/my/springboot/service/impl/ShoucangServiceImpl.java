package com.my.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.springboot.empty.Appointment;
import com.my.springboot.empty.Shoucang;
import com.my.springboot.mapper.ShoucangMapper;
import com.my.springboot.service.ShoucangService;
import com.my.springboot.utils.Result.ResponseJson;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ShoucangServiceImpl extends ServiceImpl<ShoucangMapper, Shoucang>
        implements ShoucangService {
    @Override
    public ResponseEntity isShouCang(String username, Long tid) {
        LambdaQueryWrapper<Shoucang> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Shoucang::getUname, username);
        lambdaQueryWrapper.eq(Shoucang::getTid, tid);
        List<Shoucang> list = list(lambdaQueryWrapper);
        if (list.size() > 0) {
            return ResponseJson.success();
        } else {
            return ResponseJson.fail(400, "");
        }

    }

    @Override
    public ResponseEntity getUserShouCang() {

        String userName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Appointment> list = getBaseMapper().getUserShouCang(userName);
        Map<String, Object> map = new HashMap<>();
        map.put("datalist", list);
        List<Object> dataList = new ArrayList<>();
        dataList.add(map);
        return ResponseJson.success(dataList);
    }

    @Override
    public ResponseEntity addAndRemoveShouCang(String username, Long tid) {
        LambdaQueryWrapper<Shoucang> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Shoucang::getUname, username);
        lambdaQueryWrapper.eq(Shoucang::getTid, tid);
        List<Shoucang> list = list(lambdaQueryWrapper);
        Shoucang bean = new Shoucang();
        bean.setUname(username);
        bean.setTid(tid);
        if (list != null) {
            if (list.size() > 0) {
                for (Shoucang shoucang : list) {
                    getBaseMapper().deleteById(shoucang.getId());
                }
                return ResponseJson.success("1");
            } else {

                save(bean);
                return ResponseJson.success("2");
            }
        } else {
            save(bean);
            return ResponseJson.success("2");
        }
    }

    @Override
    public ResponseEntity delShouCangTid(Long tid) {
        LambdaQueryWrapper<Shoucang> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Shoucang::getTid, tid);
        if (getBaseMapper().delete(lambdaQueryWrapper) > 0) {
            return ResponseJson.success("删除成功");
        } else {
            return ResponseJson.fail(400, "删除失败");
        }
    }
}




