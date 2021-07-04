package com.chen.mymall.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

@ImportResource(locations = {"classpath:dubbo.xml"})
@SpringBootApplication
//@ComponentScan(basePackages = "com.chen.mymall.product")
public class MymallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(MymallProductApplication.class, args);
    }

}
