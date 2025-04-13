package com.ls.shuoapiinterface;

import com.ls.shuoapiclientsdk.client.ShuoApiClient;
import com.ls.shuoapiclientsdk.model.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SdkClientTest {


    @Autowired
    private ShuoApiClient shuoApiClient;

    @Test
    public void test() {
        String result0 = shuoApiClient.getUserByGet("hnsqls");
        String result1 = shuoApiClient.getUserByPost("hnsqls");
        String result2 = shuoApiClient.getUserByJson(new User("hnsqls", "123456"));

        System.out.println("result0 = " + result0);
        System.out.println("result1 = " + result1);
        System.out.println("result2 = " + result2);
    }

}
