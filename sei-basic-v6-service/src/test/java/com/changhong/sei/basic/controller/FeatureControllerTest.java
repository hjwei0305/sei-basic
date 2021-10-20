package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.dto.FeatureDto;
import com.changhong.sei.basic.dto.FeatureType;
import com.changhong.sei.basic.entity.Feature;
import com.changhong.sei.basic.entity.FeatureGroup;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 实现功能:
 *
 * @author 王锦光 wangjg
 * @version 2020-01-20 8:45
 */
public class FeatureControllerTest extends BaseUnitTest {
    @Autowired
    private FeatureController controller;

    @Test
    public void convertToDto() {
        Feature feature = new Feature();
        feature.setCode("test-001");
        feature.setName("测试功能项转换DTO");
        feature.setFeatureType(FeatureType.Business);
        FeatureGroup group = new FeatureGroup();
        group.setId("123");
        group.setCode("group-001");
        group.setName("功能项组-001");
        feature.setFeatureGroupId("123");
        feature.setFeatureGroup(group);
        FeatureDto featureDto = controller.convertToDto(feature);
        System.out.println(JsonUtils.toJson(featureDto));
    }

    @Test
    public void findByPage() {
        String json = "{\"filters\":[{\"fieldName\":\"canMenu\",\"fieldType\":\"bool\",\"operator\":\"EQ\",\"value\":true}],\"quickSearchValue\":\"\",\"quickSearchProperties\":[\"code\",\"name\"],\"pageInfo\":{\"page\":1,\"rows\":30},\"sortOrders\":[{\"property\":\"appModuleName\",\"direction\":\"ASC\"}]}\n";
        Search search = JsonUtils.fromJson(json, Search.class);
        ResultData<PageResult<FeatureDto>> pageResult = controller.findByPage(search);
        System.out.println(JsonUtils.toJson(pageResult));
        Assert.assertTrue(pageResult.successful());
    }
}