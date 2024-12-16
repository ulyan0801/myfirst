package com.my.springboot.utils.system;

/*
   判断是linux系统还是win系统
 */
public class JudgeSystem {

    public static boolean isLinux() {
        return System.getProperty("os.name").toLowerCase().indexOf("linux") >= 0;
    }

    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }
}
