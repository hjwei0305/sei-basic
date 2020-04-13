package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.dto.FeatureRoleDto;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 实现功能:
 *
 * @author 王锦光 wangjg
 * @version 2020-04-13 16:57
 */
public class UserFeatureRoleControllerTest extends BaseUnitTest {
    @Autowired
    private UserFeatureRoleController controller;

    @Test
    public void getChildrenFromParentId() {
        String parentId = "C1C6F9B8-7480-11EA-8858-0242C0A84603";
        ResultData<List<FeatureRoleDto>> resultData = controller.getChildrenFromParentId(parentId);
        LOG.debug(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }
}