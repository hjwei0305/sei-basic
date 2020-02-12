package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.dto.PositionCopyParam;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

/**
 * 实现功能:
 *
 * @author 王锦光 wangjg
 * @version 2020-02-03 17:11
 */
public class PositionControllerTest extends BaseUnitTest {
    @Autowired
    private PositionController service;

    @Test
    public void findByOrganizationId() {
        String orgId = "877035BF-A40C-11E7-A8B9-02420B99179E";
        ResultData resultData = service.findByOrganizationId(orgId);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void copyToOrgNodes() {
        PositionCopyParam copyParam = new PositionCopyParam();
        copyParam.setPositionId("282648C6-4881-11EA-B817-0242C0A84603");
        copyParam.setTargetOrgIds(Arrays.asList("678FB334-469A-11EA-911F-0242C0A84604"));
        ResultData resultData = service.copyToOrgNodes(copyParam);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }
}