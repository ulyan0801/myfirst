package com.my.springboot.utils.file;

import com.my.springboot.utils.system.JudgeSystem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GetDownloadsFileNameFromDisk {
    List<Map<String, Object>> mapList = new ArrayList<>();
    @Value("${windows.resources.path}")
    private String windowsResourcesPath;
    //静态资源目录
    @Value("${linux.resources.path}")
    private String linuxResourcesPath;

    public List<Map<String, Object>> getFilesName() {
        mapList.clear();
        File file = null;
        if (JudgeSystem.isLinux()) {
            file = new File(linuxResourcesPath);
        } else if (JudgeSystem.isWindows()) {
            file = new File(windowsResourcesPath);
        }

        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            traverseFile(tempList[i].toString());
        }
        return mapList;
    }

    public void traverseFile(String str) {
        File file = new File(str);

        if (file.isFile()) {
            //文件全路径
            //System.out.println("-------------------"+file);
            Map<String, Object> map = new HashMap<>();
            map.put("fileName", file.getName());
            map.put("filePath", file);
            mapList.add(map);
        } else if (file.isDirectory()) { // 是文件夹
//            子目录名
            //System.out.println("-------------------"+file.getName());
            File[] tempList = file.listFiles(); // 获得子文件
            if (null != tempList) {
                for (File file2 : tempList) {      // 递归
                    if (!file2.isHidden()) {  // 判断是否是隐藏文件夹
                        traverseFile(file2.toString());
                    }
                }
            }
        }
    }
}

