package com.changhong.sei.basic.service;

import com.changhong.sei.basic.api.HelloService;
import com.changhong.sei.basic.manager.HelloManager;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.context.SessionUser;
import com.changhong.sei.core.dto.ResultData;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

/**
 * <strong>实现功能:</strong>
 * <p>调试你好的API服务实现</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2019-12-16 17:22
 */
@Service
@RefreshScope
@Api(value = "HelloService", tags = "调试你好的API服务")
public class HelloServiceImpl implements HelloService {
    @Autowired
    private HelloManager manager;

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
        String data = manager.sayHello(name, testKey);
        return ResultData.success(data);
    }
}
