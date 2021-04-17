package com.mashibing.eureka.controller;

import com.mashibing.eureka.service.IUserServiceV2;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/***********************
 * @Description: TODO 类描述<BR>
 * @author: zhao.song
 * @since: 2021/4/14 17:52
 * @version: 1.0
 ***********************/
@RestController
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

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
        return  userService.alive();
    }


    /**
     * Description: 整合RestTemplate熔断 <BR>
     *
     * @author zhao.song    2021/4/17 21:23
     * @param :
     * @return {@link java.lang.String}
     */
    @GetMapping("/isAlive")
    @HystrixCommand(fallbackMethod = "back")
    public String isAlive() {
        //1. Hystrix 整合 RestTemplate
        return restTemplate.getForObject("http://user-provider/user/isAlive", String.class);
    }

    @GetMapping("/getById")
    public String getById(Integer id) {
        return userService.getById(id);
    }

    @GetMapping("/register")
    public String register() {
        return null;
    }


    public String back() {
        return "请求失败~bbb";
    }


}
