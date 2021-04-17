package com.mashibing.eureka.service;

import org.springframework.stereotype.Component;

/***********************
 * @Description: TODO 类描述<BR>
 * @author: zhao.song
 * @since: 2021/4/17 20:51
 * @version: 1.0
 ***********************/
@Component
public class UserProviderBack implements IUserServiceV2 {

    @Override
    public String isAlive() {
        return "降级了";
    }

    @Override
    public String alive() {
        return "熔断了";
    }

    @Override
    public String register() {
        return null;
    }

    @Override
    public String getById(Integer id) {
        return null;
    }
}
