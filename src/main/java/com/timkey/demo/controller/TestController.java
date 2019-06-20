package com.timkey.demo.controller;

import com.timkey.demo.model.ImageCode;
import com.timkey.demo.utils.ImageUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.timkey.demo.customizing.ValidateCodeFilter.SESSION_KEY_IMAGE_CODE;

/**
 * @author tangfeng
 * @fileName TestController
 * @date 2019/6/18 17:00
 * @description xxxx
 */
@Controller
public class TestController {

    private SessionStrategy sessionStrategy=new HttpSessionSessionStrategy();

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
    @GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode = ImageUtils.createImageCode();
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY_IMAGE_CODE, imageCode);
        ImageIO.write(imageCode.getImage(), "jpeg", response.getOutputStream());
    }
}
