package com.timkey.demo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author tangfeng
 * @fileName TestController
 * @date 2019/6/18 17:00
 * @description xxxx
 */
@Controller
public class TestController {
    @GetMapping("/login")
    public String login(){
        return "/login";
    }
    @GetMapping({"","/","/index"})
    public String index(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ("anonymousUser".equals(principal)){
            model.addAttribute("name","anonymous user");
        }else {
            User user = (User) principal;
            model.addAttribute("name",user.getUsername());
        }
        return "index";
    }
}
