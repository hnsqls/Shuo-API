package com.ls.shuoapiinterface.client;

import com.ls.shuoapiinterface.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShuoApiClientTest {


    @Test
    void getUserByGet() {
        ShuoApiClient shuoApiClient = new ShuoApiClient("hnsqls", "hnsqls");
        String result1 = shuoApiClient.getUserByGet("hnsqls");
        String result2 = shuoApiClient.getUserByPost("hnsqls");
        String result3 = shuoApiClient.getUserByJson(new User("hnsqls", "123456"));

        System.out.println("result1 = " + result1);
        System.out.println("result2 = " + result2);
        System.out.println("result3 = " + result3);
    }

}