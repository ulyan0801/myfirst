package com.my.springboot.utils.Result;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ResponseJson {
    public static ResponseEntity success() {
        return new ResponseEntity(getDataMap(HttpStatus.OK.value(), "成功", true), HttpStatus.OK);
    }

    public static ResponseEntity success(int code) {
        return new ResponseEntity(getDataMap(code, "成功", true), HttpStatus.OK);
    }

    public static ResponseEntity success(String message) {

        return new ResponseEntity(getDataMap(HttpStatus.OK.value(), message, true), HttpStatus.OK);
    }

    public static <T> ResponseEntity<T> success(List<T> data) {

        return new ResponseEntity(getDataMap(HttpStatus.OK.value(), "成功", true, data), HttpStatus.OK);
    }

    public static <T> ResponseEntity<T> success(String message, List<T> data) {

        return new ResponseEntity(getDataMap(HttpStatus.OK.value(), message, true, data), HttpStatus.OK);
    }

    public static <T> void success(HttpServletResponse response, int code, String message, List<T> data) {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = null;
        response.setStatus(code);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            writer = response.getWriter();
            mapper.writeValue(writer, getDataMap(code, message, true, data));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }

    public static <T> void success(HttpServletResponse response, String message) {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = null;
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            writer = response.getWriter();
            mapper.writeValue(writer, getDataMap(HttpStatus.OK.value(), message, true));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }

    public static ResponseEntity fail(int code, String message) {
        return new ResponseEntity(getDataMap(code, message, false), HttpStatus.valueOf(code));
    }

    public static <T> void fail(HttpServletResponse response, int code, String message) {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = null;
        response.setStatus(code);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);//APPLICATION_JSON_VALUE中文乱码
        try {
            writer = response.getWriter();
            mapper.writeValue(writer, getDataMap(code, message, false));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }


    private static Map getDataMap(int code, String message, boolean success) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        map.put("message", message);
        map.put("success", success);
        map.put("data", new ArrayList<>());
        return map;
    }

    private static <T> Map getDataMap(int code, String message, boolean success, List<T> data) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        map.put("message", message);
        map.put("success", success);
        map.put("data", data);
        return map;
    }
}
