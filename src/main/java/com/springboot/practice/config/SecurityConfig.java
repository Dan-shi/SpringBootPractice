package com.springboot.practice.config;

import com.springboot.practice.repository.ReaderRepository;
import com.springboot.practice.security.MyAccessDecisionManager;
import com.springboot.practice.security.MyAccessDeniedHandler;
import com.springboot.practice.security.MyFilterInvocationSecurityMetadataSourceImpl;
import com.springboot.practice.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.
        builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.
        HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.
        EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.
        WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private UserService userDetailService;

    @Autowired
    private MyFilterInvocationSecurityMetadataSourceImpl filter;

    @Autowired
    private MyAccessDecisionManager decisionManager;

    @Autowired
    private MyAccessDeniedHandler deniedHandler;
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
        http.authorizeRequests()
//                .antMatchers("/js/**","/css/**","/images/*","/fonts/**","/**/*.png","/**/*.jpg","/**/*.ico").permitAll()
                //配置安全策略
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(filter);
                        o.setAccessDecisionManager(decisionManager);
                        return o;
                    }
                })
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/user/login")  //自定义的登录接口
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        StringBuffer sb = new StringBuffer();
                        sb.append("{\"status\":\"error\",\"msg\":\"");
                        if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
                            sb.append("用户名或密码输入错误，登录失败!");
                        } else if (e instanceof DisabledException) {
                            sb.append("账户被禁用，登录失败，请联系管理员!");
                        } else {
                            sb.append("登录失败!");
                        }
                        sb.append("\"}");
                        out.write(sb.toString());
                        out.flush();
                        out.close();
                    }
                })
                .successHandler(new AuthenticationSuccessHandler() {
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        System.out.println("登录成功");
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter out = httpServletResponse.getWriter();
//                        ObjectMapper objectMapper = new ObjectMapper();
                        //String s = "{\"status\":\"success\",\"msg\":"  + "}";
                        String s = "{\"status\":\"200\",\"data\":\"success\",\"msg\":\"登录成功\"}";
                        out.write(s);
                        out.flush();
                        out.close();
                    }
                })
                .and()
                .logout()
                .permitAll()
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .accessDeniedHandler(deniedHandler);
    }
    @Override
    protected void configure(
            AuthenticationManagerBuilder auth) throws Exception {
        //注入userDetailsService，需要实现userDetailsService接口
        auth.userDetailsService(userDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }

    //在这里配置哪些页面不需要认证
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**");
    }

}