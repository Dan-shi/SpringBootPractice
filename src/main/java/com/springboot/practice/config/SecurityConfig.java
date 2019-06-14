package com.springboot.practice.config;

import com.springboot.practice.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.
        builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.
        HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.
        EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.
        WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private ReaderRepository readerRepository;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/").permitAll()
//                .anyRequest().authenticated()   // 其他地址的访问均需验证权限
//                .and()
//                .formLogin()
//                .loginPage("/login")   //  登录页
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .permitAll()
//                .failureUrl("/login");
    }
    @Override
    protected void configure(
            AuthenticationManagerBuilder auth) throws Exception {
    }
}