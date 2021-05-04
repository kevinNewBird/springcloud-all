package com.mashibing.oauth01.controller;

import jdk.nashorn.internal.runtime.logging.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***********************
 * @Description: 第三方服务 <BR>
 * @author: zhao.song
 * @since: 2021/5/4 21:01
 * @version: 1.0
 ***********************/
@RestController
@RequestMapping("/oauth2/api")
public class MainController {

    @GetMapping("/me")
    public Authentication me() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/read")
    public Authentication read() {
        System.out.println("read");
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/write")
    public Authentication write() {
        System.out.println("write");
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
