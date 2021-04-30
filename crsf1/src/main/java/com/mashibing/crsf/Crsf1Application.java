package com.mashibing.crsf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(scanBasePackages = "com.mashibing.crsf")
public class Crsf1Application {

    public static void main(String[] args) {
        SpringApplication.run(Crsf1Application.class, args);
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
