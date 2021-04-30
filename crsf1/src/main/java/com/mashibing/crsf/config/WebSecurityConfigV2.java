package com.mashibing.crsf.config;

import com.mashibing.crsf.filter.CodeFilter;
import com.mashibing.crsf.service.MineUserDetailsService;
import com.mashibing.crsf.service.MyAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

/***********************
 * @Description: security 权限认证类 <BR>
 * @author: zhao.song
 * @since: 2021/4/26 23:37
 * @version: 1.0
 ***********************/
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfigV2 extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private MyAuthProvider myAuthProvider;

    @Autowired
    private PasswordEncoder encoder;

    //这个必须放在UserDetailsService的上面注入,因为userDetailsService有使用到dataSource,否则会导致启动报错
    @Autowired
    private DataSource dataSource;

//    @Autowired
//    private UserDetailsService userDetailsService;


    //这个方法的优先级高于configure(HttpSecurity)
    @Override
    public void configure(WebSecurity web) throws Exception {
        //忽略登录,可直接访问的静态资源
        web.ignoring().antMatchers("/img/**").antMatchers("/kaptcha");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //类似html的父标签的写法, and()就是回到父标签
        // 1.所有的请求都需要权限认证
        http
                .addFilterBefore(new CodeFilter(), UsernamePasswordAuthenticationFilter.class)
                // 那些地址需要登录
                .authorizeRequests()
                // 忽略静态资源(也可以在configure(WebSecurity中进行静态资源的权限跳过))
//                .antMatchers("/img/**").hasIpAddress("127.0.0.1")
                // ip地址访问白名单,封ip操作
                //所请求都需要验证
                .anyRequest().authenticated()
                .and()
                // 自定义登录表单   permitAll()--表示login.html页面不需要权限
                .formLogin().loginPage("/login.html")

                .loginProcessingUrl("/login").permitAll()
                // 登录失败 页面
                .failureUrl("/login.html?error")
                // 登录成功跳转的页面
                .defaultSuccessUrl("/ok", true).permitAll()
                // 配置 登录页 的表单name   admin -> 分权限 展示页面
                .passwordParameter("password")
                .usernameParameter("username")
                // 在登录成功后会被调起
                // 用来锁定资源 初始化资源等
                .successHandler(new AuthenticationSuccessHandler() {

                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                        Authentication authentication) throws IOException, ServletException {
                        // TODO Auto-generated method stub
                        Object user = authentication.getPrincipal();
                        System.out.println(user);
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    // 登录失败的异常信息
                    // 分析失败原因，统计失败次数
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                                        AuthenticationException exception) throws IOException, ServletException {
                        // TODO Auto-generated method stub

                        // 登录失败异常信息 exception

                        exception.printStackTrace();
                    }
                })
//                .and()
//                //记住我 cookies 不一定是浏览器,下发的token
//                .rememberMe()
                // (与rememberMe冲突)同一用户多地点登录/ 禁止其它终端登录
                .and()
                .sessionManagement()
                // 允许同时登录的客户端
                .maximumSessions(1)
                // true表示已经有用户登录的情况下,不允许相同用户登录
                .maxSessionsPreventsLogin(true)
                .and()
                .and()
                .csrf()
                // 开启 csrf 的token生成
                .disable();

    }


    /**
     * Description: 及时将过期的token失效 <BR>
     *
     * @param :
     * @return {@link HttpSessionEventPublisher}
     * @author: zhao.song
     * @date: 2021/4/29 19:11
     */
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
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

        auth.inMemoryAuthentication()
                .withUser("111").password(encoder.encode("123")).roles("admin")
                .and()
                .withUser("112").password(encoder.encode("123")).roles("user")

                .and()
                .withUser("113").password(encoder.encode("123")).roles("guest")

                .and()
                .withUser("114").password(encoder.encode("123")).roles("admin", "user")

        ;

    }


}
