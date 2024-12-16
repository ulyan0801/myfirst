package com.my.myapplication.utils.http;


import com.alibaba.fastjson.JSON;
import com.my.myapplication.bean.AppointmentBean;
import com.my.myapplication.bean.RecoverBean;
import com.my.myapplication.bean.ShiJianBean;
import com.my.myapplication.bean.UserBean;
import com.my.myapplication.bean.YuYueBean;
import com.my.myapplication.utils.global.Config;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.RequestBody;

//网络请求api
public class HttpRequest {

    /**
     * 取消网络请求
     */
    public static void cancel(String tag) {
        HttpClient.getInstance().cancel(tag);
    }


    public static JsonBean register(UserBean userBean) throws IOException {
        String beanToJson = JsonUtil.getBeanToJson(userBean);
        MediaType mediaType = MediaType.Companion.parse("application/json;charset=utf-8");
        RequestBody body = RequestBody.Companion.create(beanToJson, mediaType);
        return JSON.parseObject(HttpClient.getInstance().post(Config.REGISTER_URL, Config.REGISTER_TAG)
                .upRequestBody(body)
                .execute().body().string(), JsonBean.class);

    }


    public static JsonBean login(String name, String psw) throws IOException {

        return JSON.parseObject(
                HttpClient.getInstance().post(Config.LOGIN_URL, Config.LOGIN_TAG)
                        .params("username", name)
                        .params("password", psw)
                        .execute().body().string(), JsonBean.class);
    }

    // 获取数据 用户
    public static JsonBean getUser() throws IOException {
        return JSON.parseObject(
                HttpClient.getInstance().post(Config.USERINFO_URL, Config.USERINFO_TAG)
                        .headers("Authorization", " " + TokenUtil.getToken())
                        .execute().body().string(), JsonBean.class);
    }

    public static JsonBean IsRegister(String username) throws IOException {
        return JSON.parseObject(
                HttpClient.getInstance().post(Config.IS_REGISTER_URL, Config.I_SREGISTER_TAG)
                        .params("username", username)
                        .execute().body().string(), JsonBean.class);

    }

    public static JsonBean getUserByUserName(String username) throws IOException {
        return JSON.parseObject(
                HttpClient.getInstance().post(Config.GETUSERBYUSERNAME_URL, Config.GETUSERBYUSERNAME_TAG)
                        .headers("Authorization", " " + TokenUtil.getToken())
                        .params("username", username)
                        .execute().body().string(), JsonBean.class);
    }


    //修改用户密码
    public static JsonBean updateUserPsw(UserBean userBean) throws IOException {
        String beanToJson = JsonUtil.getBeanToJson(userBean);
        MediaType mediaType = MediaType.Companion.parse("application/json;charset=utf-8");
        RequestBody body = RequestBody.Companion.create(beanToJson, mediaType);
        return JSON.parseObject(
                HttpClient.getInstance().post(Config.UPDATEFIELDS_URL, Config.UPDATEFIELDS_TAG)
                        .headers("Authorization", " " + TokenUtil.getToken())
                        .upRequestBody(body)
                        .execute().body().string(), JsonBean.class);
    }

    //修改手机号
    public static JsonBean updateUserPhone(UserBean userBean) throws IOException {
        String beanToJson = JsonUtil.getBeanToJson(userBean);
        MediaType mediaType = MediaType.Companion.parse("application/json;charset=utf-8");
        RequestBody body = RequestBody.Companion.create(beanToJson, mediaType);
        return JSON.parseObject(
                HttpClient.getInstance().post(Config.UPDATEFIELDS_URL, Config.UPDATEFIELDS_URL)
                        .headers("Authorization", " " + TokenUtil.getToken())
                        .upRequestBody(body)
                        .execute().body().string(), JsonBean.class);
    }


    //修改用户昵称
    public static JsonBean updateUserNickname(UserBean userBean) throws IOException {
        String beanToJson = JsonUtil.getBeanToJson(userBean);
        MediaType mediaType = MediaType.Companion.parse("application/json;charset=utf-8");
        RequestBody body = RequestBody.Companion.create(beanToJson, mediaType);
        return JSON.parseObject(
                HttpClient.getInstance().post(Config.UPDATEFIELDS_URL, Config.UPDATEFIELDS_URL)
                        .headers("Authorization", " " + TokenUtil.getToken())
                        .upRequestBody(body)
                        .execute().body().string(), JsonBean.class);

    }

