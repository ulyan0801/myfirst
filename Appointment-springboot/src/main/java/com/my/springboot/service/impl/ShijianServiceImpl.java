package com.my.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.springboot.empty.Shijian;
import com.my.springboot.mapper.ShijianMapper;
import com.my.springboot.service.ShijianService;
import com.my.springboot.utils.Result.ResponseJson;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ShijianServiceImpl extends ServiceImpl<ShijianMapper, Shijian>
        implements ShijianService {

    @Override
    public ResponseEntity addShiJianData(Shijian bean) {
        if (save(bean)) {
            return ResponseJson.success("新增成功");
        } else {
            return ResponseJson.fail(400, "新增失败");
        }
    }

    @Override
    public ResponseEntity getShiJianById(Long id) {
        LambdaQueryWrapper<Shijian> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByAsc(Shijian::getBianhao);
        lambdaQueryWrapper.eq(Shijian::getTid, id);
        List<Shijian> list = list(lambdaQueryWrapper);
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setShengyurenshu(list.get(i).getRenshu());
            }
        }


        Map<String, Object> map = new HashMap<>();
        map.put("datalist", list);
        List<Object> dataList = new ArrayList<>();
        dataList.add(map);
        return ResponseJson.success(dataList);
    }

    @Override
    public ResponseEntity delShiJianByTid(Long id) {
        LambdaQueryWrapper<Shijian> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Shijian::getTid, id);
        if (getBaseMapper().delete(lambdaQueryWrapper) > 0) {
            return ResponseJson.success("删除成功");
        } else {
            return ResponseJson.fail(400, "删除失败");
        }
    }
}




