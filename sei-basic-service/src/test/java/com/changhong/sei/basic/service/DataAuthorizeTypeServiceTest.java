package com.changhong.sei.basic.service;

import com.changhong.sei.basic.entity.DataAuthorizeType;
import com.changhong.sei.core.service.bo.OperateResultWithData;
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
 * @version 2020-03-06 16:44
 */
public class DataAuthorizeTypeServiceTest extends BaseUnitTest {
    @Autowired
    private DataAuthorizeTypeService service;

    @Test
    public void save() {
        DataAuthorizeType dataAuthorizeType = new DataAuthorizeType();
        dataAuthorizeType.setCode("test");
        dataAuthorizeType.setName("测试");
        dataAuthorizeType.setAuthorizeEntityTypeId("6CDA2DD4-4984-11EA-ADFF-0242C0A84607");
        OperateResultWithData<DataAuthorizeType> result = service.save(dataAuthorizeType);
        System.out.println(JsonUtils.toJson(result));
        Assert.assertTrue(result.successful());
    }
}