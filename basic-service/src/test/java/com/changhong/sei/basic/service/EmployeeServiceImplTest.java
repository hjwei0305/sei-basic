package com.changhong.sei.basic.service;

import com.changhong.com.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.core.utils.ResultDataUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * 实现功能:
 *
 * @author 王锦光 wangjg
 * @version 2020-01-28 9:26
 */
public class EmployeeServiceImplTest extends BaseUnitTest {
    @Autowired
    private EmployeeServiceImpl service;

    @Test
    public void findByCode() {
        String code = "11006086";
        ResultData resultData = service.findByCode(code);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.isSuccessful());
    }
}