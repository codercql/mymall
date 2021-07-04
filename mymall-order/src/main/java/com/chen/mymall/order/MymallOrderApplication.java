package com.chen.mymall.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@ImportResource(locations = {"classpath:dubbo.xml"})
@SpringBootApplication
public class MymallOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(MymallOrderApplication.class, args);
    }

}
