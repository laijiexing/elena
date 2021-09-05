package com.jackson.gateway.authorization;

import cn.hutool.core.convert.Convert;
import com.jackson.gateway.constant.AuthConstant;
import com.jackson.gateway.constant.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author JieXing
 * @Description: 鉴权管理器，用于判断是否有资源的访问权限
 * @date 2021/9/5
 * @time 20:00
 */
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext object) {
        //从Redis中获取当前路径可访问角色列表
        URI uri = object.getExchange().getRequest().getURI();
        Object obj = redisTemplate.opsForHash().get(RedisConstant.RESOURCE_ROLES_MAP, uri.getPath());
        List<String> authorities = Convert.toList(String.class, obj);
        authorities = authorities.stream().map(i -> i = AuthConstant.AUTHORITY_PREFIX + i).collect(Collectors.toList());
        //认证通过且角色匹配的用户可访问当前路径
        return authentication
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(authorities::contains)
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }
}
