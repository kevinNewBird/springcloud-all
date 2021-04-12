package com.mashibing.eureka.controller;

import com.mashibing.eureka.service.HealthStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${server.port}")
    private String port;

    @Autowired
    private HealthStatusService healthService;

    @GetMapping("/hello")
    public String sayHello(){
        return "hello eureka client0,我的port: " + port;
    }

    @GetMapping("/health")
    public String health(boolean status) {
        healthService.setStatus(status);
        return status ? "服务上线" : "服务下线";
    }

}
