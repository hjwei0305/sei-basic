package com.changhong.sei.basic.controller;

import com.changhong.sei.core.dto.ParentRelationParam;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * 实现功能:
 *
 * @author 王锦光 wangjg
 * @version 2020-02-07 23:45
 */
public class PositionFeatureRoleControllerTest extends BaseUnitTest {
    @Autowired
    private PositionFeatureRoleController controller;

    @Test
    public void insertRelationsByParents() {

       // childId为职ID,parentIds为用户ID
        // {childId: "87910181-2287-11ED-A70D-0242AC14000B", parentIds: ["5512EF96-237A-11ED-B087-34C93D8809B5"]}
        ParentRelationParam relationParam = new ParentRelationParam();
        relationParam.setChildId("282648C6-4881-11EA-B817-0242C0A84603");
        relationParam.setParentIds(Arrays.asList("B3677B30-4823-11EA-B817-0242C0A84603"));
        ResultData resultData = controller.insertRelationsByParents(relationParam);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void getRelationsByParentId(){
        String parentId="282648C6-4881-11EA-B817-0242C0A84603";
        ResultData resultData = controller.getRelationsByParentId(parentId);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }
}
