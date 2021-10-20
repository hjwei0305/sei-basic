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
 * @version 2020-05-26 13:46
 */
public class FeatureRoleControllerTest extends BaseUnitTest {

    @Autowired
    private FeatureRoleController controller;

    @Test
    public void findByFeatureRoleGroup() {
        String roleGroupId = "154B3D89-5DF3-11EA-8202-0242C0A84605";
        ResultData<List<FeatureRoleDto>> resultData = controller.findByFeatureRoleGroup(roleGroupId);
        LOG.debug(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }
}