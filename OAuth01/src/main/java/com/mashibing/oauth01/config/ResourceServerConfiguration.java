package com.mashibing.oauth01.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/***********************
 * @Description: 资源服务器 <BR>
 * @author: zhao.song
 * @since: 2021/5/4 20:58
 * @version: 1.0
 ***********************/
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableResourceServer
@Configuration
public class ResourceServerConfiguration  extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/oauth2/api/**").authorizeRequests()
                .antMatchers(HttpMethod.GET, "/oauth2/api/read/**").access("#oauth2.hasScope('read')")
                .antMatchers(HttpMethod.GET, "/oauth2/api/write/**").access("#oauth2.hasScope('write')");
    }
}
