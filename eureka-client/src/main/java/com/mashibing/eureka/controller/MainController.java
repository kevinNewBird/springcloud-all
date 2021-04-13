package com.mashibing.eureka.controller;

import com.mashibing.eureka.pojo.Person;
import com.mashibing.eureka.service.HealthStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;

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

    @GetMapping("/getMap")
    public Map<String,String> getMap(){
        return Collections.singletonMap("id", "100");
    }

    @GetMapping("/getObj")
    public Person getObj(){
        return new Person(1, "xiaoliuliu111");
    }

    @GetMapping("/getObj2")
    public Person getObj2(String name){
        return new Person(1, name);
    }

    @PostMapping("/postObj")
    public Person postObj(@RequestParam String name){
        return new Person(1, name);
    }

    @PostMapping("/postObj2")
    public Person postObj(@RequestParam String name,@RequestBody Map<String,String> map){
        System.out.println(map);
        return new Person(1, name);
    }

    @PostMapping("/postLocation")
    public URI postLocation(@RequestBody Person oPerson, HttpServletResponse response) throws URISyntaxException {
        System.out.println(oPerson);
        URI uri = new URI("http://www.baidu.com?wd=" + oPerson.getName());
        response.addHeader("Location", uri.toString());
        return uri;
    }


    @GetMapping("/health")
    public String health(boolean status) {
        healthService.setStatus(status);
        return status ? "服务上线" : "服务下线";
    }

}
