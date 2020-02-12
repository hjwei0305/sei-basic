package com.changhong.sei.basic.config;

import com.changhong.sei.basic.service.cust.CorporationServiceCust;
import com.changhong.sei.basic.service.cust.CorporationServiceCustBase;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 实现功能: 自定义业务逻辑扩展配置
 *
 * @author 王锦光 wangjg
 * @version 2020-02-12 15:46
 */
@Configuration
public class ServiceCustAutoConfig {
    /**
     * 公司业务逻辑扩展实现
     * @return 扩展实现
     */
    @Bean
    @ConditionalOnMissingBean(CorporationServiceCust.class)
    public CorporationServiceCust corporationServiceCust(){
        return new CorporationServiceCustBase();
    }
}
