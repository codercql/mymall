package com.chen.mymall.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@ImportResource(locations = {"classpath:dubbo.xml"})
@SpringBootApplication
public class MymallUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(MymallUserApplication.class, args);
    }

}

//测试 hot-fix分支

