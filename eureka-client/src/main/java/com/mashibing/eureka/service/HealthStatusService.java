package com.mashibing.eureka.service;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Service;

/***********************
 * @Description: 服务的自我健康检查手动上报(spring-boot-actuator) <BR>
 * @author: zhao.song
 * @since: 2021/4/10 23:29
 * @version: 1.0
 ***********************/
@Service
public class HealthStatusService implements HealthIndicator {

    // 服务在注册中心的状态
    private boolean status = true;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Description: 基于第三方组件actuator实现 <BR>
     *
     * @author zhao.song    2021/4/10 23:32
     * @param :
     * @return {@link org.springframework.boot.actuate.health.Health}
     */
    @Override
    public Health health() {
        if (status){
            return new Health.Builder().up().build();
        }else {
            return new Health.Builder().down().build();
        }
    }
}
