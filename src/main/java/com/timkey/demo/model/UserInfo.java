package com.timkey.demo.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author tangfeng
 * @fileName UserInfo
 * @date 2019/6/18 15:32
 * @description xxxx
 */
@Entity
@Table(name = "T_USER")
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo extends BaseEntity implements Serializable {
    private String name;
    private String password;
    //@Enumerated(EnumType.STRING)
    //private Roles role;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",joinColumns = {@JoinColumn(name = "uid")},inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> roles;
}
