package com.jackson.authentication.service;

import com.jackson.authentication.mappers.AuthRoleDao;
import com.jackson.authentication.mappers.AuthUserDao;
import com.jackson.authentication.models.vo.AuthRoleVO;
import com.jackson.authentication.models.vo.AuthUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JieXing
 * @Description: 获取用户信息
 * @date 2021/8/12
 * @time 22:26
 */
@Component(value = "myUserDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Qualifier(value = "myPasswordEncoder")
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthUserDao authUserDao;

    @Autowired
    private AuthRoleDao authRoleDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUserVO userVO = authUserDao.getUserInfoByAccountNum(username);
        if (null == userVO) {
            throw new RuntimeException("账号不存在");
        }
        List<AuthRoleVO> roleVO = authRoleDao.getRolesByUserId(userVO.getUserId());
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (roleVO != null && roleVO.size() > 0) {
            for (AuthRoleVO vo : roleVO) {
                authorities.add(new SimpleGrantedAuthority(vo.getRoleName()));
            }
        }
        String password = passwordEncoder.encode(userVO.getPassword());
        return new User(username, password, authorities);
    }
}
