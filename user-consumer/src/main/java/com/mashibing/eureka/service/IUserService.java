package com.mashibing.eureka.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/***********************
 * @Description: 用户远程feign
 *       不结合eureka, 就是自定义一个client名字. 就用url属性指定服务器列表(只需要启动user-provider和user-consumer)<BR>
 * @author: zhao.song
 * @since: 2021/4/14 18:09
 * @version: 1.0
 ***********************/
@FeignClient(name = "user-provider", url = "http://localhost:8090")
public interface IUserService {

    @GetMapping("/alive")
    String alive();

    @GetMapping("/register")
    public String register();
}
