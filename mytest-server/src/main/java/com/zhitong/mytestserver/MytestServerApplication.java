package com.zhitong.mytestserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@MapperScan(basePackages = "com.zhitong.mytestserver.dao")
@EnableEurekaClient
@EnableDiscoveryClient
public class MytestServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MytestServerApplication.class, args);
    }

}
