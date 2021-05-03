package com.mashibing.jwt01.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/***********************
 * @Description: controller <BR>
 * @author: zhao.song
 * @since: 2021/5/3 11:02
 * @version: 1.0
 ***********************/
@RestController
public class MainController {

    @GetMapping("/jwt")
    public String jwt() {
        return "hi,jwt";
    }
}
