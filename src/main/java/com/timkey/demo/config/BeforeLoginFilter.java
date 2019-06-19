package com.timkey.demo.config;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author tangfeng
 * @fileName BeforeLoginFilter
 * @date 2019/6/19 10:11
 * @description xxxx
 */
@Slf4j
public class BeforeLoginFilter extends GenericFilter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.warn("我是个过滤器，我在UsernamePasswordAuthencationFilter之前");
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
