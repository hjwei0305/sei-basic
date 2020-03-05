package com.changhong.sei.basic.service.client;

import com.changhong.sei.serial.sdk.SerialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 实现功能: 给号器
 *
 * @author 王锦光 wangjg
 * @version 2020-01-19 21:39
 */
@Component
public class NumberGenerator {
    @Autowired
    private SerialService serialService;

    /**
     * 获取一个序列编号.
     *
     * @param entityClass 业务实体类
     * @return 序列编号
     */
    public String getNumber(Class entityClass) {
        return serialService.getNumber(entityClass);
    }
}
