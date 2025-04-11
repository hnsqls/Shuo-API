package com.ls.shuoapiinterface.client;

import com.ls.shuoapiinterface.model.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestParam;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShuoApiClientTest {

    @Resource
    private ShuoApiClient shuoApiClient;
    @Test
    void getUserByGet() {
        String result1 = shuoApiClient.getUserByGet("hnsqls");
        String result2 = shuoApiClient.getUserByPost("hnsqls");
        String result3 = shuoApiClient.getUserByJson(new User("hnsqls", "123456"));

        System.out.println("result1 = " + result1);
        System.out.println("result2 = " + result2);
        System.out.println("result3 = " + result3);
    }

}