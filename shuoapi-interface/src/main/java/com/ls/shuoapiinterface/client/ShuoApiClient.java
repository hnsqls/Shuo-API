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
public class ShuoApiClient {
    private String accessKey;
    private String secretKey;

    /**
     * 添加请求头信息
     *
     * @return
     */
    private HashMap<String, String> getHeaderMap() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("accessKey", accessKey);
        headers.put("secretKey", secretKey);
        return headers;
    }

    // 添加构造方法
    public ShuoApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getUserByGet(String name) {
        return HttpRequest.get("http://localhost:8849/api/user/name/" + name)
                .addHeaders(getHeaderMap())
                .execute()
                .body();
    }

    public String getUserByPost(String name) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        return HttpRequest.post("http://localhost:8849/api/user/name")
                .addHeaders(getHeaderMap())
                .form(paramMap)
                .execute()
                .body();
    }

    public String getUserByJson(User user) {
        String json = JSONUtil.toJsonStr(user);
        return HttpRequest.post("http://localhost:8849/api/user/json")
                .addHeaders(getHeaderMap())
                .body(json)
                .execute()
                .body();
    }
}
