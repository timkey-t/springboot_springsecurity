package com.timkey.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author tangfeng
 * @fileName Role
 * @date 2019/6/19 9:06
 * @description xxxx
 */
@Entity(name = "T_ROLE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    private Long rid;

    private String name;

    private String desc;
}
