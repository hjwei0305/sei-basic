package com.changhong.sei.basic.service.datachange;

import com.changhong.sei.basic.entity.AppModule;
import com.changhong.sei.core.dao.datachange.DataHistoryUtil;
import com.changhong.sei.core.datachange.DataChangeProducer;
import com.changhong.sei.core.dto.datachange.DataHistoryRecord;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 实现功能:
 *
 * @author 王锦光 wangjg
 * @version 2020-04-22 9:46
 */
public class DataHistoryUtilTest extends BaseUnitTest {
    @Autowired(required = false)
    private DataChangeProducer producer;

    @Test
    public void generateCreateRecord() {
        AppModule newModule = new AppModule();
        newModule.setCode("0001");
        newModule.setName("测试");
        newModule.setRemark("测试说明");
        DataHistoryRecord record = DataHistoryUtil.generateSaveRecord(JsonUtils.toJson(null), newModule);
        Assert.assertNotNull(record);
        String message = JsonUtils.toJson(record);
        producer.send(message);
        LOG.debug(message);
    }

    @Test
    public void generateUpdateRecord() {
        AppModule originalModule = new AppModule();
        originalModule.setId("123456");
        originalModule.setCode("0001");
        originalModule.setName("测试");
        originalModule.setRemark("测试说明");
        AppModule newModule = new AppModule();
        newModule.setCode("0001");
        newModule.setName("new测试");
        newModule.setRemark("new测试说明");
        DataHistoryRecord record = DataHistoryUtil.generateSaveRecord(JsonUtils.toJson(originalModule), newModule);
        Assert.assertNotNull(record);
        LOG.debug(JsonUtils.toJson(record));
    }

    @Test
    public void generateDeleteRecord() {
        AppModule originalModule = new AppModule();
        originalModule.setId("123456");
        originalModule.setCode("0001");
        originalModule.setName("测试");
        originalModule.setRemark("测试说明");
        DataHistoryRecord record = DataHistoryUtil.generateDeleteRecord(originalModule);
        Assert.assertNotNull(record);
        LOG.debug(JsonUtils.toJson(record));
    }
}