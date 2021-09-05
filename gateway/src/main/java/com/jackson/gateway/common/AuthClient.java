package com.jackson.gateway.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * @author JieXing
 * @Description: 认证逻辑
 * @date 2021/8/17
 * @time 18:33
 */
@Component
public class AuthClient {

    private RestTemplate restTemplate = new RestTemplate();

    @Value("${'${service.url.oauth}'+'/oauth/check_token'}")
    private String checkTokenUrl;

    public boolean hasPermissionControl(String url) {
        return url.startsWith("/elena");
    }

    public boolean accessAble(ServerHttpRequest request) {
        String token = request.getQueryParams().getFirst("token");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(checkTokenUrl).queryParam("token", token);
        URI url = builder.build().encode().toUri();

        HttpEntity<?> entity = new HttpEntity<>(request.getHeaders());

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            return response.getBody() != null;
        } catch (RestClientException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
