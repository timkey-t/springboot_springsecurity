package com.timkey.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tangfeng
 * @fileName HelloController
 * @date 2019/6/18 14:18
 * @description 简单的Controller
 */
@RestController
@RequestMapping("/hello")
public class HelloController {
    @GetMapping()
    public String hello(){
        return "Hello Spring Security!!!";
    }

    @GetMapping("/helloAdmin")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String helloAdmin(){
        return "Hello,Admin";
    }

    @GetMapping("/helloUser")
    @PreAuthorize("hasAnyRole('NORMAL','ADMIN')")
    public String helloUser(){
        return "Hello,user";
    }
    @GetMapping("/authen")
    public Object getAuthenInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }
}
