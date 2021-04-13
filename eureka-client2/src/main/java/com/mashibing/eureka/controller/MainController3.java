package com.mashibing.eureka.controller;

import com.mashibing.eureka.pojo.Person;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/***********************
 * @Description: Restful 风格的API调用风格 <BR>
 * @author: zhao.song
 * @since: 2021/4/14 0:15
 * @version: 1.0
 ***********************/
@RestController
public class MainController3 {


    @Autowired
    RestTemplate restTemplate;

    /**
     * Description: 自动处理url <BR>
     *
     * @param :
     * @return {@link java.lang.String}
     * @author zhao.song    2021/4/12 17:31
     */
    @GetMapping("/client10")
    public String client10() {
        String url = "http://provider/hello";

//        String url = "http://provider/hello";
        System.out.println("url:" + url);
        String respStr = restTemplate.getForObject(url, String.class);
        // 较之getForObject返回信息会多一点
        ResponseEntity<String> entity = restTemplate.getForEntity(url, String.class);
        // org.springframework.http.ResponseEntity@2b130e15[status=200,headers=[Content-Type:"text/plain;charset=UTF-8"
        // , Content-Length:"37", Date:"Tue, 13 Apr 2021 16:54:56 GMT"
        // , Keep-Alive:"timeout=60", Connection:"keep-alive"],body=hello eureka client0,我的port: 8082]
        System.out.println(ToStringBuilder.reflectionToString(entity));

        System.out.println("respStr:" + respStr);

        return respStr;
    }


    @GetMapping("/client11")
    public Object client11() {
        String url = "http://provider/getMap";
//        String url = "http://provider/hello";
        System.out.println("url:" + url);
        Map<String, String> respStr = restTemplate.getForObject(url, Map.class);
        // 较之getForObject返回信息会多一点
        ResponseEntity<Map> entity = restTemplate.getForEntity(url, Map.class);
        // org.springframework.http.ResponseEntity@2b130e15[status=200,headers=[Content-Type:"text/plain;charset=UTF-8"
        // , Content-Length:"37", Date:"Tue, 13 Apr 2021 16:54:56 GMT"
        // , Keep-Alive:"timeout=60", Connection:"keep-alive"],body=hello eureka client0,我的port: 8082]
        System.out.println(ToStringBuilder.reflectionToString(entity));

        System.out.println("respStr:" + respStr);

        return respStr;
    }


    // 无参请求
    @GetMapping("/client12")
    public Object client12() {
        String url = "http://provider/getObj";
         Person obj = restTemplate.getForObject(url, Person.class);
        return obj;
    }

    // 有参请求
    @GetMapping("/client13")
    public Object client13() {
        // 参数占位符
        String url = "http://provider/getObj2?name={1}";
        // 第二种占位方式
        String url2 = "http://provider/getObj2?name={name}";
        Map<String, String> params = Collections.singletonMap("name", "xiaoliuliu789999");
        Person obj = restTemplate.getForObject(url, Person.class, "xiaoliuliu2232323");
        Person obj2 = restTemplate.getForObject(url2, Person.class, params);
        System.out.println(obj2);
        return obj;
    }

    // POST有参请求
    @GetMapping("/client14")
    public Object client14() {
        String url = "http://provider/postObj?name={name}";

        Map<String, String> params = Collections.singletonMap("name", "xiaoliuliu22222");
        Person obj = restTemplate.postForObject(url, null, Person.class, params);
        return obj;
    }

    // POST有参请求
    @GetMapping("/client15")
    public Object client15() {
        String url = "http://provider/postObj2?name={name}";

        Map<String, String> params = Collections.singletonMap("name", "xiaoliuliu22222");
        Map<String, String> paraBody = Collections.singletonMap("name2", "xiaoliuliu3333");
        Person obj = restTemplate.postForObject(url, paraBody, Person.class, params);
        return obj;
    }

    // POST有参请求
    @GetMapping("/client16")
    public String client16(HttpServletResponse response) throws IOException {
        String url = "http://provider/postLocation";

        Map<String, String> params = Collections.singletonMap("name", "xiaoliuliu22222");
        URI location = restTemplate.postForLocation(url, params, Person.class);
        //跳转
        response.sendRedirect(location.toURL().toString());
        System.out.println("location = " + location.toURL().toString());
        return location.toURL().toString();
    }

}
