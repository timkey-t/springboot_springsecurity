package com.timkey.demo.service;

import com.timkey.demo.model.UserInfo;

/**
 * @author tangfeng
 * @fileName UserInfoService
 * @date 2019/6/18 15:47
 * @description xxxx
 */
public interface UserInfoService {
    public UserInfo findByUsername(String username);
}
