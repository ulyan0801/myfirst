package com.my.myapplication.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShiJianBean implements Serializable {


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long id;

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    private Long tid;

    public Integer getBianhao() {
        return bianhao;
    }

    public void setBianhao(Integer bianhao) {
        this.bianhao = bianhao;
    }

    private Integer bianhao;

    public Integer getRenshu() {
        return renshu;
    }

    public void setRenshu(Integer renshu) {
        this.renshu = renshu;
    }

    private Integer renshu;

    public Integer getShengyurenshu() {
        return shengyurenshu;
    }

    public void setShengyurenshu(Integer shengyurenshu) {
        this.shengyurenshu = shengyurenshu;
    }

    private Integer shengyurenshu;


    public String getYuyueshijian() {
        return yuyueshijian;
    }

    public void setYuyueshijian(String yuyueshijian) {
        this.yuyueshijian = yuyueshijian;
    }

    private String yuyueshijian;

    public String getJiage() {
        return jiage;
    }

    public void setJiage(String jiage) {
        this.jiage = jiage;
    }

    private String jiage;


    public List<String> getUsername() {
        return username;
    }

    public void setUsername(List<String> username) {
        this.username = username;
    }

    private List<String> username = new ArrayList<>();


    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    private boolean select = false;
}
