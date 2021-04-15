package com.mashibing.eureka.controller;

import com.mashibing.api.UserApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/***********************
 * @Description: TODO 类描述<BR>
 * @author: zhao.song
 * @since: 2021/4/14 17:52
 * @version: 1.0
 ***********************/
@RestController
public class UserController implements UserApi {


    public String isAlive() {
        return "is ok?";
    }

    public String alive() {
        return "ok";
    }

    @Override
    public String getById(@RequestParam Integer id) {
        return String.valueOf(id);
    }


    @GetMapping("/register")
    public String register(){
        return "success";
    }

}
