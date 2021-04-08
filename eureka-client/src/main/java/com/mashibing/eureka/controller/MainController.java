package com.mashibing.eureka.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/***********************
 * @Description: TODO 类描述<BR>
 * @author: zhao.song
 * @since: 2021/4/9 0:25
 * @version: 1.0
 ***********************/
@RestController
public class MainController {

    @GetMapping("/hello")
    public String sayHello(){
        return "hello eureka client0";
    }
}
