package com.mashibing.crsf.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/***********************
 * @Description: kaptcha 防机器暴力登录配置类 <BR>
 * @author: zhao.song
 * @since: 2021/4/30 17:02
 * @version: 1.0
 ***********************/
@Configuration
public class KaptchaConfig {

    @Bean
    public DefaultKaptcha getDefaultKaptcha() {
        DefaultKaptcha captchaProducer = new DefaultKaptcha();
        Properties props = new Properties();
        props.setProperty("kaptcha.border", "yes");
        props.setProperty("kaptcha.border.color", "105,179,90");
        props.setProperty("kaptcha.textproducer.font.color", "blue");
        props.setProperty("kaptcha.image.width", "310");
        props.setProperty("kaptcha.image.height", "240");
        props.setProperty("kaptcha.textproducer.font.size", "30");
        props.setProperty("kaptcha.session.key", "code");
        props.setProperty("kaptcha.textproducer.char.length", "4");
        //    props.setProperty("kaptcha.textproducer.char.string", "678");
        props.setProperty("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.ShadowGimpy");
        props.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        Config config = new Config(props);
        captchaProducer.setConfig(config);
        return captchaProducer;
    }
}
