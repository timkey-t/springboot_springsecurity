package com.timkey.demo.customizing;

import com.timkey.demo.model.Role;
import com.timkey.demo.model.UserInfo;
import com.timkey.demo.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
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
 * @author tangfeng
 * @fileName CustomUserDetailsService
 * @date 2019/6/18 15:58
 * @description xxxx
 */
@Component
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("用户名：{}", username);
        UserInfo userInfo = userInfoService.findByUsername(username);
        if (userInfo == null) {
            throw new UsernameNotFoundException("not found");
        }
        List<GrantedAuthority> authorities =new ArrayList<>();
        for (Role r:userInfo.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+r.getName()));
        }
        User user = new User(userInfo.getName(), passwordEncoder.encode(userInfo.getPassword()),authorities);
        log.info("user:{}",user.toString());
        return user;
    }
}
