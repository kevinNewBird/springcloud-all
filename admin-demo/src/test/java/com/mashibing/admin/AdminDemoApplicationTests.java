package com.mashibing.admin;

import com.mashibing.admin.notice3nd.DingDingMessageUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AdminDemoApplicationTests {

    @Autowired
    private DingDingMessageUtil dingDingMessageUtil;

    @Test
    void contextLoads() {
    }

    @Test
    public void test0() {
        dingDingMessageUtil.sendTextMessage("系统警告，给爷成！！！");
    }

}
