package com.chen.mymall.seckill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@ImportResource(locations = {"classpath:dubbo.xml"})
//@ComponentScan(basePackages = "com.")
@SpringBootApplication
public class MymallSeckillApplication {

    public static void main(String[] args) {
        SpringApplication.run(MymallSeckillApplication.class, args);
    }

}
