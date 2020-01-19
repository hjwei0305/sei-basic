package com.changhong.sei.basic.manager.client;

import com.changhong.sei.apitemplate.ApiTemplate;
import com.changhong.sei.core.context.ContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
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
    private ApiTemplate apiTemplate;
    /**
     * 获取一个序列编号.
     * 有隔离码可为空
     *
     * @param entityClassName 业务实体类名(含包路径)
     * @param isolationCode   隔离码
     * @return 序列编号
     */
    public String getNumber(String entityClassName, String isolationCode) {
        if (StringUtils.isBlank(entityClassName)) {
            throw new IllegalArgumentException("业务实体类名不能为空");
        }
        String url = ContextUtil.getProperty("sei.number-generator-url");
        String path;
        Map<String, String> params = new HashMap<>();
        params.put("envCode", ContextUtil.getProperty("spring.cloud.config.profile"));
        params.put("entityClassName", entityClassName);
        if (StringUtils.isNotBlank(isolationCode)) {
            params.put("isolationCode", isolationCode);
            path = "/serialNumberConfig/getNumberWithIsolation";
        } else {
            path = "/serialNumberConfig/getNumber";
        }
        return apiTemplate.getByUrl(url+path, String.class, params);
    }

    /**
     * 获取一个序列编号.
     *
     * @param entityClassName 业务实体类名(含包路径)
     * @return 序列编号
     */
    public String getNumber(String entityClassName) {
        return getNumber(entityClassName, null);
    }

    /**
     * 获取一个序列编号.
     *
     * @param entityClass 业务实体类
     * @return 序列编号
     */
    public String getNumber(Class entityClass) {
        if (Objects.isNull(entityClass)) {
            throw new IllegalArgumentException("业务实体类名不能为空");
        }
        return getNumber(entityClass.getName(), null);
    }
}
