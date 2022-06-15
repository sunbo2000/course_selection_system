package org.snbo.ucenterservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author sunbo
 * @create 2022-04-06-16:37
 */

@SpringBootApplication
@ComponentScan(basePackages = {"org.snbo"})
@MapperScan("org.snbo.ucenterservice.mapper")
@EnableDiscoveryClient
@EnableFeignClients
public class UCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UCenterApplication.class);
    }
}
