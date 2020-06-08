package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.dto.DataRoleRelation;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.auth.AuthEntityData;
import com.changhong.sei.core.dto.auth.AuthTreeEntityData;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        String roleId = "72FA053A-4A23-11EA-8AF0-0242C0A84607";
        String authTypeId = "B8B785EF-498E-11EA-B2F9-0242C0A84607";
        ResultData<List<AuthEntityData>> resultData = controller.getUnassignedAuthDataList(roleId, authTypeId);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void insertRelations() {
        DataRoleRelation relation = new DataRoleRelation();
        relation.setDataAuthorizeTypeId("B8B785EF-498E-11EA-B2F9-0242C0A84607");
        relation.setDataRoleId("72FA053A-4A23-11EA-8AF0-0242C0A84607");
        List<String> entityIds = new ArrayList<>(Arrays.asList("15A7029F-A34F-11E7-A967-02420B99179E"));
        relation.setEntityIds(entityIds);
        ResultData resultData = controller.insertRelations(relation);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void getUnassignedAuthTreeDataList() {
        String roleId = "0AF65EDD-6734-11EA-8764-0242C0A8460C";
        String authTypeId = "02808BCB-4A27-11EA-8AF0-0242C0A84607";
        ResultData<List<AuthTreeEntityData>> resultData = controller.getUnassignedAuthTreeDataList(roleId, authTypeId);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void getAuthorizeTypesByRoleId() {
        String roleId = "1A7A84F3-54A7-11EA-9A58-0242C0A84605";
        ResultData resultData = controller.getAuthorizeTypesByRoleId(roleId);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void getAssignedAuthTreeDataList() {
        String roleId = "0AF65EDD-6734-11EA-8764-0242C0A8460C";
        String authTypeId = "02808BCB-4A27-11EA-8AF0-0242C0A84607";
        ResultData<List<AuthTreeEntityData>> resultData = controller.getAssignedAuthTreeDataList(roleId, authTypeId);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }
}