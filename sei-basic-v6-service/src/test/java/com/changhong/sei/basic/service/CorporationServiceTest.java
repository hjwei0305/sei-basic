package com.changhong.sei.basic.service;

import com.changhong.sei.basic.dao.CorporationDao;
import com.changhong.sei.basic.entity.Corporation;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 实现功能: 公司业务逻辑单元测试
 */
public class CorporationServiceTest extends BaseUnitTest {
    @Autowired
    private CorporationService service;
    @Autowired
    private CorporationDao dao;

    @Test
    public void findByCode() {
        String code = "10044-Q000";
        Corporation corporation = service.findByCode(code);
        Assert.assertNotNull(corporation);
        LOG.debug(JsonUtils.toJson(corporation));
    }

    @Test
    public void findByProperty() {
        String code = "10044-Q000";
        Corporation corporation = dao.findByProperty("code", code);
        Assert.assertNotNull(corporation);
        LOG.debug(JsonUtils.toJson(corporation));
    }
}