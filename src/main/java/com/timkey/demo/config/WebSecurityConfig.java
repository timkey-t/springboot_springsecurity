package com.timkey.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login")
                .and()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/", "/index").permitAll()
                //.antMatchers("/resource/**","/test/**").permitAll()
                //.antMatchers("resource/**/*.{js.html}").permitAll()//允许/resource下.js和.html文件可以直接访问
                .anyRequest().access("@authService.canAccess(request,authentication)")
                .and().sessionManagement().maximumSessions(1)//只能登陆一个
                ;
                //.anyRequest().authenticated();

        //自定义过滤器
        /*http.addFilterBefore(new BeforeLoginFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(new AfterLoginFilter(), UsernamePasswordAuthenticationFilter.class);*/

    }

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
        //基于内存的方式创建用户(5.0后必须加加密方式）
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(passwordEncoder().encode("123456"))
                .roles("admin");
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder().encode("123456"))
                .roles("normal");

    }*/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
