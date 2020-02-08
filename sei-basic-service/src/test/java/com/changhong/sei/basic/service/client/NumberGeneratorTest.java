package com.changhong.sei.basic.service.client;

import com.changhong.com.sei.core.test.BaseUnitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 实现功能:
 *
 * @author 王锦光 wangjg
 * @version 2020-01-20 10:51
 */
public class NumberGeneratorTest extends BaseUnitTest {
    @Autowired
    private NumberGenerator generator;

    @Test
    public void getNumber() {
        //String className = "com.ecmp.basic.entity.Menu";
        String className = "com.changhong.sei.basic.entity.Menu";
        String number = generator.getNumber(className);
        System.out.println("number："+number);
    }
}