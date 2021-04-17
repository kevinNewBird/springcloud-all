package com.mashibing.eureka.service;

import com.mashibing.api.UserApi;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/***********************
 * @Description: 用户远程feign
 *       结合eureka,使用服务名来进行调用,name使用服务名user-provider, url去掉<BR>
 * @author: zhao.song
 * @since: 2021/4/14 18:09
 * @version: 1.0
 ***********************/
@FeignClient(name = "user-provider",fallback = UserProviderBack.class)
public interface IUserServiceV2 extends UserApi {

    /**
     * 假设UserApi接口中没有及时的定义我们需要的方法
     * , 我们也可以在继承了接口后动态的增加
     */

    /*
    这种方式何RestTemplate 没有区别, 优点就是没有代码侵入,方便做异构系统
     */
    @GetMapping("/alive")
    String alive();

    @GetMapping("/register")
    public String register();

    /**
     * Description: 这里的getMapping/RequestParam 都是给Feign看的 <BR>
     *
     * @author zhao.song    2021/4/15 17:57
     * @param id:
     * @return {@link java.lang.String}
     */
    @GetMapping("/getById")
    public String getById(@RequestParam Integer id);
}
