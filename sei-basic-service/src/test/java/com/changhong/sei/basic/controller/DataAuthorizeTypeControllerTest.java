package com.changhong.sei.basic.controller;

import com.changhong.com.sei.core.test.BaseUnitTest;
import com.changhong.sei.basic.dto.DataAuthorizeTypeDto;
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
    public void save(){
        DataAuthorizeTypeDto dto = new DataAuthorizeTypeDto();
        dto.setAuthorizeEntityTypeId("6CDA2DD4-4984-11EA-ADFF-0242C0A84607");
        dto.setFeatureId("4CFD6B6D-4691-11EA-911F-0242C0A84604");
        dto.setName("测试");
        dto.setCode("TEST");
        dto.setFeatureName("新增与修改");
        dto.setAuthorizeEntityTypeName("bbb");
        ResultData resultData = controller.save(dto);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }
}