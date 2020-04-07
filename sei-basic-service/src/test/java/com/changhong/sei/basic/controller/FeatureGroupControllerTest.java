package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.dto.FeatureGroupDto;
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
 * @version 2020-03-12 9:26
 */
public class FeatureGroupControllerTest extends BaseUnitTest {
    @Autowired
    private FeatureGroupController controller;

    @Test
    public void findAll() {
        ResultData<List<FeatureGroupDto>> resultData = controller.findAll();
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void findOne() {
        String id = "0753E9CF-5031-11E9-A211-0242C0A8440C";
        ResultData<FeatureGroupDto> resultData = controller.findOne(id);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void save() {
        FeatureGroupDto dto = new FeatureGroupDto();
        dto.setCode("BASIC-TEST");
        dto.setName("基础应用-测试");
        dto.setAppModuleId("BDC7B155-4498-11EA-8468-0242C0A84604");
        ResultData<FeatureGroupDto> resultData = controller.save(dto);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void delete() {
        String id = "3E0DE12A-78D7-11EA-A441-48E244F5A3DB";
        ResultData<?> resultData = controller.delete(id);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }
}