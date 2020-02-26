package com.changhong.sei.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * <strong>实现功能:</strong>
 * <p>REST服务主程序</p>
 */
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.changhong.sei.notify.api", "com.changhong.sei.basic.service.client"})
public class BasicRestApplication {
    public static void main(String[] args) {
        SpringApplication.run(BasicRestApplication.class, args);
    }
}
