package com.timkey.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @author tangfeng
 * @fileName Permission
 * @date 2019/6/19 10:55
 * @description xxxx
 */
@Entity(name = "T_PERMISSION")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
    @Id
    private Long id;
    //权限名称
    private String name;
    //描述
    private String desc;
    //地址
    private String url;
    //父id
    private Long pid;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_perm",joinColumns = {@JoinColumn(name = "perm_id")},inverseJoinColumns = {@JoinColumn(name ="role_id")})
    private List<Role> roles;
}
