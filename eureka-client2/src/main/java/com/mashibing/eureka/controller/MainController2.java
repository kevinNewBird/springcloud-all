package com.mashibing.eureka.controller;

import com.netflix.appinfo.InstanceInfo;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.eureka.EurekaServiceInstance;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/***********************
 * @Description: TODO 类描述<BR>
 * @author: zhao.song
 * @since: 2021/4/9 0:25
 * @version: 1.0
 ***********************/
@RestController
public class MainController2 {

    @Autowired
    DiscoveryClient client;//spring cloud抽象接口,照着eureka抽出来
//    EurekaClient client;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LoadBalancerClient lb;



    @GetMapping("/client5")
    public String client5() {

        String url = "http://provider/hello";
        System.out.println("url:" + url);
        String respStr = restTemplate.getForObject(url, String.class);
        System.out.println("respStr:" + respStr);

        return "xxxx";
    }

}
