package com.mashibing.eureka.controller;

import com.mashibing.eureka.service.IUserServiceV2;
import com.mashibing.eureka.service.RestService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.StringJoiner;

/***********************
 * @Description: TODO 类描述<BR>
 * @author: zhao.song
 * @since: 2021/4/14 17:52
 * @version: 1.0
 ***********************/
@RestController
public class UserController {


    @Value("${myconfig}")
    private String name;


    @Value("${server.port}")
    private String port;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RestService restService;

    @Autowired
    private IUserServiceV2 userService;



    /**
     * Description: 整合Feign熔断 <BR>
     *
     * @author zhao.song    2021/4/17 21:23
     * @param :
     * @return {@link java.lang.String}
     */
    @GetMapping("/alive")
    public String alive() {
        //1.Hystrix 整合 Feign
        StringJoiner joiner = new StringJoiner(">>>>>");
        return joiner.add("Consuemer port:" + port).add(userService.alive()).toString();
    }


    /**
     * Description: 整合RestTemplate熔断 <BR>
     *
     * @author zhao.song    2021/4/17 21:23
     * @param :
     * @return {@link java.lang.String}
     */
    @GetMapping("/alive2")
    public String isAlive() {
        //1. Hystrix 整合 RestTemplate
//        return restTemplate.getForObject("http://user-provider/user/isAlive", String.class);
        StringJoiner joiner = new StringJoiner(">>>>>");
        return joiner.add("Consuemer port:" + port).add(restService.alive()).toString();
    }

    @GetMapping("/getById")
    public String getById(Integer id) {
        return userService.getById(id);
    }

    @GetMapping("/register")
    public String register() {
        return userService.register();
    }


    public String back() {
        return "请求失败~bbb";
    }


}
