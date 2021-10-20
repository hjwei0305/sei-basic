package com.changhong.sei.basic.sdk;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 实现功能: 基础应用开发工具包配置类
 *
 * @author 王锦光 wangjg
 * @version 2020-03-20 14:55
 */
@Configuration
public class BasicSdkAutoConfig {

    @Bean
    public UserAuthorizeManager userAuthorizeManager(){
        return new UserAuthorizeManager();
    }
}
