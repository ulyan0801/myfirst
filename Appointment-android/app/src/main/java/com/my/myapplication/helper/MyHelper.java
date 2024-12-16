package com.my.myapplication.helper;


import android.annotation.SuppressLint;
import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.my.myapplication.bean.AppointmentBean;
import com.my.myapplication.bean.RecoverBean;
import com.my.myapplication.bean.ShiJianBean;
import com.my.myapplication.bean.UserBean;
import com.my.myapplication.bean.WoDeYuYueBean;
import com.my.myapplication.bean.YuYueBean;
import com.my.myapplication.utils.http.HttpRequest;
import com.my.myapplication.utils.http.JsonBean;
import com.my.myapplication.utils.http.JsonUtil;
import com.my.myapplication.utils.http.TokenUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyHelper {

    private static MyHelper instance = null;


    public MyHelper(Context context) {


    }

    public static MyHelper getInstance(Context context) {

        if (instance == null) {
            instance = new MyHelper(context);
        }
        return instance;
    }


    // 添加一条数据 用户
    public long addUser(UserBean user) throws IOException {

        JsonBean jsonBean = HttpRequest.register(user);
        if (jsonBean.getCode() == 200) {

            return 1;
        }
        return -1;
    }


    /**
     * 登录校验账号密码 用户
     */
    public UserBean matchAccount(String name, String psw) throws IOException {
        UserBean re_user = null;
        JsonBean jsonBean = HttpRequest.login(name, psw);
        if (jsonBean.getCode() == 200) {
            TokenUtil.setToken(jsonBean.getData());
            JsonBean jsonBean1 = HttpRequest.getUser();
            if (jsonBean1.getCode() == 200) {
                re_user = JsonUtil.getJsonToBean(jsonBean1.getData()[0], UserBean.class);
            }

        }
        return re_user;
    }

    /**
     * 判断账号是否被注册 用户
     */
    public boolean isRegister(String name) throws IOException {

        JsonBean jsonBean = HttpRequest.IsRegister(name);
        return jsonBean.getCode() == 200;
    }

    //修改用户密码
    public long updateUserPsw(UserBean userBean) throws IOException {

        Long result = 0L;
        JsonBean jsonBean = HttpRequest.updateUserPsw(userBean);
        if (jsonBean.getCode() == 200) {

            return 1;
        }

        return result;
    }

    //修改手机号
    public long updateUserPhone(UserBean userBean) throws IOException {
        Long result = 0L;
        JsonBean jsonBean = HttpRequest.updateUserPhone(userBean);
        if (jsonBean.getCode() == 200) {

            return 1;
        }

        return result;
    }

    /**
     * 根据用户名查一条数据
     */

    @SuppressLint("Range")
    public UserBean getUserByUserName(String username) throws IOException {
        UserBean re_user = null;
        JsonBean jsonBean1 = HttpRequest.getUserByUserName(username);
        if (jsonBean1.getCode() == 200) {
            re_user = JsonUtil.getJsonToBean(jsonBean1.getData()[0], UserBean.class);
        }

        return re_user;
    }

    //修改用户昵称
    public long updateUserNickname(UserBean userBean) throws IOException {
        Long result = 0L;
        JsonBean jsonBean = HttpRequest.updateUserNickname(userBean);
        if (jsonBean.getCode() == 200) {

            return 1;
        }

        return result;
    }

    //修改用户头像
    public long updateUserTouXiang(UserBean userBean) throws IOException {
        Long result = 0L;
        JsonBean jsonBean = HttpRequest.updateUserTouXiang(userBean);
        if (jsonBean.getCode() == 200) {

            return 1;
        }

        return result;
    }


    // 添加一条数据
    public long addData(AppointmentBean bean) throws IOException {
        Long id = 0L;
        JsonBean jsonBean = HttpRequest.addData(bean);
        if (jsonBean.getCode() == 200) {
            JSONObject obj = JSON.parseObject(jsonBean.getData()[0]);
            id = obj.getLong("id");
            return id;
        }
        return id;

    }

    // 添加一条数据 预约时间段
    public long addShiJianData(ShiJianBean bean) throws IOException {

        Long id = 0L;
        JsonBean jsonBean = HttpRequest.addShiJianData(bean);
        if (jsonBean.getCode() == 200) {

            return 1;
        }

        return id;
    }

    // 添加一条数据 预约信息
    public long addYuYueData(YuYueBean bean) throws IOException {

        Long id = 0L;
        JsonBean jsonBean = HttpRequest.addYuYueData(bean);
        if (jsonBean.getCode() == 200) {

            return 1;
        }

        return id;
    }


    public long updateYuYue(YuYueBean bean) throws IOException {
        Long id = 0L;
        JsonBean jsonBean = HttpRequest.updateYuYue(bean);
        if (jsonBean.getCode() == 200) {

            return 1;
        }

        return id;
    }

    /**
     * 查预约数据
     */

    public List<YuYueBean> getYuYueDataByTid(Long id) throws IOException {
        List<YuYueBean> list = new ArrayList<>();

        JsonBean jsonBean = HttpRequest.getYuYueDataByTid(id);
        if (jsonBean.getCode() == 200) {
            list = JsonUtil.getJsonToList(jsonBean.getData(), YuYueBean.class);
        }
        return list;
    }


    /**
     * 查预约数据
     */

    public List<YuYueBean> getYuYueDataByTidAndShijianidAndRiqi(Long tid, Long shijianid, String riqi) throws IOException {

        List<YuYueBean> list = new ArrayList<>();
        JsonBean jsonBean = HttpRequest.getYuYueDataByTidAndShijianidAndRiqi(tid, shijianid, riqi);
        if (jsonBean.getCode() == 200) {
            list = JsonUtil.getJsonToList(jsonBean.getData(), YuYueBean.class);
        }

        return list;
    }


    /**
     * 查预约时间段数据
     */

    public List<ShiJianBean> getShiJianById(Long id) throws IOException {
        List<ShiJianBean> list = new ArrayList<>();

        JsonBean jsonBean = HttpRequest.getShiJianById(id);
        if (jsonBean.getCode() == 200) {
            list = JsonUtil.getJsonToList(jsonBean.getData(), ShiJianBean.class);
        }
        return list;
    }

    /**
     * 查我的预约 根据账号
     */

    public List<WoDeYuYueBean> getWoDeYuYue(String userName) throws IOException {
        List<WoDeYuYueBean> list = new ArrayList<>();

        JsonBean jsonBean = HttpRequest.getWoDeYuYue(userName);
        if (jsonBean.getCode() == 200) {
            list = JsonUtil.getJsonToList(jsonBean.getData(), WoDeYuYueBean.class);
        }
        return list;
    }

    public List<WoDeYuYueBean> getAllYuYue(Long tid, Long shijianid, String riqi) throws IOException {
        List<WoDeYuYueBean> list = new ArrayList<>();

        JsonBean jsonBean = HttpRequest.getAllYuYue(tid, shijianid, riqi);
        if (jsonBean.getCode() == 200) {
            list = JsonUtil.getJsonToList(jsonBean.getData(), WoDeYuYueBean.class);
        }
        return list;
    }

    // 添加一条数据
    public String oneUploadFile(String tupian) throws IOException {

        String url = "";
        JsonBean jsonBean = HttpRequest.oneUploadFile(tupian);
        if (jsonBean.getCode() == 200) {
            JSONObject obj = JSON.parseObject(jsonBean.getData()[0]);
            url = obj.getString("imgUrl");
            return url;
        }


        return url;

    }


    /**
     * 查所有信息
     */

    public List<AppointmentBean> getAllData() throws IOException {
        List<AppointmentBean> list = new ArrayList<>();

        JsonBean jsonBean = HttpRequest.getAllData();
        if (jsonBean.getCode() == 200) {
            list = JsonUtil.getJsonToList(jsonBean.getData(), AppointmentBean.class);
        }
        return list;
    }


    //根据分类查数据
    public List<AppointmentBean> getDataByLeiXing(String leixing) throws IOException {
        if ("全部".equals(leixing)) {
            return getAllData();
        }
        List<AppointmentBean> list = new ArrayList<>();

        JsonBean jsonBean = HttpRequest.getDataByLeiXing(leixing);
        if (jsonBean.getCode() == 200) {
            list = JsonUtil.getJsonToList(jsonBean.getData(), AppointmentBean.class);
        }
        return list;
    }

    /**
     * 根据ID查一条数据
     */

    public AppointmentBean getDataById(Long id) throws IOException {
        List<AppointmentBean> list = new ArrayList<>();
        JsonBean jsonBean = HttpRequest.getDataById(id);
        if (jsonBean.getCode() == 200) {
            list = JsonUtil.getJsonToList(jsonBean.getData(), AppointmentBean.class);
            return list.get(0);
        }
        return null;
    }


    /**
     * 根据id更新数据
     */
    public int updateDataById(AppointmentBean bean) throws IOException {

        JsonBean jsonBean = HttpRequest.updateDataById(bean);
        if (jsonBean.getCode() == 200) {

            return 1;
        }
        return -1;
    }

    /**
     * 根据id删除数据
     */

    public int delDataById(long id) throws IOException {
        JsonBean jsonBean = HttpRequest.delDataById(id);
        if (jsonBean.getCode() == 200) {
            return 1;
        }
        return -1;
    }


    /**
     * 根据id删除 预约时间段
     */

    public int delShiJianByTid(Long id) throws IOException {
        JsonBean jsonBean = HttpRequest.delShiJianByTid(id);
        if (jsonBean.getCode() == 200) {
            return 1;
        }
        return -1;
    }


    /**
     * 根据id删除数据
     */

    public int delYuYueByTid(Long tid) throws IOException {
        JsonBean jsonBean = HttpRequest.delYuYueByTid(tid);
        if (jsonBean.getCode() == 200) {
            return 1;
        }
        return -1;
    }

    public int delYuYueByid(Long id) throws IOException {
        JsonBean jsonBean = HttpRequest.delYuYueByid(id);
        if (jsonBean.getCode() == 200) {
            return 1;
        }
        return -1;
    }

    //删点赞
    public int delDianZanTid(Long tid) throws IOException {
        JsonBean jsonBean = HttpRequest.delDianZanTid(tid);
        if (jsonBean.getCode() == 200) {
            return 1;
        }
        return -1;
    }

    //删收藏
    public int delShouCangTid(Long tid) throws IOException {
        JsonBean jsonBean = HttpRequest.delShouCangTid(tid);
        if (jsonBean.getCode() == 200) {
            return 1;
        }
        return -1;
    }

    //删留言
    public int delLiuYanTid(Long tid) throws IOException {
        JsonBean jsonBean = HttpRequest.delLiuYanTid(tid);
        if (jsonBean.getCode() == 200) {
            return 1;
        }
        return -1;
    }

    /**
     * 是否收藏购买页面
     */
    @SuppressLint("Range")
    public boolean isShouCang(String username, Long tid) throws IOException {

        JsonBean jsonBean = HttpRequest.isShouCang(username, tid);
        if (jsonBean.getCode() == 200) {
            return true;
        }
        return false;
    }

    /**
     * 添加收藏购买页面
     */
    public long addAndRemoveShouCang(String username, Long tid) throws IOException {
        JsonBean jsonBean = HttpRequest.addAndRemoveShouCang(username, tid);
        if ("1".equals(jsonBean.getMessage())) {
            return 1;
        } else {
            return 2;
        }

    }

    /**
     * 查询收藏数据
     */
    public List<AppointmentBean> getUserShouCang(String username) throws IOException {
        List<AppointmentBean> list = new ArrayList<>();

        JsonBean jsonBean = HttpRequest.getUserShouCang(username);
        if (jsonBean.getCode() == 200) {
            list = JsonUtil.getJsonToList(jsonBean.getData(), AppointmentBean.class);
        }
        return list;
    }

    /**
     * 是否点赞
     */
    public boolean isDianZan(String username, Long tid) throws IOException {
        JsonBean jsonBean = HttpRequest.isDianZan(username, tid);
        if (jsonBean.getCode() == 200) {
            return true;
        }
        return false;
    }

    /**
     * 添加移除点赞
     */
    public long addAndRemoveDianZan(String username, Long tid) throws IOException {
        JsonBean jsonBean = HttpRequest.addAndRemoveDianZan(username, tid);
        if ("1".equals(jsonBean.getMessage())) {
            return 1;
        } else {
            return 2;
        }

    }

    /**
     * 查询点赞数量
     */
    public Integer getItemDianZan(Long tid) throws IOException {

        Integer cnt = 0;
        JsonBean jsonBean = HttpRequest.getItemDianZan(tid);
        if (jsonBean.getCode() == 200) {
            JSONObject obj = JSON.parseObject(jsonBean.getData()[0]);
            cnt = obj.getInteger("cnt");
            return cnt;
        }
        return cnt;
    }

    // 添加一条数据 留言

    public long addRecoverData(RecoverBean bean) throws IOException {
        JsonBean jsonBean = HttpRequest.addRecoverData(bean);
        if (jsonBean.getCode() == 200) {
            return 1;
        }
        return -1;
    }

    /**
     * 查所有数据 留言
     */

    @SuppressLint("Range")
    public List<RecoverBean> getAllRecoverData(long pid) throws IOException {
        List<RecoverBean> list = new ArrayList<>();

        JsonBean jsonBean = HttpRequest.getAllRecoverData(pid);
        if (jsonBean.getCode() == 200) {
            list = JsonUtil.getJsonToList(jsonBean.getData(), RecoverBean.class);
        }
        return list;
    }
}
