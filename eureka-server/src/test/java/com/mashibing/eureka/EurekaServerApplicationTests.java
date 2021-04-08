package com.mashibing.eureka;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class EurekaServerApplicationTests {

    public static final String REPORT_EDIT_KEY = "1";
    public static final String REPORT_DEL_KEY = "2";
    public static final String REPORT_DOC_KEY = "3";

    @Test
    void contextLoads() {
    }

    public static List<String> keyContainer = Collections.unmodifiableList(Arrays.asList(REPORT_EDIT_KEY, REPORT_DEL_KEY
            , REPORT_DOC_KEY));

    @Test
    public void test() {
        // 选题权限的key容器
        System.out.println(keyContainer.stream()
                .filter(key -> !REPORT_EDIT_KEY.equals(key) && !REPORT_DEL_KEY.equals(key)).collect(Collectors.toList()));
        System.out.println(keyContainer);

    }

}
