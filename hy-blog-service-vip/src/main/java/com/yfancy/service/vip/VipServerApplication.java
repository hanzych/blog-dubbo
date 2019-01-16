package com.yfancy.service.vip;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(value = "com.yfancy.service.vip.dao")
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class VipServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(VipServerApplication.class, args);
    }

}

