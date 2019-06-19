package com.timkey.demo.customizing;

import com.timkey.demo.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * @author tangfeng
 * @fileName AuthService
 * @date 2019/6/19 16:16
 * @description xxxx
 */
@Service
@Slf4j
public class AuthService {

    @Autowired
    private PermissionService permissionService;
    /**
     *  1.未登陆的情况下，需要做一个判断或者拦截
     *  2.匿名的角色判断 ROLE_ANONYMOUS
     *  3.通过request对象的url 获取到对应的权限信息
     *  4.将我们的获取到的权限信息和当前登陆账号信息进行比较
     * @author tangfeng
     * @date 2019/6/19
     * @param request,authentication
     * @return boolean
     */
    public boolean canAccess(HttpServletRequest request, Authentication authentication){
        log.info("我成功进入了！");
        boolean b=false;
        Object principal = authentication.getPrincipal();
        //1
        if (principal==null || "anonymousUser".equals(principal)){
            return b;
        }
        //2
        if (authentication instanceof AnonymousAuthenticationToken){
            //check
        }
        //3
        Collection<ConfigAttribute> configAttributes = null;
        Map<String, Collection<ConfigAttribute>> map = permissionService.getPermissionMap();
        for (Iterator<String> it=map.keySet().iterator();it.hasNext();){
            String curUrl = it.next();
            AntPathRequestMatcher matcher = new AntPathRequestMatcher(curUrl);
            if (matcher.matches(request)){
                configAttributes = map.get(curUrl);
                break;
            }
        }

        if ( configAttributes == null || configAttributes.size()==0){
            return b;
        }
        //4
        for (Iterator<ConfigAttribute> it= configAttributes.iterator();it.hasNext();) {
            ConfigAttribute cfa = it.next();
            String role = cfa.getAttribute();//ROLE_ADMIN
            for (GrantedAuthority authority:authentication.getAuthorities()){
                if (role.equals(authority.getAuthority())){
                    log.info("当前匹配到的角色是：{}",role);
                    b=true;
                    break;
                }
            }
        }
        
        return b;
    }
}
