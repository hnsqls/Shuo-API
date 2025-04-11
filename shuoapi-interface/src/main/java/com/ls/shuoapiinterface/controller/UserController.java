package com.ls.shuoapiinterface.controller;

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
        String secretKey = request.getHeader("secretKey");
        if (!Objects.equals(accessKey, "hnsqls") || !Objects.equals(secretKey, "hnsqls")) {
            return "accessKey or secretKey is wrong";
        }

        return "GET:hello " + name;
    }

    @PostMapping("/name")
    public String getUserByPost(@RequestParam String name,HttpServletRequest request){
        String accessKey = request.getHeader("accessKey");
        String secretKey = request.getHeader("secretKey");
        if (!Objects.equals(accessKey, "hnsqls") || !Objects.equals(secretKey, "hnsqls")) {
            return "accessKey or secretKey is wrong";
        }
        return "POST:hello " + name;
    }

    @PostMapping("/json")
    public String getUserByJson(@RequestBody User user,HttpServletRequest request) {
        String accessKey = request.getHeader("accessKey");
        String secretKey = request.getHeader("secretKey");
        if (!Objects.equals(accessKey, "hnsqls") || !Objects.equals(secretKey, "hnsqls")) {
            return "accessKey or secretKey is wrong";
        }
        return "POST Json :hello " + user.getName();
    }
}
