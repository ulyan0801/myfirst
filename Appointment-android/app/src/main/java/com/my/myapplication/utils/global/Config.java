package com.my.myapplication.utils.global;

//全局配置文件 如服务器地址 跳转界面Intetn的key 本地存储sp用到的Key等
public class Config {

    //网络请求基地址 如果是androidstudio自带的模拟器 IP可改为http://10.0.2.2:端口号 真机局域网ip
    public static String HTTP_BASE_URL = "http://10.0.2.2:8888";


    //注册
    public static String REGISTER_URL = HTTP_BASE_URL + "/register";
    public static String REGISTER_TAG = "register";

    public static String LOGIN_URL = HTTP_BASE_URL + "/login";
    public static String LOGIN_TAG = "login";

    public static String IS_REGISTER_URL = HTTP_BASE_URL + "/isregister";
    public static String I_SREGISTER_TAG = "isregister";

    public static String GETUSERBYUSERNAME_URL = HTTP_BASE_URL + "/getUserByUserName";
    public static String GETUSERBYUSERNAME_TAG = "getUserByUserName";


    public static String USERINFO_URL = HTTP_BASE_URL + "/userinfo";
    public static String USERINFO_TAG = "userinfo";

    public static String UPDATEFIELDS_URL = HTTP_BASE_URL + "/updatefields";
    public static String UPDATEFIELDS_TAG = "updatefields";


    public static String ADDDATA_URL = HTTP_BASE_URL + "/addData";
    public static String ADDDATA_TAG = "addData";

    public static String ADDSHIJIANDATA_URL = HTTP_BASE_URL + "/addShiJianData";
    public static String ADDSHIJIANDATA_TAG = "addShiJianData";


    public static String ADDYUYUEDATA_URL = HTTP_BASE_URL + "/addYuYueData";
    public static String ADDYUYUEDATA_TAG = "addYuYueData";

    public static String UPDATEYUYUE_URL = HTTP_BASE_URL + "/updateYuYue";
    public static String UPDATEYUYUE_TAG = "updateYuYue";


    public static String ONEUPLOADFILE_URL = HTTP_BASE_URL + "/oneUploadFile";
    public static String ONEUPLOADFILE_TAG = "oneUploadFile";


    public static String GETALLDATA_URL = HTTP_BASE_URL + "/getAllData";
    public static String GETALLDATA_TAG = "getAllData";

    public static String UPDATEDATABYID_URL = HTTP_BASE_URL + "/updateDataById";
    public static String UPDATEDATABYID_TAG = "updateDataById";


    public static String GETDATABYID_URL = HTTP_BASE_URL + "/getDataById";
    public static String GETDATABYID_TAG = "getDataById";


    public static String GETYUYUEDATABYTID_URL = HTTP_BASE_URL + "/getYuYueDataByTid";
    public static String GETYUYUEDATABYTID_TAG = "getYuYueDataByTid";


    public static String GETYUYUEDATABYTIDANDSHIJIANIDANDRIQI_URL = HTTP_BASE_URL + "/getYuYueDataByTidAndShijianidAndRiqi";
    public static String GETYUYUEDATABYTIDANDSHIJIANIDANDRIQID_TAG = "getYuYueDataByTidAndShijianidAndRiqi";


    public static String GETSHIJIANBYID_URL = HTTP_BASE_URL + "/getShiJianById";
    public static String GETSHIJIANBYID_TAG = "getShiJianById";


    public static String GETWODEYUYUE_URL = HTTP_BASE_URL + "/getWoDeYuYue";
    public static String GETWODEYUYUE_TAG = "getWoDeYuYue";

    public static String GETALLYUYUE_URL = HTTP_BASE_URL + "/getAllYuYue";
    public static String GETALLYUYUE_TAG = "getAllYuYue";


    public static String DELDATABYID_URL = HTTP_BASE_URL + "/delDataById";
    public static String DELDATABYID_TAG = "delDataById";

    public static String DELSHIJIANBYTID_URL = HTTP_BASE_URL + "/delShiJianByTid";
    public static String DELSHIJIANBYTID_TAG = "delShiJianByTid";

    public static String DELYUYUEBYID_URL = HTTP_BASE_URL + "/delYuYueByid";
    public static String DELYUYUEBYID_TAG = "delYuYueByid";


    public static String DELYUYUEBYTIDANDSHIJIANID_URL = HTTP_BASE_URL + "/delYuYueByTidAndShiJianid";
    public static String DELYUYUEBYTIDANDSHIJIANID_TAG = "delYuYueByTidAndShiJianid";

    public static String DELYUYUEBYTID_URL = HTTP_BASE_URL + "/delYuYueByTid";
    public static String DELYUYUEBYTID_TAG = "delYuYueByTid";


    public static String DELDIANZANTID_URL = HTTP_BASE_URL + "/delDianZanTid";
    public static String DELDIANZANTID_TAG = "delDianZanTid";

    public static String DELSHOUCANGTID_URL = HTTP_BASE_URL + "/delShouCangTid";
    public static String DELSHOUCANGTID_TAG = "delShouCangTid";

    public static String DELLIUYANTID_URL = HTTP_BASE_URL + "/delLiuYanTid";
    public static String DELLIUYANTID_TAG = "delLiuYanTid";


    public static String GETDATABYLEIXING_URL = HTTP_BASE_URL + "/getDataByLeiXing";
    public static String GETDATABYLEIXING_TAG = "getDataByLeiXing";


    public static String ISSHOUCANG_URL = HTTP_BASE_URL + "/isShouCang";
    public static String ISSHOUCANG_TAG = "isShouCang";


    public static String GETUSERSHOUCANG_URL = HTTP_BASE_URL + "/getUserShouCang";
    public static String GETUSERSHOUCANG_TAG = "getUserShouCang";

    public static String ADDANDREMOVESHOUCANG_URL = HTTP_BASE_URL + "/addAndRemoveShouCang";
    public static String ADDANDREMOVESHOUCANG_TAG = "addAndRemoveShouCang";


    public static String ISDIANZAN_URL = HTTP_BASE_URL + "/isDianZan";
    public static String ISDIANZAN_TAG = "isDianZan";


    public static String GETITEMDIANZAN_URL = HTTP_BASE_URL + "/getItemDianZan";
    public static String GETITEMDIANZAN_TAG = "getItemDianZan";

    public static String ADDANDREMOVEDIANZAN_URL = HTTP_BASE_URL + "/addAndRemoveDianZan";
    public static String ADDANDREMOVEDIANZAN_TAG = "addAndRemoveDianZan";

    public static String ADDRECOVERDATA_URL = HTTP_BASE_URL + "/addRecoverData";
    public static String ADDRECOVERDATA_TAG = "addRecoverData";


    public static String GETALLRECOVERDATA_URL = HTTP_BASE_URL + "/getAllRecoverData";
    public static String GETALLRECOVERDATA_TAG = "getAllRecoverData";

    public static String TOKEN_KEY = "token";

}
