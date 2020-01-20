package com.changhong.sei.basic.manager.client;

import com.changhong.com.sei.core.test.BaseUnitTest;
import org.apache.kafka.common.protocol.types.Field;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

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
        String className = "com.ecmp.basic.entity.Menu";
        String number = generator.getNumber(className);
        System.out.println("number："+number);
    }
}