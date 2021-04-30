package com.mashibing.crsf;

import com.mashibing.crsf.entity.CusUser;
import com.mashibing.crsf.repository.UserJDBCRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Crsf1ApplicationTests {

    @Autowired
    private UserJDBCRepository userJDBCRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void testFindByUsername() {
        CusUser oUser = userJDBCRepository.findByUsername("jdbc");
        System.out.println(oUser);
    }

}
