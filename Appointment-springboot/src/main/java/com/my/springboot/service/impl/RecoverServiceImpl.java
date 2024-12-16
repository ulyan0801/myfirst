package com.my.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.springboot.empty.Recover;
import com.my.springboot.empty.User;
import com.my.springboot.mapper.RecoverMapper;
import com.my.springboot.service.RecoverService;
import com.my.springboot.utils.Result.ResponseJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class RecoverServiceImpl extends ServiceImpl<RecoverMapper, Recover>
        implements RecoverService {
    @Autowired
    UserServiceImpl userService;

    @Override
    public ResponseEntity addRecoverData(Recover recover) {
        if (save(recover)) {
            return ResponseJson.success("新增失败");
        } else {
            return ResponseJson.fail(400, "新增失败");
        }
    }

    @Override
    public ResponseEntity getAllRecoverData(Long pid) {
        LambdaQueryWrapper<Recover> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Recover::getPid, pid);
        List<Recover> list = list(lambdaQueryWrapper);

        for (int i = 0; i < list.size(); i++) {
            LambdaQueryWrapper<User> userQueryWrapperr = new LambdaQueryWrapper<>();
            userQueryWrapperr.eq(User::getUsername, list.get(i).getName());
            User user = userService.getBaseMapper().selectOne(userQueryWrapperr);
            list.get(i).setAvatar(user.getAvatar());
            list.get(i).setUserNickname(user.getUserNickname());

        }


        Map<String, Object> map = new HashMap<>();
        map.put("datalist", list);
        List<Object> dataList = new ArrayList<>();
        dataList.add(map);
        return ResponseJson.success(dataList);
    }

    @Override
    public ResponseEntity delRecoverById(Long tid) {
        LambdaQueryWrapper<Recover> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Recover::getPid, tid);
        if (getBaseMapper().delete(lambdaQueryWrapper) > 0) {
            return ResponseJson.success("删除成功");
        } else {
            return ResponseJson.fail(400, "删除失败");
        }
    }
}




