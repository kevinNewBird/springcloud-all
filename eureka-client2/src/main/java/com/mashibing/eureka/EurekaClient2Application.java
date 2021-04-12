package com.mashibing.eureka;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


//主要用于获取client1的自定义元数据信息
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = "com.mashibing.eureka")
public class EurekaClient2Application {

    public static void main(String[] args) {
        SpringApplication.run(EurekaClient2Application.class, args);
    }

    @Bean
    @LoadBalanced//加上这个配置会把eureka里的地址转化为真正的ip地址访问
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

//    @Bean
//    public IRule myRule(){
//        // 默认的ribbon的轮询策略: return new RoundRobinRule()
//        // 改变为 return new RuandomRule();
//        return new RandomRule();
//    }
}
