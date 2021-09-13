package com.jackson.gateway.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author JieXing
 * @Description:
 * @date 2021/9/13
 * @time 11:08
 */
@Component
@Data
public class BaseAuthorizationConfig {
    @Value(value = "${base.authorization}")
    private String baseAuthorization;
}
