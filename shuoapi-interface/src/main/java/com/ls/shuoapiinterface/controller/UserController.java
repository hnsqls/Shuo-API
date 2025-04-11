package com.ls.shuoapiinterface.controller;

import com.ls.shuoapiinterface.model.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/name/{name}")
    public String getUserByGet(@PathVariable String name){
        return "GET:hello " + name;
    }

    @PostMapping("/name")
    public String getUserByPost(@RequestParam String name){
        return "POST:hello " + name;
    }

    @PostMapping("/json")
    public String getUserByJson(@RequestBody User user) {
        return "POST Json :hello " + user.getName();
    }
}
