package com.ls.shuoapiclientsdk;

import com.ls.shuoapiclientsdk.client.ShuoApiClient;
import lombok.Data;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@Data
@ConfigurationProperties(prefix = "shuo.api")
public class ShuoApiClientConfig {

    private   String accessKey;
    private  String secretKey;

    @Bean
    public ShuoApiClient shuoApiClient(){
        return new ShuoApiClient(accessKey,secretKey);
    }

}
