package com.changhong.sei.basic.controller;

import com.changhong.com.sei.core.test.BaseUnitTest;
import com.changhong.sei.basic.dto.EmployeeDto;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.util.JsonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 实现功能:
 *
 * @author 王锦光 wangjg
 * @version 2020-01-28 9:26
 */
public class EmployeeControllerTest extends BaseUnitTest {
    @Autowired
    private EmployeeController controller;

    @Test
    public void findByCode() {
        String code = "1100116";
        ResultData resultData = controller.findByCode(code);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void getEmployeeOrgCodes() {
        String userId = "B54E8964-D14D-11E8-A64B-0242C0A8441B";
        ResultData resultData = controller.getEmployeeOrgCodes(userId);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void getEmployeePositionCodes() {
        String userId = "B54E8964-D14D-11E8-A64B-0242C0A8441B";
        ResultData resultData = controller.getEmployeePositionCodes(userId);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void saveTenantAdmin() {
        EmployeeDto dto = new EmployeeDto();
        dto.setCode("20313494");
        dto.setUserName("易成");
        dto.setOrganizationId("322C8C8B-4A79-11EA-B122-0242C0A84607");
        dto.setEmail("93336406@qq.com");
        dto.setTenantCode("20313494");
        ResultData resultData = controller.saveTenantAdmin(dto);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }
}