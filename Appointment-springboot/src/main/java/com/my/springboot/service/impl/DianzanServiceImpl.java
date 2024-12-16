package com.my.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.springboot.empty.Dianzan;
import com.my.springboot.mapper.DianzanMapper;
import com.my.springboot.service.DianzanService;
import com.my.springboot.utils.Result.ResponseJson;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DianzanServiceImpl extends ServiceImpl<DianzanMapper, Dianzan>
        implements DianzanService {

    @Override
    public ResponseEntity isDianZan(String username, Long tid) {
        LambdaQueryWrapper<Dianzan> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Dianzan::getUname, username);
        lambdaQueryWrapper.eq(Dianzan::getTid, tid);
        List<Dianzan> list = list(lambdaQueryWrapper);
        if (list.size() > 0) {
            return ResponseJson.success();
        } else {
            return ResponseJson.fail(400, "");
        }

    }

    @Override
    public ResponseEntity getItemDianZan(Long tid) {
        LambdaQueryWrapper<Dianzan> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Dianzan::getTid, tid);
        List<Dianzan> list = list(lambdaQueryWrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("cnt", list.size());
        List<Object> dataList = new ArrayList<>();
        dataList.add(map);
        return ResponseJson.success(dataList);
    }

    @Override
    public ResponseEntity addAndRemoveDianZan(String username, Long tid) {
        LambdaQueryWrapper<Dianzan> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Dianzan::getUname, username);
        lambdaQueryWrapper.eq(Dianzan::getTid, tid);
        List<Dianzan> list = list(lambdaQueryWrapper);
        Dianzan bean = new Dianzan();
        bean.setUname(username);
        bean.setTid(tid);
        if (list != null) {
            if (list.size() > 0) {
                for (Dianzan dianzan : list) {
                    getBaseMapper().deleteById(dianzan.getId());
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
    public ResponseEntity delDianZanTid(Long tid) {
        LambdaQueryWrapper<Dianzan> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Dianzan::getTid, tid);
        if (getBaseMapper().delete(lambdaQueryWrapper) > 0) {
            return ResponseJson.success("删除成功");
        } else {
            return ResponseJson.fail(400, "删除失败");
        }
    }
}




