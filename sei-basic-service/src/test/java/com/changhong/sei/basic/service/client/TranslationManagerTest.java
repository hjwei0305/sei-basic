package com.changhong.sei.basic.service.client;

import com.changhong.sei.commondata.sdk.client.TranslationManager;
import com.changhong.sei.commondata.sdk.client.dto.TranslateQuery;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 实现功能: 翻译测试
 *
 * @author 王锦光 wangjg
 * @version 2020-03-26 10:05
 */
public class TranslationManagerTest extends BaseUnitTest {
    @Autowired
    private TranslationManager manager;

    @Test
    public void getTransResult() {
        List<String> queries = new LinkedList<>();
        queries.add("基础应用");
        queries.add("权限管理");
        queries.add("功能角色");
        queries.add("数据角色");
        TranslateQuery query = new TranslateQuery();
        query.setTo("en");
        query.setQueries(queries);
        Map<String, String> resultData = manager.getTransResult(query);
        System.out.println(JsonUtils.toJson(resultData));
    }
}
