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
     * Description: 整合restTemplate的服务熔断降级<BR>
     *
     * @author zhao.song    2021/4/17 20:41
     * @param :  
     * @return {@link java.lang.String}
     */
    @GetMapping("/alive")
    @HystrixCommand(fallbackMethod = "back", commandProperties = {
            @HystrixProperty(name = "fallback.enabled", value = "false")
    })
    public String alive() {
//        return userService.alive();
        //1. Hystrix 整合 RestTemplate
        String response = restTemplate.getForObject("http://user-provider/user/alive", String.class);
        return response;
    }

    /**
     * Description: 整合feign的服务熔断降级 <BR>
     *
     * @author zhao.song    2021/4/17 20:40
     * @param :  
     * @return {@link java.lang.String}
     */
    @GetMapping("/isAlive")
    public String isAlive() {
        return userService.isAlive();
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
