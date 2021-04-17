package com.mashibing.eureka.service;

import com.netflix.hystrix.exception.HystrixTimeoutException;
import feign.FeignException;
import feign.hystrix.FallbackFactory;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.stereotype.Component;

/***********************
 * @Description: 精细化定义熔断返回<BR>
 * @author: zhao.song
 * @since: 2021/4/17 20:51
 * @version: 1.0
 ***********************/
//泛型是表示该工厂在哪个Feign远程调用上使用
@Component
public class UserProviderBackFactory implements FallbackFactory<IUserServiceV2> {


    @Override
    public IUserServiceV2 create(Throwable cause) {
        //cause 既包含了本地的异常,也包含了远端的异常
        return new IUserServiceV2() {
            @Override
            public String alive() {
                cause.printStackTrace();
                if (cause instanceof FeignException.InternalServerError) {
                    return "远程服务器500";
                } else if (cause instanceof HystrixTimeoutException) {
                    return "远程调用超时";
                } else {
                    return "呵呵";
                }

            }

            @Override
            public String register() {
                return "哈哈";
            }

            @Override
            public String getById(Integer id) {
                return null;
            }

            @Override
            public String isAlive() {
                return null;
            }
        };
    }
}
