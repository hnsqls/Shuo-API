package com.ls.shuoapiinterface.client;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.ls.shuoapiinterface.model.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * 调用接口的客户端
 */
@Component
public class ShuoApiClient {

    public String getUserByGet(String name){
        return HttpUtil.get("http://localhost:8849/api/user/name/" + name);
    }

    public String getUserByPost(String name){
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        return HttpUtil.post("http://localhost:8849/api/user/name", paramMap);
    }

    public String getUserByJson(User user) {
        String json = JSONUtil.toJsonStr(user);
        HttpResponse execute = HttpRequest.post("http://localhost:8849/api/user/json")
                .body(json)
                .execute();
        return execute.body();
    }
}
