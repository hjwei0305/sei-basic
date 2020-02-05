package com.changhong.sei.basic.controller;

import com.changhong.com.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.util.JsonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 实现功能:
 *
 * @author 王锦光 wangjg
 * @version 2020-01-26 16:30
 */
public class CorporationControllerTest extends BaseUnitTest {
    @Autowired
    private CorporationController service;

    @Test
    public void findByErpCode() {
        String erpCode = "Q000";
        ResultData resultData = service.findByErpCode(erpCode);
        Assert.assertTrue(resultData.successful());
        System.out.println(JsonUtils.toJson(resultData));
    }
}