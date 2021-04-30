package com.mashibing.crsf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;

/***********************
 * @Description: 自定义权限校验器 <BR>
 * @author: zhao.song
 * @since: 2021/4/29 0:32
 * @version: 1.0
 ***********************/
@Service("myAuthProvider")
public class MyAuthProvider implements AuthenticationProvider {

    @Autowired
    private MineUserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // TODO 限制穷举密码,防止暴力破解(可以增加重试次数的限制,避免这个问题)

        // 1.表单提交上来的数据
        String username = authentication.getPrincipal().toString();
        String rawPassword = encoder.encode(authentication.getCredentials().toString());
        System.out.printf("username:%s--password:%s\n", username, rawPassword);

        // 2.去数据库拿出来user对象
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // 3.比对
        //明文 不等同于 数据库里存的密文(应该由前端加密上传,目前是明文)
        if (encoder.matches(rawPassword, userDetails.getPassword())) {
            // 密码通过,登录成功(这个密文的作用:生成签名)
            // 使用数据库的密码,只会获取一次
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username
                    , userDetails.getPassword(), userDetails.getAuthorities());
            // 签名(用户名+密码+权限): 校验前面的数据正确性, 不走会话和数据库(比如jwt)
            return authentication;

        } else {
            throw new BadCredentialsException("用户名或密码不正确!");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
