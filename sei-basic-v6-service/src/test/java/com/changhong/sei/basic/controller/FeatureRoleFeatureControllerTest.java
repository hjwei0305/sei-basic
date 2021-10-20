package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.dao.FeatureRoleDao;
import com.changhong.sei.basic.dto.FeatureRoleDto;
import com.changhong.sei.basic.entity.FeatureRole;
import com.changhong.sei.core.dto.ResultData;
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
 * @version 2020-02-06 17:33
 */
public class FeatureRoleFeatureControllerTest extends BaseUnitTest {
    @Autowired
    private FeatureRoleFeatureController controller;

    @Test
    public void getUnassignedChildren() {
        String parentId = "10206823-47EA-11EA-BFEE-0242C0A84604";
        ResultData resultData = controller.getUnassignedChildren(parentId);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void getChildrenFromParentId() {
        String parentId = "10206823-47EA-11EA-BFEE-0242C0A84604";
        ResultData resultData = controller.getChildrenFromParentId(parentId);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void getFeatureTree() {
        String featureRoleId = "D6FF7A66-5DF1-11EA-8202-0242C0A84605";
        ResultData resultData = controller.getFeatureTree(featureRoleId);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void getUnassignedFeatureTree() {
        String appModuleId = "997D47A9-53BD-11EA-AFBB-0242C0A84605";
        String featureRoleId = "D6FF7A66-5DF1-11EA-8202-0242C0A84605";
        ResultData resultData = controller.getUnassignedFeatureTree(appModuleId, featureRoleId);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void getParentsFromChildId() {
        String featureId = "0E9BEC7F-28F8-11E8-ADEE-0242C0A84202";
        ResultData<List<FeatureRoleDto>> resultData = controller.getParentsFromChildId(featureId);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }
}