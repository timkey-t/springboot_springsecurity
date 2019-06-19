package com.timkey.demo.repository;

import com.timkey.demo.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author tangfeng
 * @fileName UserInfoRepository
 * @date 2019/6/18 15:43
 * @description xxxx
 */
public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {

    UserInfo findByName(String name);
}
