package com.changhong.sei.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * <strong>实现功能:</strong>
 * <p>REST服务主程序</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2019-12-18 10:41
 */
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.changhong.sei.basic.manager.client",
        "com.changhong.sei.notify.api"})
public class BasicRestApplication {
    public static void main(String[] args) {
        SpringApplication.run(BasicRestApplication.class, args);
    }
}
