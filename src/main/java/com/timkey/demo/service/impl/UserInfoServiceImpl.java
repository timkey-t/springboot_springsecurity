package com.timkey.demo.service.impl;

import com.timkey.demo.model.UserInfo;
import com.timkey.demo.repository.UserInfoRepository;
import com.timkey.demo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tangfeng
 * @fileName UserInfoServiceImpl
 * @date 2019/6/18 15:48
 * @description xxxx
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Override
    public UserInfo findByUsername(String username) {
        return userInfoRepository.findByName(username);
    }
}
