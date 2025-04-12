package com.ls.shuoapiinterface.controller;

import com.ls.shuoapiinterface.client.ShuoApiClient;
import com.ls.shuoapiinterface.model.User;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/name/{name}")
    public String getUserByGet(@PathVariable String name, HttpServletRequest request){
        String accessKey = request.getHeader("accessKey");
        String sign = request.getHeader("sign");

        // todo  查数据库根据accessKey 查secretKey
        String secretKey = "hnsqls";// 模拟了先
        if (secretKey == null || accessKey.isEmpty()){
            return "accessKey or secretKey is wrong";
        }

        String checkSign = ShuoApiClient.getSign(accessKey,secretKey);
        if (!Objects.equals(accessKey, "hnsqls") || !Objects.equals(sign, checkSign)) {
            return "accessKey or secretKey is wrong";
        }

        return "GET:hello " + name;
    }

    @PostMapping("/name")
    public String getUserByPost(@RequestParam String name,HttpServletRequest request){
        String accessKey = request.getHeader("accessKey");
        String sign = request.getHeader("sign");

        // todo  查数据库根据accessKey 查secretKey
        String secretKey = "hnsqls";// 模拟了先
        if (secretKey == null || accessKey.isEmpty()){
            return "accessKey or secretKey is wrong";
        }

        String checkSign = ShuoApiClient.getSign(accessKey,secretKey);
        if (!Objects.equals(accessKey, "hnsqls") || !Objects.equals(sign, checkSign)) {
            return "accessKey or secretKey is wrong";
        }
        return "POST:hello " + name;
    }

    @PostMapping("/json")
    public String getUserByJson(@RequestBody User user,HttpServletRequest request) {
        String accessKey = request.getHeader("accessKey");
        String sign = request.getHeader("sign");

        // todo  查数据库根据accessKey 查secretKey
        String secretKey = "hnsqls";// 模拟了先
        if (secretKey == null || accessKey.isEmpty()){
            return "accessKey or secretKey is wrong";
        }

        String checkSign = ShuoApiClient.getSign(accessKey,secretKey);
        if (!Objects.equals(accessKey, "hnsqls") || !Objects.equals(sign, checkSign)) {
            return "accessKey or secretKey is wrong";
        }
        return "POST Json :hello " + user.getName();
    }
}
