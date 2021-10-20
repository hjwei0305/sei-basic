package com.changhong.sei.basic.controller;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * 实现功能:
 *
 * @author 王锦光 wangjg
 * @version 2020-02-08 16:30
 */
public class PositionDataRoleControllerTest extends BaseUnitTest {
    @Autowired
    private PositionDataRoleController controller;

    @Test
    public void getRelationsByParentId(){
        String parentId="459FB69E-4664-11EA-983E-0242C0A84604";
        ResultData resultData = controller.getRelationsByParentId(parentId);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }
}