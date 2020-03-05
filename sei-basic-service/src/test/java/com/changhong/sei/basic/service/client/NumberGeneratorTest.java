package com.changhong.sei.basic.service.client;

import com.changhong.sei.basic.entity.Menu;
import com.changhong.sei.basic.entity.Organization;
import com.changhong.sei.basic.entity.Position;
import com.changhong.sei.basic.entity.PositionCategory;
import com.changhong.sei.core.test.BaseUnitTest;
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
    public void getNumberByClassName() {
        String number = generator.getNumber(Menu.class);
        System.out.println("number："+number);
    }
}