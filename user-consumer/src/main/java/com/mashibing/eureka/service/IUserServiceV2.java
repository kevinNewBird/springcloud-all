package com.mashibing.eureka.service;

import com.mashibing.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/***********************
 * @Description: 用户远程feign
 *       结合eureka,使用服务名来进行调用,name使用服务名user-provider, url去掉<BR>
 * @author: zhao.song
 * @since: 2021/4/14 18:09
 * @version: 1.0
 ***********************/
@FeignClient(name = "user-provider")
public interface IUserServiceV2 extends UserApi {


    /*
    这种方式何RestTemplate 没有区别, 优点就是没有代码侵入,方便做异构系统
     */
    @GetMapping("/alive")
    String alive();

    @GetMapping("/register")
    public String register();

    @GetMapping("/getById")
    public String getById(Integer id);
}
