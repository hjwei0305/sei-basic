package com.changhong.sei.basic.controller;

import com.changhong.com.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.util.JsonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * 实现功能:
 *
 * @author 王锦光 wangjg
 * @version 2020-02-06 21:06
 */
public class UserControllerTest extends BaseUnitTest {
    @Autowired
    private UserController controller;

    @Test
    public void getUserInformation() {
        String userId = "1F30A429-CDBB-11E8-B852-0242C0A8441B";
        ResultData resultData = controller.getUserInformation(userId);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void getUserAuthorizedFeatureMaps() {
        String userId = "B54E8964-D14D-11E8-A64B-0242C0A8441B";
        ResultData resultData = controller.getUserAuthorizedFeatureMaps(userId);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }
}