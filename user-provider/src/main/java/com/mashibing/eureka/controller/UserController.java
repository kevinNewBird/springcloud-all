package com.mashibing.eureka.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***********************
 * @Description: TODO 类描述<BR>
 * @author: zhao.song
 * @since: 2021/4/14 17:52
 * @version: 1.0
 ***********************/
@RestController
public class UserController {


    @GetMapping("/alive")
    public String alive(){
        return "ok";
    }
}
