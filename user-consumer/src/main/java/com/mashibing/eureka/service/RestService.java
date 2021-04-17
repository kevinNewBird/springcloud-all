package com.mashibing.eureka.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/***********************
 * @Description: rest 请求服务<BR>
 * @author: zhao.song
 * @since: 2021/4/17 22:14
 * @version: 1.0
 ***********************/
@Service
public class RestService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "back")
    public String alive(){
        //使用注册服务名user-provider方式请求, 必须在restTemplate上加@LoadBalanced
        // ,  否则映射到具体的ip地址上
        return restTemplate.getForObject("http://user-provider/alive", String.class);
    }

    public String back() {
        return "请求失败~bbb";
    }
}
