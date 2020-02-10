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
 * @version 2020-02-10 14:10
 */
public class DataRoleAuthTypeValueControllerTest extends BaseUnitTest {
    @Autowired
    private DataRoleAuthTypeValueController controller;

    @Test
    public void getUnassignedAuthDataList() {
        String roleId = "7F2FC891-4A23-11EA-8AF0-0242C0A84607";
        String authTypeId = "B8B785EF-498E-11EA-B2F9-0242C0A84607";
        ResultData resultData = controller.getUnassignedAuthDataList(roleId, authTypeId);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }
}