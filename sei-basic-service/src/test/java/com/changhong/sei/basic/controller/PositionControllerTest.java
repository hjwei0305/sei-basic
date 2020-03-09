package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.dto.PositionCopyParam;
import com.changhong.sei.basic.dto.PositionDto;
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
    private PositionController controller;

    @Test
    public void findByOrganizationId() {
        String orgId = "C34F75A2-4703-11EA-911F-0242C0A84604";
        ResultData resultData = controller.findByOrganizationId(orgId);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void copyToOrgNodes() {
        PositionCopyParam copyParam = new PositionCopyParam();
        copyParam.setPositionId("282648C6-4881-11EA-B817-0242C0A84603");
        copyParam.setTargetOrgIds(Arrays.asList("678FB334-469A-11EA-911F-0242C0A84604"));
        ResultData resultData = controller.copyToOrgNodes(copyParam);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void save() {
        String json = "{\"organizationName\":\"共享服务事业部\",\"positionCategoryName\":\"测试岗\",\"name\":\"123444\",\"organizationId\":\"4227A5ED-5DE5-11EA-B7CF-0242C0A84605\",\"organizationCode\":\"00011\",\"positionCategoryId\":\"8C4B6638-514B-11EA-8018-0242C0A84607\",\"positionCategoryCode\":\"测试岗\"}";
        PositionDto dto = JsonUtils.fromJson(json, PositionDto.class);
        ResultData resultData = controller.save(dto);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }
}