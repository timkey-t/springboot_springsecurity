package com.timkey.demo.repository;

import com.timkey.demo.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author tangfeng
 * @fileName PermissionRepository
 * @date 2019/6/19 11:26
 * @description xxxx
 */
public interface PermissionRepository  extends JpaRepository<Permission,Long> {
}
