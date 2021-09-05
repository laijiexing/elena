package com.jackson.gateway.common;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;

/**
 * @author JieXing
 * @Description:
 * @date 2021/8/17
 * @time 17:07
 */
@Component
@Data
public class ClientConfig {
    @Value("${security.oauth2.client.client-id}")
    private String clientId;

    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;

    public String getBasicHeader(){
        return "Basic" + Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());
    }
}
