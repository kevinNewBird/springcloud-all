package com.mashibing.crsf.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/***********************
 * @Description: TODO 类描述<BR>
 * @author: zhao.song
 * @since: 2021/4/26 23:37
 * @version: 1.0
 ***********************/
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //类似html的父标签的写法, and()就是回到父标签
        // 1.所有的请求都需要权限认证
        http
                // 那些地址需要登录
                .authorizeRequests()
                //所请求都需要验证
                .anyRequest().authenticated()
                .and()
                // 自定义登录表单   permitAll()--表示login.html页面不需要权限
                .formLogin().loginPage("/login.html")
                //表单上用户名和密码参数名  adming -> 分权限展示页面
                .usernameParameter("username")
                .passwordParameter("password")
                // 配置提交登录参数的接口
                .loginProcessingUrl("/login")
                // 登录失败页面,但是具体失败的原因是带不过去的
                .failureUrl("/login.html?error")
                // 登录成功跳转页面(true表示不管从那个页面登录都跳转ok.html)
                .defaultSuccessUrl("/ok", true)
                // 给没登录的用户 ,可以访问这个地址的权限
                .permitAll()
                // 失败异常
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
                        e.printStackTrace();

                        //判断异常信息,做更灵活的跳转
                        // 跳转的两种方式
                        //request
                        request.getRequestDispatcher(request.getRequestURL().toString()).forward(request, response);
                        //response
//                        response.sendRedirect("/login.html?error");
                    }
                })
                .and()
                .csrf()
                // 开启 csrf 的token生成
                .csrfTokenRepository(new HttpSessionCsrfTokenRepository());
    }


    /**
     * Description: 权限相关的配置(session登录,不适合分布式)  并发高时,使用jwt保存会话信息 <BR>
     *
     * @param auth:
     * @return
     * @author: zhao.song
     * @date: 2021/4/28 10:51
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //第一种方式: 配置账号密码(properties配置文件就失效了)
        auth
                //内存用户
                .inMemoryAuthentication()
                .withUser("kevin").password(encoder.encode("1233"))
                //角色
                .roles("admin")
                .and()
                .withUser("tom").password(encoder.encode("1233"))
                .roles("user");
        //第二种方式:
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }

    /**
     * Description: 第二种定义用户的方式 <BR>
     *
     * @param :
     * @return {@link UserDetailsService}
     * @author: zhao.song
     * @date: 2021/4/28 15:48
     */
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager userManager = new InMemoryUserDetailsManager();
        User user = new User("lily", encoder.encode("1234")
                // 账户是否可用(是否被删除)
                , true
                // 账户没有过期
                , true
                // 密码没有过期: false用户密码过期无法登录(User credentials have expired)
                , true
                // 账户没被锁定(是否冻结),false账户将被锁定,无法登录(User account is locked)
                , true
                , Collections.singletonList(new SimpleGrantedAuthority("admin")));
        userManager.createUser(user);
        userManager.createUser(User.withUsername("bob").password(encoder.encode("1234")).roles("user").build());
        return userManager;
    }

    /**
     * Description: 密码加密(如果不使用properties配置,密码必须加密) <BR>
     *
     * @param :
     * @return {@link PasswordEncoder}
     * @author: zhao.song
     * @date: 2021/4/28 11:00
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        //废弃的方式,一般用于测试使用
//        return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }

}
