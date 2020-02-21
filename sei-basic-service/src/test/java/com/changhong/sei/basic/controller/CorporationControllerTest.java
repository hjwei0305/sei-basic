package com.changhong.sei.basic.controller;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.test.BaseUnitTest;
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
    private CorporationController controller;

    @Test
    public void findByErpCode() {
        String erpCode = "Q000";
        ResultData resultData = controller.findByErpCode(erpCode);
        Assert.assertTrue(resultData.successful());
        System.out.println(JsonUtils.toJson(resultData));
    }

    @Test
    public void getUserAuthorizedEntities(){
        ResultData resultData = controller.getUserAuthorizedEntities(null);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }
}