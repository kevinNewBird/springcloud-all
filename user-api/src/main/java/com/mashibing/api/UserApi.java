package com.mashibing.api;

import org.springframework.web.bind.annotation.RequestMapping;

/***********************
 * @Description: 接口类: 暴露给provider和consumer共同使用,实现统一 <BR>
 * @author: zhao.song
 * @since: 2021/4/15 1:18
 * @version: 1.0
 ***********************/
//如果下游同时使用了feign和hystrix,由于这个的存在会导致被注册两次
// (feign整合hystrix, hystrix的fallback需要集成feign的入口)
//@RequestMapping("/user")
public interface UserApi {

    @RequestMapping("/isAlive")
    public String isAlive();

    @RequestMapping("/alive")
    public String alive();

    @RequestMapping("/getById")
    public String getById(Integer id);

}
