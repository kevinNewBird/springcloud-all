package com.mashibing.eureka.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.converters.Auto;
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

import java.util.Arrays;
import java.util.List;

/***********************
 * @Description: TODO 类描述<BR>
 * @author: zhao.song
 * @since: 2021/4/9 0:25
 * @version: 1.0
 ***********************/
@RestController
public class MainController {

    @Autowired
    DiscoveryClient client;//spring cloud抽象接口,照着eureka抽出来
//    EurekaClient client;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LoadBalancerClient lb;

    @GetMapping("/hello")
    public String sayHello() {
        return "hello eureka client2";
    }


    @GetMapping("/client")
    public String client() {
//        List<String> services = client.getServices();
        List<String> services = client.getServices();
//        System.out.println(ToStringBuilder.reflectionToString(services));
        System.out.println(services);
        return "hello eureka client2";
    }

    @GetMapping("/client2")
    public Object client2() {
        return client.getInstances("provider");
    }

    @GetMapping("/client3")
    public String client3() {
        List<ServiceInstance> instances = client.getInstances("provider");
        for (ServiceInstance ins : instances) {
            System.out.println(ToStringBuilder.reflectionToString(ins));
        }
        if (instances.size() > 0) {
            //服务
            EurekaServiceInstance instance = (EurekaServiceInstance) instances.get(0);
            InstanceInfo instanceInfo = instance.getInstanceInfo();
            if (instanceInfo.getStatus() == InstanceInfo.InstanceStatus.UP) {
                String url = "http://" + instanceInfo.getHostName() + ":" + instanceInfo.getPort() + "/hello";
                System.out.println("url:" + url);
                String respStr = restTemplate.getForObject(url, String.class);
                System.out.println("respStr:" + respStr);
            }

        }
        return "xxxx";
    }

    @GetMapping("/client4")
    public String client4() {
        // ribbon 完成客户端的负载均衡,过滤掉down的节点
        RibbonLoadBalancerClient.RibbonServer instance = (RibbonLoadBalancerClient.RibbonServer) lb.choose("provider");

        String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/hello";
        System.out.println("url:" + url);
        String respStr = restTemplate.getForObject(url, String.class);
        System.out.println("respStr:" + respStr);

        return "xxxx";
    }

}
