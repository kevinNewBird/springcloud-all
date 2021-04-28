package com.mashibing.crsf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/***********************
 * @Description: <BR>
 * @author: zhao.song
 * @since: 2021/4/26 23:34
 * @version: 1.0
 ***********************/
@Controller
public class MainController {


    @GetMapping("/login.html")
    public String login() {
        System.out.println("login");
        // 会定位到模板上templates
        return "login";
    }

    @GetMapping("/ok")
    public String ok(){
        return "ok";
    }
}
