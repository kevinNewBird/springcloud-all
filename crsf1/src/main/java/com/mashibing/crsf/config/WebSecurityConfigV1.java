package com.mashibing.crsf.config;

import com.mashibing.crsf.filter.CodeFilter;
import com.mashibing.crsf.service.MineUserDetailsService;
import com.mashibing.crsf.service.MyAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
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
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
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
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfigV1 extends WebSecurityConfigurerAdapter {

    @Autowired
    private MineUserDetailsService userDetailsService;

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
        http.addFilterBefore(new CodeFilter(), UsernamePasswordAuthenticationFilter.class);

        //类似html的父标签的写法, and()就是回到父标签
        // 1.所有的请求都需要权限认证
        http
//                .addFilterBefore(new CodeFilter(), UsernamePasswordAuthenticationFilter.class)
                // 那些地址需要登录
                .authorizeRequests()
                // 忽略静态资源(也可以在configure(WebSecurity中进行静态资源的权限跳过))
//                .antMatchers("/img/**").permitAll()
                // 登录的用户有什么角色可以访问这个地址,需要角色就是说明需要登陆
                .antMatchers("/admin/**").hasRole("admin")
                .antMatchers("/user/**").hasRole("user")
                //所请求都需要登录验证,如果没有这个配置,别的地址都不需要登录了
                .anyRequest().authenticated()
                .and()
                // 自定义登录表单   permitAll()--表示login.html页面不需要权限
                .formLogin().loginPage("/login.html").permitAll()
                // 配置提交登录参数的接口,在login.html中的action中地址
                .loginProcessingUrl("/login").permitAll()
                // 登录失败页面,但是具体失败的原因是带不过去的
                .failureUrl("/login.html?error")
                // 登录成功跳转页面(true表示不管从那个页面登录都跳转ok.html)
                .defaultSuccessUrl("/ok", true)
                // 给没登录的用户 ,可以访问这个地址的权限
                .permitAll()
                //表单上用户名和密码参数名  adming -> 分权限展示页面
                .usernameParameter("username")
                .passwordParameter("password")
                // 登录失败后被调起,作用:分析失败原因,
                // /统计失败次数
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
                        e.printStackTrace();

                        //判断异常信息,做更灵活的跳转
                        // 跳转的两种方式
                        //request
//                        request.getRequestDispatcher(request.getRequestURL().toString()).forward(request, response);
                        //response
//                        response.sendRedirect("/login.html?error");
                    }
                })
                //在登录成功后会被调起,作用: 用来锁定资源/初始化资源等
                //只能有一个
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        //用户对象
//                        Object principal =  authentication.getPrincipal();

                    }
                })
                .and()
                .logout()
                //这个是配置在out.html的地址
                .logoutUrl("/logout")
//                 退出的处理器,可以加多个,作用:清理用户资源
                .addLogoutHandler(new LogoutHandler() {
                    @Override
                    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
                        System.out.println("我滚了....");
                    }
                })
                .addLogoutHandler(new LogoutHandler() {
                    @Override
                    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
                        System.out.println("我又滚了...");
                    }
                })
                .and()
                .rememberMe()
                .and()
                // 开启csrf(这个将会影响logout, 不开启csrf可以使用get请求的logout直接退出,开启后必须使用post请求退出)
                .csrf()
//                .disable()
                // 开启 csrf 的token生成,如果开启了这个必须要在静态文件中接受这个参数,否则登录会有问题
                .csrfTokenRepository(new HttpSessionCsrfTokenRepository())
        ;
    }

    /**
     * Description: 重定义权限的继承关系 <BR>
     *
     * @param :
     * @return {@link RoleHierarchy}
     * @author: zhao.song
     * @date: 2021/4/30 1:48
     */
    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_admin > ROLE_user");
        return roleHierarchy;
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
        // 内存方式
        //第一种方式: 配置账号密码(properties配置文件就失效了)
//        auth
//                //内存用户
//                .inMemoryAuthentication()
//                .withUser("kevin").password(encoder.encode("1233"))
//                //角色
//                .roles("admin")
//                .and()
//                .withUser("tom").password(encoder.encode("1233"))
//                .roles("user");
//        //第二种方式:
//        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
//
//        // jdbc方式
//        JdbcUserDetailsManager userDetailsService = auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .getUserDetailsService();
//        if (!userDetailsService.userExists("cjdbc")) {
//            userDetailsService.createUser(User.withUsername("cjdbc")
//                    .password(encoder.encode("cjdbc")).roles("admin").build());
//        }


        // 自定义ORM登录方法(用户的查询)
        auth.userDetailsService(userDetailsService)
                .and()
                //自定义自己的权限校验器
                .authenticationProvider(myAuthProvider);

    }


    /**
     * Description: 第二种定义用户的方式(使用了bean注入的方式,
     * configure(AuthenticationManagerBuilder)可以注释掉
     * ,当然留着也不影响,两者可以同时存在共同作用) <BR>
     *
     * @param :
     * @return {@link UserDetailsService}
     * @author: zhao.song
     * @date: 2021/4/28 15:48
     */
//    @Bean
//    public UserDetailsService userDetailsService() {
//        // 1.基于内存存储用户
////        InMemoryUserDetailsManager userManager = new InMemoryUserDetailsManager();
////        User user = new User("lily", encoder.encode("1234")
////                // 账户是否可用(是否被删除)
////                , true
////                // 账户没有过期
////                , true
////                // 密码没有过期: false用户密码过期无法登录(User credentials have expired)
////                , true
////                // 账户没被锁定(是否冻结),false账户将被锁定,无法登录(User account is locked)
////                , true
////                , Collections.singletonList(new SimpleGrantedAuthority("admin")));
////        userManager.createUser(user);
////        userManager.createUser(User.withUsername("bob").password(encoder.encode("1234")).roles("user").build());
//
//        // 2.基于JDBC 用户存储
//        JdbcUserDetailsManager userManager = new JdbcUserDetailsManager(dataSource);
//        if (!userManager.userExists("jdbc")) {
//            userManager.createUser(User.withUsername("jdbc").password(encoder.encode("jdbc")).roles("admin", "xxoo").build());
//        }
//
//
//        return userManager;
//    }

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


}
