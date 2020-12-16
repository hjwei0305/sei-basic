package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.dto.DataAuthorizeTypeDto;
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
 * @version 2020-02-07 16:47
 */
public class DataAuthorizeTypeControllerTest extends BaseUnitTest {
    @Autowired
    private DataAuthorizeTypeController controller;

    @Test
    public void findAll() {
        ResultData resultData = controller.findAll();
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void save() {
        DataAuthorizeTypeDto dto = new DataAuthorizeTypeDto();
        dto.setAuthorizeEntityTypeId("6CDA2DD4-4984-11EA-ADFF-0242C0A84607");
        dto.setFeatureCode("BEIS-HTPZ-YHZH");
        dto.setName("测试");
        dto.setCode("TEST");
        dto.setAuthorizeEntityTypeName("bbb");
        ResultData resultData = controller.save(dto);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void getByDataRole() {
        String dataRoleId = "72FA053A-4A23-11EA-8AF0-0242C0A84607";
        ResultData resultData = controller.getByDataRole(dataRoleId);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void getByEntityClassNameAndFeature() {
        String entityClassName = "com.changhong.sei.basic.entity.corporation";
        String featureCode = "BEIS-HTPZ-YHZH";
        ResultData resultData = controller.getByEntityClassNameAndFeature(entityClassName, featureCode);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }
}