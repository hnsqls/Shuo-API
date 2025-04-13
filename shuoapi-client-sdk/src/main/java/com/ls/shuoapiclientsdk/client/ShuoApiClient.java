package com.ls.shuoapiclientsdk.client;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.ls.shuoapiclientsdk.model.User;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * 调用接口的客户端
 */

public class ShuoApiClient {
    private   String accessKey;
    private  String secretKey;

    /**
     * 添加请求头信息
     *
     * @return
     */
    private HashMap<String, String> getHeaderMap() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("accessKey", accessKey);
//        headers.put("secretKey", secretKey); 不能直接暴露
        headers.put("sign", getSign(accessKey,secretKey)); // 签名认证
        return headers;
    }

    public  static String getSign(String accessKey,String secretKey){
//        （accessKey+secretKey + userid + 盐）
        String SALT = "hnsqls";

        String sign = accessKey + SALT+ secretKey;
        // sha 256加密
        Digester sha256 = new Digester(DigestAlgorithm.SHA256);
        return sha256.digestHex(sign);
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
