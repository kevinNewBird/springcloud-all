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
import java.util.Random;

/***********************
 * @Description: fu<BR>
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


    @GetMapping("/client6")
    public String client6() {
        ServiceInstance instance = lb.choose("provider");
        String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/hello";
//        String url = "http://provider/hello";
        System.out.println("url:" + url);
        String respStr = restTemplate.getForObject(url, String.class);
        System.out.println("respStr:" + respStr);

        return respStr;
    }

    @Autowired
    DiscoveryClient discoveryClient;
    /**
     * Description: 自定义负载均衡策略 <BR>
     *
     * @param :
     * @return {@link java.lang.String}
     * @author zhao.song    2021/4/12 17:04
     */
    @GetMapping("/client7")
    public String client7() {
        List<ServiceInstance> instances = discoveryClient.getInstances("provider");

        // 自定义轮询算法
        int index = new Random().nextInt(instances.size());
        // 替代choose
        ServiceInstance instance = instances.get(index);

        return "respStr";
    }

    @GetMapping("/client8")
    public String client8() {
        ServiceInstance instance = lb.choose("provider");
        String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/hello";
//        String url = "http://provider/hello";
        System.out.println("url:" + url);
        String respStr = restTemplate.getForObject(url, String.class);
        System.out.println("respStr:" + respStr);

        return respStr;
    }

    /**
     * Description: 自动处理url <BR>
     *
     * @author zhao.song    2021/4/12 17:31
     * @param :
     * @return {@link java.lang.String}
     */
    @GetMapping("/client9")
    public String client9() {
        String url = "http://provider/hello";
//        String url = "http://provider/hello";
        System.out.println("url:" + url);
        String respStr = restTemplate.getForObject(url, String.class);
        System.out.println("respStr:" + respStr);

        return respStr;
    }

}
