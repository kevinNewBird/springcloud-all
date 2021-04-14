package com.mashibing.eureka.controller;

import com.mashibing.eureka.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/***********************
 * @Description: TODO 类描述<BR>
 * @author: zhao.song
 * @since: 2021/4/14 17:52
 * @version: 1.0
 ***********************/
@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/alive")
    public String alive(){
        return userService.alive();
    }

    @GetMapping("/register")
    public String register(){
        return userService.register();
    }
}
