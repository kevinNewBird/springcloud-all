package com.mashibing.eureka.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/***********************
 * @Description: spring -security 关闭跨域攻击<BR>
 * @author: zhao.song
 * @since: 2021/4/11 0:56
 * @version: 1.0
 ***********************/
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //如果服务注册报错(security默认开启了防跨域攻击)
    //Root name 'timestamp' does not match expected ('instance') for type [simple


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 实现admin上报(加上了security,必须这样配置)
        http.authorizeRequests().anyRequest().permitAll().and().csrf().disable();
        //关闭跨域
//        http.csrf().disable();
//        super.configure(http);
    }
}
