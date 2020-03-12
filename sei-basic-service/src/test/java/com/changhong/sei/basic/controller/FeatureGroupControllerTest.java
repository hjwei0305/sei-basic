package com.changhong.sei.basic.controller;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * 实现功能:
 *
 * @author 王锦光 wangjg
 * @version 2020-03-12 9:26
 */
public class FeatureGroupControllerTest extends BaseUnitTest {
    @Autowired
    private FeatureGroupController controller;

    @Test
    public void findAll() {
        ResultData resultData = controller.findAll();
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }
}