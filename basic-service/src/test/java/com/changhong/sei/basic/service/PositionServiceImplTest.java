package com.changhong.sei.basic.service;

import com.changhong.com.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.util.JsonUtils;
import io.swagger.annotations.ApiModel;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * 实现功能:
 *
 * @author 王锦光 wangjg
 * @version 2020-02-03 17:11
 */
public class PositionServiceImplTest extends BaseUnitTest {
    @Autowired
    private PositionServiceImpl service;

    @Test
    public void findByOrganizationId() {
        String orgId = "877035BF-A40C-11E7-A8B9-02420B99179E";
        ResultData resultData = service.findByOrganizationId(orgId);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.isSuccessful());
    }
}