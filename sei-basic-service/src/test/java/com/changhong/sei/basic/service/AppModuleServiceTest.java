package com.changhong.sei.basic.service;

import com.changhong.sei.basic.entity.AppModule;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <strong>实现功能:</strong>
 * <p></p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-19 11:20
 */
public class AppModuleServiceTest extends BaseUnitTest {
    @Autowired
    private AppModuleService service;

    @Test
    public void findByCode() {
        String code = "sei-notify";
        AppModule appModule = service.findByCode(code);
        Assert.assertNotNull(appModule);
        System.out.println(JsonUtils.toJson(appModule));
    }

    @Test
    public void save() {
        AppModule appModule = new AppModule();
        appModule.setId("EE358C7C-8788-11EA-9693-48E244F5A3DB");
        appModule.setCode("TEST");
        appModule.setName("测试-new");
        appModule.setRemark("临时创建的测试数据-new");
        appModule.setRank(100);
        OperateResultWithData<AppModule> result = service.save(appModule);
        LOG.debug(JsonUtils.toJson(result));
        Assert.assertTrue(result.successful());
    }

    @Test
    public void delete() {
        String id = "EE358C7C-8788-11EA-9693-48E244F5A3DB";
        OperateResult result = service.delete(id);
        LOG.debug(JsonUtils.toJson(result));
        Assert.assertTrue(result.successful());
    }
}