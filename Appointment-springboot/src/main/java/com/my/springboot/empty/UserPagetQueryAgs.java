package com.my.springboot.empty;

import lombok.Data;

import java.util.List;

@Data
public class UserPagetQueryAgs {
    private int page;
    private int limit;
    private String accountOrNickName;
    private String addr;
    private List<String> registerTime;
    private String sex;
    private String status;

}
