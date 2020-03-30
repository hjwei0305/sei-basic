package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.dto.PositionDto;
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
 * @version 2020-03-19 10:34
 */
public class EmployeePositionControllerTest extends BaseUnitTest {
    @Autowired
    private EmployeePositionController controller;

    @Test
    public void getChildrenFromParentId() {
        String parentId = "0C0E05FA-5494-11EA-9A58-0242C0A84605";
        ResultData<List<PositionDto>> resultData = controller.getChildrenFromParentId(parentId);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }
}