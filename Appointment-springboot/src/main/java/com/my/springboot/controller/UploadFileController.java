package com.my.springboot.controller;

import com.my.springboot.utils.Result.ResponseJson;
import com.my.springboot.utils.system.JudgeSystem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@Api(tags = "上传图片视频文件接口")
public class UploadFileController {

    //静态资源目录
    @Value("${windows.resources.path}")
    private String windowsResourcesPath;
    //静态资源目录
    @Value("${linux.resources.path}")
    private String linuxResourcesPath;

    @Value("${spring.mvc.static-path-pattern}")
    private String statics;

    @PostMapping("/oneUploadFile")
    @ApiOperation("单文件上传")
    public ResponseEntity oneUploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseJson.fail(400, "文件不能为空");
        }
        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf("."));
        String newFileName = new SimpleDateFormat("yyyyMMddHHmm").format(new Date()) + "-" + UUID.randomUUID() + ext;
        //判断系统类型
        File upload = null;
        File path = null;
        if (JudgeSystem.isLinux()) {
            path = new File(linuxResourcesPath);
        } else if (JudgeSystem.isWindows()) {
            path = new File(windowsResourcesPath);
        }

        upload = new File(path.getAbsolutePath());
        if (null == upload) {
            return ResponseJson.fail(400, "新增失败");
        }
        if (!upload.exists()) upload.mkdirs();
//添加分隔符
        String uploadPath = upload + File.separator;
        try {
            file.transferTo(new File(uploadPath + newFileName));

            if (statics.contains("*")) {
                statics = statics.replace("*", "");
            }
            Map<String, Object> map = new HashMap<>();
            map.put("imgUrl", statics + newFileName);
            List<Object> dataList = new ArrayList<>();
            dataList.add(map);
            return ResponseJson.success(dataList);
        } catch (Exception e) {
            return ResponseJson.fail(400, "新增失败");
        }

    }


}
