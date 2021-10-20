package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.dto.EmployeeBriefInfo;
import com.changhong.sei.basic.dto.EmployeeDto;
import com.changhong.sei.basic.dto.search.EmployeeBriefInfoQueryParam;
import com.changhong.sei.basic.dto.search.EmployeeQuickQueryParam;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageInfo;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.SearchOrder;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

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
        String code = "123";
        ResultData<EmployeeDto> resultData = controller.findByCode(code);
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

    @Test
    public void save() {
        String json = "{\"organizationName\":\"四川虹信软件股份有限公司\",\"code\":\"testid\",\"userName\":\"test\",\"frozen\":false,\"organizationId\":\"877035BF-A40C-11E7-A8B9-02420B99179E\"}";
        EmployeeDto dto = JsonUtils.fromJson(json, EmployeeDto.class);
        dto.setFrozen(true);
        ResultData resultData = controller.save(dto);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void queryEmployees() {
        EmployeeQuickQueryParam queryParam = new EmployeeQuickQueryParam();
        queryParam.setOrganizationId("877035BF-A40C-11E7-A8B9-02420B99179E");
        queryParam.setIncludeSubNode(true);
        queryParam.setExcludePositionId("459FB69E-4664-11EA-983E-0242C0A84604");
        queryParam.setQuickSearchValue("测试");
        queryParam.setPageInfo(new PageInfo());
        List<SearchOrder> orders = new ArrayList<>();
        orders.add(new SearchOrder("e.code", SearchOrder.Direction.ASC));
        queryParam.setSortOrders(orders);
        ResultData<PageResult<EmployeeDto>> resultData = controller.queryEmployees(queryParam);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void queryEmployeeBriefInfos() {
        EmployeeBriefInfoQueryParam queryParam = new EmployeeBriefInfoQueryParam();
        queryParam.setIncludeFrozen(true);
        queryParam.setQuickSearchValue("测试");
        queryParam.setPageInfo(new PageInfo());
        ResultData<PageResult<EmployeeBriefInfo>> resultData = controller.queryEmployeeBriefInfos(queryParam);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }
}