    //修改用户头像
    public static JsonBean updateUserTouXiang(UserBean userBean) throws IOException {

        String beanToJson = JsonUtil.getBeanToJson(userBean);
        MediaType mediaType = MediaType.Companion.parse("application/json;charset=utf-8");
        RequestBody body = RequestBody.Companion.create(beanToJson, mediaType);
        return JSON.parseObject(
                HttpClient.getInstance().post(Config.UPDATEFIELDS_URL, Config.UPDATEFIELDS_URL)
                        .headers("Authorization", " " + TokenUtil.getToken())
                        .upRequestBody(body)
                        .execute().body().string(), JsonBean.class);
    }

    // 添加一条数据
    public static JsonBean addData(AppointmentBean bean) throws IOException {
        String beanToJson = JsonUtil.getBeanToJson(bean);
        MediaType mediaType = MediaType.Companion.parse("application/json;charset=utf-8");
        RequestBody body = RequestBody.Companion.create(beanToJson, mediaType);
        return JSON.parseObject(HttpClient.getInstance().post(Config.ADDDATA_URL, Config.ADDDATA_TAG)
                .headers("Authorization", " " + TokenUtil.getToken())
                .upRequestBody(body)
                .execute().body().string(), JsonBean.class);


    }

    //  添加  预约时间段
    public static JsonBean addShiJianData(ShiJianBean bean) throws IOException {
        String beanToJson = JsonUtil.getBeanToJson(bean);
        MediaType mediaType = MediaType.Companion.parse("application/json;charset=utf-8");
        RequestBody body = RequestBody.Companion.create(beanToJson, mediaType);
        return JSON.parseObject(HttpClient.getInstance().post(Config.ADDSHIJIANDATA_URL, Config.ADDSHIJIANDATA_TAG)
                .headers("Authorization", " " + TokenUtil.getToken())
                .upRequestBody(body)
                .execute().body().string(), JsonBean.class);


    }

    //  添加  预约信息
    public static JsonBean addYuYueData(YuYueBean bean) throws IOException {
        String beanToJson = JsonUtil.getBeanToJson(bean);
        MediaType mediaType = MediaType.Companion.parse("application/json;charset=utf-8");
        RequestBody body = RequestBody.Companion.create(beanToJson, mediaType);
        return JSON.parseObject(HttpClient.getInstance().post(Config.ADDYUYUEDATA_URL, Config.ADDYUYUEDATA_TAG)
                .headers("Authorization", " " + TokenUtil.getToken())
                .upRequestBody(body)
                .execute().body().string(), JsonBean.class);


    }


    public static JsonBean updateYuYue(YuYueBean bean) throws IOException {

        String beanToJson = JsonUtil.getBeanToJson(bean);
        MediaType mediaType = MediaType.Companion.parse("application/json;charset=utf-8");
        RequestBody body = RequestBody.Companion.create(beanToJson, mediaType);
        return JSON.parseObject(HttpClient.getInstance().post(Config.UPDATEYUYUE_URL, Config.UPDATEYUYUE_TAG)
                .headers("Authorization", " " + TokenUtil.getToken())
                .upRequestBody(body)
                .execute().body().string(), JsonBean.class);


    }

    // 添加一张图片 或视频 文件

    public static JsonBean oneUploadFile(String tupian) throws IOException {


        return JSON.parseObject(
                HttpClient.getInstance().post(Config.ONEUPLOADFILE_URL, Config.ONEUPLOADFILE_TAG)
                        .headers("Authorization", " " + TokenUtil.getToken())
                        .params("file", new File(tupian))
                        .execute().body().string(), JsonBean.class);


    }

    /**
     * 查预约数据
     */

    public static JsonBean getYuYueDataByTid(Long Id) throws IOException {

        return JSON.parseObject(
                HttpClient.getInstance().post(Config.GETYUYUEDATABYTID_URL, Config.GETYUYUEDATABYTID_TAG)
                        .headers("Authorization", " " + TokenUtil.getToken())
                        .params("Id", Id)
                        .execute().body().string(), JsonBean.class);

    }


    /**
     * 查预约数据
     */

    public static JsonBean getYuYueDataByTidAndShijianidAndRiqi(Long tid, Long shijianid, String riqi) throws IOException {
        return JSON.parseObject(
                HttpClient.getInstance().post(Config.GETYUYUEDATABYTIDANDSHIJIANIDANDRIQI_URL, Config.GETYUYUEDATABYTIDANDSHIJIANIDANDRIQID_TAG)
                        .headers("Authorization", " " + TokenUtil.getToken())
                        .params("tid", tid)
                        .params("shijianid", shijianid)
                        .params("riqi", riqi)
                        .execute().body().string(), JsonBean.class);
    }

