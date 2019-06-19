package com.timkey.demo.service.impl;

import com.timkey.demo.model.Permission;
import com.timkey.demo.model.Role;
import com.timkey.demo.repository.PermissionRepository;
import com.timkey.demo.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author tangfeng
 * @fileName PermissonServiceImpl
 * @date 2019/6/19 11:28
 * @description 1.在程序启动的时候就加载权限信息，这里使用@PostConstrust 2.存储权限信信息
 */
@Service
@Slf4j
public class PermissonServiceImpl implements PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    private Map<String, Collection<ConfigAttribute>> permissionMap=null;

    @PostConstruct
    public void initPermission(){
       /*
        *从数据库中获取所有的权限信息，然后进行遍历，存储到map集合中
         */
        permissionMap=new HashMap<>();
        List<Permission> all = permissionRepository.findAll();
        for (Permission p:all){
            Collection<ConfigAttribute> collection=new ArrayList<ConfigAttribute>();
            for (Role r:p.getRoles()) {
                ConfigAttribute config = new SecurityConfig("ROLE_" + r.getName());
                collection.add(config);
            }
            permissionMap.put(p.getUrl(),collection);
        }
        log.info("map:{}",permissionMap);
    }

    @Override
    public Map<String, Collection<ConfigAttribute>> getPermissionMap() {
        if(permissionMap==null || permissionMap.size()==0){
            initPermission();
        }
        return permissionMap;
    }
}
