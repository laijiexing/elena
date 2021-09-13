package com.jackson.authentication;

import com.jackson.authentication.mappers.AuthRoleDao;
import com.jackson.authentication.mappers.AuthUserDao;
import com.jackson.authentication.models.vo.AuthRoleVO;
import com.jackson.authentication.models.vo.AuthUserVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest(classes = {ElenaAuthApplication.class})
class ElenaAuthApplicationTests {

    @Autowired
    @Qualifier(value = "myPasswordEncoder")
    PasswordEncoder passwordEncoder;

    @Autowired
    private AuthUserDao authUserDao;

    @Autowired
    private AuthRoleDao authRoleDao;


    @Test
    void encode() {
        String encode = passwordEncoder.encode("elena-gateway-server");
        System.out.println(encode);
    }

    @Test
    void sql(){
        AuthUserVO userVO = authUserDao.getUserInfoByAccountNum("admin");
        List<AuthRoleVO> roleVO = authRoleDao.getRolesByUserId(userVO.getUserId());
        System.out.println(userVO);
        System.out.println(roleVO);
    }


}
