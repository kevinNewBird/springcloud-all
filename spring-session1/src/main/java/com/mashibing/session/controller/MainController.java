package com.mashibing.session.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/***********************
 * @Description: <BR>
 * @author: zhao.song
 * @since: 2021/5/2 20:04
 * @version: 1.0
 ***********************/
@RestController
public class MainController {

    @Autowired
    HttpServletRequest request;

    @GetMapping("/list")
    public String list() {
        request.getSession().setAttribute("aaa", "0000");
        return "xxoo";
    }

    @GetMapping("/get")
    public String get() {
        return request.getSession().getAttribute("aaa").toString();
    }

}
