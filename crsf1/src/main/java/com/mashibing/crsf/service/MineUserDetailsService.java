package com.mashibing.crsf.service;

import com.mashibing.crsf.entity.CusUser;
import com.mashibing.crsf.repository.UserJDBCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

/***********************
 * @Description: 自定义用户类 <BR>
 * @author: zhao.song
 * @since: 2021/4/29 0:06
 * @version: 1.0
 ***********************/
@Component("mineDetailService")
public class MineUserDetailsService implements UserDetailsService {

    @Autowired
    private UserJDBCRepository userJDBCRepository;

    /**
     * Description: 自定义数据源查询数据库,脱离原生的内置查询方式 <BR>
     *
     * @param username:
     * @return {@link UserDetails}
     * @author: zhao.song
     * @date: 2021/4/29 0:27
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 获取用户
        CusUser cusUser = userJDBCRepository.findByUsername(username);
        return User.withUsername(cusUser.getUsername()).password(cusUser.getPassword())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_admin")))
                .disabled(!cusUser.isEnabled())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .build();
    }
}
