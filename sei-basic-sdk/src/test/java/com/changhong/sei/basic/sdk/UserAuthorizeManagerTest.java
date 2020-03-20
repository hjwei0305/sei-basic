package com.changhong.sei.basic.sdk;

import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * 实现功能:
 *
 * @author 王锦光 wangjg
 * @version 2020-03-20 10:44
 */
public class UserAuthorizeManagerTest extends BaseUnitTest {
    @Autowired
    private UserAuthorizeManager manager;

    @Test
    public void getNormalUserAuthorizedEntitiesFromBasic() {
        String entityClassName = "com.changhong.sei.basic.entity.Corporation";
        String featureCode = "";
        String userId = "02620F45-5EAF-11EA-A2E3-0242C0A84605";
        List<String> result = manager.getNormalUserAuthorizedEntitiesFromBasic(entityClassName, featureCode, userId);
        Assert.assertNotNull(result);
        System.out.println(JsonUtils.toJson(result));
    }
}