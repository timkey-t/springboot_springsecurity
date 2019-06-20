package com.timkey.demo.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author tangfeng
 * @fileName MyAuthenctiationSuccessHandler
 * @date 2019/6/19 14:18
 * @description xxxx
 */
@Slf4j
@Component("myAuthenctiationSuccessHandler")
public class MyAuthenctiationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        log.info("登陆成功！");
        redirectStrategy.sendRedirect(request, response, "/index");
    }
}
