package com.my.myapplication.bean;

import java.io.Serializable;

public class YuYueBean implements Serializable {


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


    public Long getShijianid() {
        return shijianid;
    }

    public void setShijianid(Long shijianid) {
        this.shijianid = shijianid;
    }

    private Long shijianid;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;

    public String getRiqi() {
        return riqi;
    }

    public void setRiqi(String riqi) {
        this.riqi = riqi;
    }

    private String riqi;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    private boolean select = false;

    public Integer getHaoma() {
        return haoma;
    }

    public void setHaoma(Integer haoma) {
        this.haoma = haoma;
    }

    private Integer haoma;
}