    /**
     * 查预约时间段
     */
    public static JsonBean getShiJianById(Long Id) throws IOException {

        return JSON.parseObject(
                HttpClient.getInstance().post(Config.GETSHIJIANBYID_URL, Config.GETSHIJIANBYID_TAG)
                        .headers("Authorization", " " + TokenUtil.getToken())
                        .params("Id", Id)
                        .execute().body().string(), JsonBean.class);

    }


    //查我的预约 根据账号
    public static JsonBean getWoDeYuYue(String userName) throws IOException {

        return JSON.parseObject(
                HttpClient.getInstance().post(Config.GETWODEYUYUE_URL, Config.GETWODEYUYUE_TAG)
                        .headers("Authorization", " " + TokenUtil.getToken())
                        .execute().body().string(), JsonBean.class);

    }

    public static JsonBean getAllYuYue(Long tid, Long shijianid, String riqi) throws IOException {

        return JSON.parseObject(
                HttpClient.getInstance().post(Config.GETALLYUYUE_URL, Config.GETALLYUYUE_TAG)
                        .headers("Authorization", " " + TokenUtil.getToken())
                        .params("tid", tid)
                        .params("shijianid", shijianid)
                        .params("riqi", riqi)
                        .execute().body().string(), JsonBean.class);

    }

    public static JsonBean getAllData() throws IOException {

        return JSON.parseObject(
                HttpClient.getInstance().post(Config.GETALLDATA_URL, Config.GETALLDATA_TAG)
                        .headers("Authorization", " " + TokenUtil.getToken())
                        .execute().body().string(), JsonBean.class);

    }


    /**
     * 根据分类查数据
     */

    public static JsonBean getDataByLeiXing(String leixing) throws IOException {
        return JSON.parseObject(
                HttpClient.getInstance().post(Config.GETDATABYLEIXING_URL, Config.GETDATABYLEIXING_TAG)
                        .headers("Authorization", " " + TokenUtil.getToken())
                        .params("leixing", leixing)
                        .execute().body().string(), JsonBean.class);
    }


    public static JsonBean getDataById(Long Id) throws IOException {

        return JSON.parseObject(
                HttpClient.getInstance().post(Config.GETDATABYID_URL, Config.GETDATABYID_TAG)
                        .headers("Authorization", " " + TokenUtil.getToken())
                        .params("Id", Id)
                        .execute().body().string(), JsonBean.class);

    }

    public static JsonBean updateDataById(AppointmentBean bean) throws IOException {
        String beanToJson = JsonUtil.getBeanToJson(bean);
        MediaType mediaType = MediaType.Companion.parse("application/json;charset=utf-8");
        RequestBody body = RequestBody.Companion.create(beanToJson, mediaType);
        return JSON.parseObject(HttpClient.getInstance().post(Config.UPDATEDATABYID_URL, Config.UPDATEDATABYID_TAG)
                .headers("Authorization", " " + TokenUtil.getToken())
                .upRequestBody(body)
                .execute().body().string(), JsonBean.class);

    }


    public static JsonBean delDataById(Long Id) throws IOException {

        return JSON.parseObject(
                HttpClient.getInstance().post(Config.DELDATABYID_URL, Config.DELDATABYID_TAG)
                        .headers("Authorization", " " + TokenUtil.getToken())
                        .params("Id", Id)
                        .execute().body().string(), JsonBean.class);

    }

    /**
     * 根据id删除数据 预约时间段
     */

    public static JsonBean delShiJianByTid(Long Id) throws IOException {

        return JSON.parseObject(
                HttpClient.getInstance().post(Config.DELSHIJIANBYTID_URL, Config.DELSHIJIANBYTID_TAG)
                        .headers("Authorization", " " + TokenUtil.getToken())
                        .params("Id", Id)
                        .execute().body().string(), JsonBean.class);

    }

    public static JsonBean delYuYueByid(Long Id) throws IOException {

        return JSON.parseObject(
                HttpClient.getInstance().post(Config.DELYUYUEBYID_URL, Config.DELYUYUEBYID_TAG)
                        .headers("Authorization", " " + TokenUtil.getToken())
                        .params("Id", Id)
                        .execute().body().string(), JsonBean.class);

    }


    /**
     * 根据id删除数据
     */

    public static JsonBean delYuYueByTid(Long tid) throws IOException {

        return JSON.parseObject(
                HttpClient.getInstance().post(Config.DELYUYUEBYTID_URL, Config.DELYUYUEBYTID_TAG)
                        .headers("Authorization", " " + TokenUtil.getToken())
                        .params("tid", tid)
                        .execute().body().string(), JsonBean.class);

    }


