package com.changhong.sei.basic.service.client;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.exception.ServiceException;
import com.changhong.sei.serial.sdk.SerialService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 实现功能: 序列号生成器
 *
 * @author 王锦光 wangjg
 * @version 2021-04-15 20:30
 */
@Component
public class SerialGenerator {
    @Autowired
    private SerialService serialService;

    /**
     * 通过类型获取编号
     * @param clazz 实体类型
     * @return 编号
     */
    public String getNumber(Class<?> clazz) {
        try {
            String number = serialService.getNumber(clazz);
            if (StringUtils.isBlank(number)) {
                // 00197 = 调用给号服务，获取的编号为空！
                throw new ServiceException(ContextUtil.getMessage("00123"));
            }
            return number;
        } catch (Exception e) {
            // 00124 = 调用给号服务异常！
            throw new ServiceException(ContextUtil.getMessage("00124"), e);
        }
    }

    /**
     * 通过类型代码获取编号
     * @param classCode 实体类型代码
     * @return 编号
     */
    public String getNumber(String classCode) {
        try {
            String number = serialService.getNumber(classCode, null, null);
            if (StringUtils.isBlank(number)) {
                // 00197 = 调用给号服务，获取的编号为空！
                throw new ServiceException(ContextUtil.getMessage("00123"));
            }
            return number;
        } catch (Exception e) {
            // 00198 = 调用给号服务异常！
            throw new ServiceException(ContextUtil.getMessage("00124"), e);
        }
    }
}
