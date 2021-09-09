package com.jackson.authentication.config.oauth;

import com.jackson.authentication.jwt.JwtTokenEnhancer;
import com.jackson.authentication.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.JdbcClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

/**
 * @author JieXing
 * @Description: oauth认证服务器配置
 * @date 2021/8/12
 * @time 22:29
 */
@EnableAuthorizationServer
@Configuration
public class Oauth2Config extends AuthorizationServerConfigurerAdapter {

    @Qualifier(value = "myPasswordEncoder")
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Qualifier(value = "myUserDetailsService")
    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private KeyPair keyPair;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier(value = "myJwtTokenEnhancer")
    private JwtTokenEnhancer jwtTokenEnhancer;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        JdbcClientDetailsServiceBuilder jdbc = clients.jdbc(dataSource);
        jdbc.passwordEncoder(passwordEncoder);
    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
        security.checkTokenAccess("isAuthenticated()");
        security.tokenKeyAccess("isAuthenticated()");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();
        delegates.add(jwtTokenEnhancer);
        delegates.add(accessTokenConverter);
        //配置JWT的内容增强器
        enhancerChain.setTokenEnhancers(delegates);
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService) //配置加载用户信息的服务
                .accessTokenConverter(accessTokenConverter)
                .tokenEnhancer(enhancerChain);
    }

}