    //删点赞
    public static JsonBean delDianZanTid(Long tid) throws IOException {
        return JSON.parseObject(
                HttpClient.getInstance().post(Config.DELDIANZANTID_URL, Config.DELDIANZANTID_TAG)
                        .headers("Authorization", " " + TokenUtil.getToken())
                        .params("tid", tid)
                        .execute().body().string(), JsonBean.class);
    }

    //删收藏
    public static JsonBean delShouCangTid(Long tid) throws IOException {
        return JSON.parseObject(
                HttpClient.getInstance().post(Config.DELSHOUCANGTID_URL, Config.DELSHOUCANGTID_TAG)
                        .headers("Authorization", " " + TokenUtil.getToken())
                        .params("tid", tid)
                        .execute().body().string(), JsonBean.class);
    }

    //删留言
    public static JsonBean delLiuYanTid(Long tid) throws IOException {
        return JSON.parseObject(
                HttpClient.getInstance().post(Config.DELLIUYANTID_URL, Config.DELLIUYANTID_TAG)
                        .headers("Authorization", " " + TokenUtil.getToken())
                        .params("tid", tid)
                        .execute().body().string(), JsonBean.class);
    }

    public static JsonBean isShouCang(String username, Long tid) throws IOException {
        return JSON.parseObject(
                HttpClient.getInstance().post(Config.ISSHOUCANG_URL, Config.ISSHOUCANG_TAG)
                        .headers("Authorization", " " + TokenUtil.getToken())
                        .params("tid", tid)
                        .params("username", username)
                        .execute().body().string(), JsonBean.class);

    }

    public static JsonBean getUserShouCang(String username) throws IOException {
        return JSON.parseObject(
                HttpClient.getInstance().post(Config.GETUSERSHOUCANG_URL, Config.GETUSERSHOUCANG_TAG)
                        .headers("Authorization", " " + TokenUtil.getToken())
                        .execute().body().string(), JsonBean.class);

    }

    public static JsonBean addAndRemoveShouCang(String username, Long tid) throws IOException {
        return JSON.parseObject(
                HttpClient.getInstance().post(Config.ADDANDREMOVESHOUCANG_URL, Config.ADDANDREMOVESHOUCANG_TAG)
                        .headers("Authorization", " " + TokenUtil.getToken())
                        .params("tid", tid)
                        .params("username", username)
                        .execute().body().string(), JsonBean.class);

    }


    public static JsonBean isDianZan(String username, Long tid) throws IOException {
        return JSON.parseObject(
                HttpClient.getInstance().post(Config.ISDIANZAN_URL, Config.ISDIANZAN_TAG)
                        .headers("Authorization", " " + TokenUtil.getToken())
                        .params("tid", tid)
                        .params("username", username)
                        .execute().body().string(), JsonBean.class);

    }


    /**
     * 查询点赞数量
     */

    public static JsonBean getItemDianZan(Long tid) throws IOException {
        return JSON.parseObject(
                HttpClient.getInstance().post(Config.GETITEMDIANZAN_URL, Config.GETITEMDIANZAN_TAG)
                        .headers("Authorization", " " + TokenUtil.getToken())
                        .params("tid", tid)
                        .execute().body().string(), JsonBean.class);
    }


    public static JsonBean addAndRemoveDianZan(String username, Long tid) throws IOException {
        return JSON.parseObject(
                HttpClient.getInstance().post(Config.ADDANDREMOVEDIANZAN_URL, Config.ADDANDREMOVEDIANZAN_TAG)
                        .headers("Authorization", " " + TokenUtil.getToken())
                        .params("tid", tid)
                        .params("username", username)
                        .execute().body().string(), JsonBean.class);

    }


    /**
     * 查所有数据 留言
     */


    public static JsonBean getAllRecoverData(long pid) throws IOException {
        return JSON.parseObject(
                HttpClient.getInstance().post(Config.GETALLRECOVERDATA_URL, Config.GETALLRECOVERDATA_TAG)
                        .headers("Authorization", " " + TokenUtil.getToken())
                        .params("pid", pid)
                        .execute().body().string(), JsonBean.class);

    }

    // 添加一条数据 留言
    public static JsonBean addRecoverData(RecoverBean bean) throws IOException {

        //获取当前时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        bean.setTime(str);


        String beanToJson = JsonUtil.getBeanToJson(bean);
        MediaType mediaType = MediaType.Companion.parse("application/json;charset=utf-8");
        RequestBody body = RequestBody.Companion.create(beanToJson, mediaType);
        return JSON.parseObject(
                HttpClient.getInstance().post(Config.ADDRECOVERDATA_URL, Config.ADDRECOVERDATA_TAG)
                        .headers("Authorization", " " + TokenUtil.getToken())
                        .upRequestBody(body)
                        .execute().body().string(), JsonBean.class);

    }
}
