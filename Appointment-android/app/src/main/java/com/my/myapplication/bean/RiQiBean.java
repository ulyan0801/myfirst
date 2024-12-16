package com.my.myapplication.bean;

public class RiQiBean {

    public RiQiBean(String riqi) {
        this.riqi = riqi;
    }

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
}
