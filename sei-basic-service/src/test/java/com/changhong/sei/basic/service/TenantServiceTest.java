package com.changhong.sei.basic.service;

import com.changhong.sei.core.service.bo.OperateResult;
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
 * @version 2020-03-06 15:50
 */
public class TenantServiceTest extends BaseUnitTest {
    @Autowired
    private TenantService service;

    @Test
    public void delete() {
        String id = "06C5FEC7-5F51-11EA-A2E3-0242C0A84605";
        OperateResult result = service.delete(id);
        System.out.println(JsonUtils.toJson(result));
        Assert.assertTrue(result.successful());
    }
}