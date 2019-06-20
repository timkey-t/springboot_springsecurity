package com.timkey.demo.customizing;

import com.timkey.demo.exception.VaildateCodeException;
import com.timkey.demo.handler.MyAuthenctiationFailureHandler;
import com.timkey.demo.model.ImageCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author tangfeng
 * @fileName ValidateCodeFilter
 * @date 2019/6/19 18:03
 * @description xxxx
 */
@Component
public class ValidateCodeFilter extends OncePerRequestFilter {

    private final MyAuthenctiationFailureHandler authenctiationFailureHandler;

    public static final String SESSION_KEY_IMAGE_CODE="SESSION_KEY_IMAGE_CODE";

    private SessionStrategy sessionStrategy=new HttpSessionSessionStrategy();

    @Autowired
    public ValidateCodeFilter(MyAuthenctiationFailureHandler authenctiationFailureHandler) {
        this.authenctiationFailureHandler = authenctiationFailureHandler;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (StringUtils.equalsIgnoreCase("/login",httpServletRequest.getRequestURI()) && StringUtils.equalsIgnoreCase(httpServletRequest.getMethod(),"post")){
            try {
                validateCode(new ServletWebRequest(httpServletRequest));
            } catch (VaildateCodeException e) {
                authenctiationFailureHandler.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
                return;
            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    private void validateCode(ServletWebRequest servletWebRequest) throws ServletRequestBindingException {
        ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(servletWebRequest, SESSION_KEY_IMAGE_CODE);
        String codeInRequest = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), "imageCode");

        if (StringUtils.isBlank(codeInRequest)) {
            throw new VaildateCodeException("验证码不能为空！");
        }
        if (codeInSession == null) {
            throw new VaildateCodeException("验证码不存在！");
        }
        if (codeInSession.isExpire()) {
            sessionStrategy.removeAttribute(servletWebRequest, SESSION_KEY_IMAGE_CODE);
            throw new VaildateCodeException("验证码已过期！");
        }
        if (!StringUtils.equalsIgnoreCase(codeInSession.getCode(), codeInRequest)) {
            throw new VaildateCodeException("验证码不正确！");
        }
        sessionStrategy.removeAttribute(servletWebRequest, SESSION_KEY_IMAGE_CODE);
    }
}
