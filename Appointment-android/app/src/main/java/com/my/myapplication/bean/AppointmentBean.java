package com.my.myapplication.bean;

//创建数据类
public class AppointmentBean {
    private Long id;


    private String leixing;

    public String getLeixing() {
        return leixing;
    }

    public void setLeixing(String leixing) {
        this.leixing = leixing;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getXingming() {
        return xingming;
    }

    public void setXingming(String xingming) {
        this.xingming = xingming;
    }

    private String xingming;
    private String content;
    private String qita;
    private String shijian;
    private String xingqi;
    private String shipin;
    private String leixingf;

    public String getLeixingf() {
        return leixingf;
    }

    public void setLeixingf(String leixingf) {
        this.leixingf = leixingf;
    }


    public String getShijian() {
        return shijian;
    }

    public void setShijian(String shijian) {
        this.shijian = shijian;
    }

    public String getXingqi() {
        return xingqi;
    }

    public void setXingqi(String xingqi) {
        this.xingqi = xingqi;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getShipin() {
        return shipin;
    }

    public void setShipin(String shipin) {
        this.shipin = shipin;
    }


    public String getQita() {
        return qita;
    }

    public void setQita(String qita) {
        this.qita = qita;
    }


}
