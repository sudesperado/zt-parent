package com.zhitong.uruleclientserver;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class UruleClientServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UruleClientServerApplication.class, args);
    }
}
