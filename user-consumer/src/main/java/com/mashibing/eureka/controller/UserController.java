package com.mashibing.eureka.controller;

import com.mashibing.api.UserApi;
import com.mashibing.eureka.service.IUserService;
import com.mashibing.eureka.service.IUserServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/***********************
 * @Description: TODO 类描述<BR>
 * @author: zhao.song
 * @since: 2021/4/14 17:52
 * @version: 1.0
 ***********************/
@RestController
public class UserController {



    @Autowired
    private IUserServiceV2 userService;

    @GetMapping("/alive")
    public String alive(){
        return userService.alive();
    }

    @GetMapping("/isAlive")
    public String isAlive(){
        return userService.isAlive();
    }

    @GetMapping("/getById")
    public String getById(Integer id) {
        return userService.getById(id);
    }

    @GetMapping("/register")
    public String register(){
        return userService.register();
    }


}
