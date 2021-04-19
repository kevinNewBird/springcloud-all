package com.mashibing.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class AdminDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminDemoApplication.class, args);
    }

}
