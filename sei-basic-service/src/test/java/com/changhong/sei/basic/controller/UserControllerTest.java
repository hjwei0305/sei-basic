package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.dto.*;
import com.changhong.sei.basic.dto.search.EmployeeQuickQueryParam;
import com.changhong.sei.basic.dto.search.UserQuickQueryParam;
import com.changhong.sei.basic.entity.DataRole;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageInfo;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.enums.UserType;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * 实现功能:
 *
 * @author 王锦光 wangjg
 * @version 2020-02-06 21:06
 */
public class UserControllerTest extends BaseUnitTest {
    @Autowired
    private UserController controller;

    @Test
    public void getUserInformation() {
        String userId = "1F30A429-CDBB-11E8-B852-0242C0A8441B";
        ResultData resultData = controller.getUserInformation(userId);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void getUserAuthorizedFeatureMaps() {
        String userId = "B54E8964-D14D-11E8-A64B-0242C0A8441B";
        ResultData resultData = controller.getUserAuthorizedFeatureMaps(userId);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void getDataRolesByAccount() {
        String account = "12332111";
        ResultData<List<DataRoleDto>> resultData = controller.getDataRolesByAccount(account);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void getUserAuthorizedMenus() {
        String userId = "989C3B6A-6758-11EA-B205-0242C0A8460C";
        ResultData<List<MenuDto>> resultData = controller.getUserAuthorizedMenus(userId);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void queryUsers() {
        UserQuickQueryParam queryParam = new UserQuickQueryParam();
        //queryParam.setUserType(UserType.Employee);
        queryParam.setExcludeFeatureRoleId("1E3816FF-8AB4-11EA-ADD6-0242C0A84603");
        //queryParam.setExcludeDataRoleId("3B5121E6-727E-11EA-9AB3-0242C0A84603");
        queryParam.setQuickSearchValue("测试");
        queryParam.setPageInfo(new PageInfo());
        ResultData<PageResult<UserDto>> resultData = controller.queryUsers(queryParam);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void getFeatureRolesByAccount() {
        String account = "12332111";
        ResultData<List<FeatureRoleDto>> resultData = controller.getFeatureRolesByAccount(account);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }
}