package com.mashibing.crsf.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/***********************
 * @Description: TODO 类描述<BR>
 * @author: zhao.song
 * @since: 2021/4/27 0:29
 * @version: 1.0
 ***********************/
@RestController
public class HiController {

    @GetMapping("/hi")
    public String sayHi() {
        System.out.println("hi");
        return "hi come on!";
    }
}
