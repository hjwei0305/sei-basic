package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.HelloApi;
import com.changhong.sei.basic.service.HelloService;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.context.SessionUser;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.log.LogUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <strong>实现功能:</strong>
 * <p>调试你好的API服务实现</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2019-12-16 17:22
 */
@RestController
@RefreshScope
@Api(value = "HelloApi", tags = "调试你好的API服务")
@RequestMapping(path = "basicHello", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class HelloController implements HelloApi {
    @Autowired
    private HelloService service;

    @Value("${basic.test-key}")
    private String testKey;

    /**
     * 你好
     * @param name 姓名
     * @return 返回句子
     */
    public ResultData<String> sayHello(String name) {
        SessionUser sessionUser = ContextUtil.getSessionUser();
        System.out.println(sessionUser);
        String data;
        try {
            data = service.sayHello(name, testKey);
        } catch (Exception e) {
            LogUtil.error("执行方法sayHello异常！", e);
            return ResultData.fail("执行方法sayHello异常！"+e.getMessage());
        }
        return ResultData.success(data);
    }
}
