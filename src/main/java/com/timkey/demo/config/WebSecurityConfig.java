package com.timkey.demo.config;

import com.timkey.demo.customizing.ValidateCodeFilter;
import com.timkey.demo.handler.MyAuthenctiationFailureHandler;
import com.timkey.demo.handler.MyAuthenctiationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tangfeng
 * @fileName WebSecurityConfig
 * @date 2019/6/18 14:30
 * @description xxxx
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//true 会拦截@PreAuthrize注解的配置
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAuthenctiationSuccessHandler authenctiationSuccessHandler;
    @Autowired
    private MyAuthenctiationFailureHandler authenticationFailureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //自定义过滤器
        /*http.addFilterBefore(new BeforeLoginFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(new AfterLoginFilter(), UsernamePasswordAuthenticationFilter.class);*/
        http.addFilterBefore(new ValidateCodeFilter(authenticationFailureHandler), UsernamePasswordAuthenticationFilter.class);
        http.formLogin().loginPage("/login")
                .successHandler(authenctiationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/", "/index","/code/image").permitAll()
                //.antMatchers("/resource/**","/test/**").permitAll()
                //.antMatchers("resource/**/*.{js.html}").permitAll()//允许/resource下.js和.html文件可以直接访问
                .anyRequest().access("@authService.canAccess(request,authentication)")
                .and().sessionManagement().maximumSessions(1)//只能登陆一个
                ;
                //.anyRequest().authenticated();

    }


   /* @Bean
    public UserDetailsService myUserDetailsService() {

        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();

        String[][] usersGroupsAndRoles = {
                {"user", "password", "ROLE_USER", "GROUP_oneTeam"},
                {"test", "password", "ROLE_USER", "GROUP_twoTeam"},
                {"other", "password", "ROLE_USER", "GROUP_otherTeam"},
                {"admin", "password", "ROLE_ADMIN"},
        };

        for (String[] user : usersGroupsAndRoles) {
            List<String> authoritiesStrings = Arrays.asList(Arrays.copyOfRange(user, 2, user.length));
            log.info("> Registering new user: " + user[0] + " with the following Authorities[" + authoritiesStrings + "]");
            inMemoryUserDetailsManager.createUser(new User(user[0], passwordEncoder().encode(user[1]),
                    authoritiesStrings.stream().map(s -> new SimpleGrantedAuthority(s)).collect(Collectors.toList())));
        }


        return inMemoryUserDetailsManager;
    }*/

    /**
     * 通过覆写configure方法，进行创建用户
     *
     * @param auth
     * @return void
     * @author tangfeng
     * @date 2019/6/18
     */
   /* @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //1基于内存的方式创建用户(5.0后必须加加密方式）
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(passwordEncoder().encode("123456"))
                .roles("admin");
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder().encode("123456"))
                .roles("normal");
        //2.
         auth.userDetailsService(myUserDetailsService());
    }*/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
