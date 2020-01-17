package com.changhong.sei.basic.service;

import com.changhong.com.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.util.JsonUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * <strong>实现功能:</strong>
 * <p></p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-17 22:27
 */
public class HelloServiceImplTest extends BaseUnitTest {
    @Autowired
    private HelloServiceImpl service;

    @Test
    public void sayHello() {
        String name = "王锦光";
        ResultData result = service.sayHello(name);
        System.out.println(JsonUtils.toJson(result));
    }
}