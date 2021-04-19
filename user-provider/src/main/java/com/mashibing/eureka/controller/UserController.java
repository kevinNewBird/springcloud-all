package com.mashibing.eureka.controller;

import com.mashibing.api.UserApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/***********************
 * @Description: 模拟--用户服务提供者<BR>
 * @author: zhao.song
 * @since: 2021/4/14 17:52
 * @version: 1.0
 ***********************/
@RestController
public class UserController implements UserApi {

    //计数器
    AtomicInteger counter = new AtomicInteger();

    @Value("${server.port}")
    private String port;


    public String isAlive() {
        return "is ok?";
    }

    public String alive() {
        timeout();
        /**
         * try{
         *  1. 发起想服务放的请求
         *    1.1. 判断连接超时
         *      > 这次请求,记录到服务里
         *    http请求  线程消耗
         *
         *
         *    <tip>限流,也叫隔离<tip/>
         *    map(URI,线程数)
         *    线程池(线程数)
         *
         * 阈值
         * 计数 连续失败次数达到阈值,这就叫做熔断
         *    count++
         *    if(count == 10){
         *       new Random ==1 发起请求 或者 按时间
         *       throw exception
         *    }
         *
         *    请求/不请求/半请求
         *    开   关   半开
         *
         *
         *    if(当前线程满了){
         *        throw exception;
         *    }
         *
         *    1.2. 尝试想其它服务发起请求
         *  2. 还是没成功
         * }catch(Exception e){
         *   3.降级
         *   // 3.1.避免返回不友好的错误信息
         *       -> 好看点的页面,重试按钮,联系邮箱
         *       3.2. 另外一个东西, 写到MQ里/发admin邮件
         *   return "客观稍后再来"
         * }
         *
         *
         * Hystrix干的就是这个事
         */
        return "provider project,ok or bad, port:" + port;
    }

    @Override
    public String getById(@RequestParam Integer id) {
        return String.valueOf(id);
    }


    @GetMapping("/register")
    public String register() {
        return "success";
    }

    private void timeout() {
        Optional.of(String.format("simulate hytrix, the port [%s] ready to sleep...", port)).ifPresent(System.out::println);
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int invokeMount = counter.getAndIncrement();
        Optional.of(String.format("simulate hytrix,the port [%s] %s times invoke...", port, invokeMount)).ifPresent(System.out::println);
    }

}
