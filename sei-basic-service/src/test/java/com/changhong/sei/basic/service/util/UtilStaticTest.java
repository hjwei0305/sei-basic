package com.changhong.sei.basic.service.util;

import com.changhong.sei.core.dto.auth.AuthEntityData;
import com.changhong.sei.core.dto.serach.PageInfo;
import com.changhong.sei.core.dto.serach.QueryParam;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.util.DateUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * 实现功能:
 *
 * @author 王锦光 wangjg
 * @version 2020-02-12 10:39
 */
public class UtilStaticTest {

    @Test
    public void setRemoveAll() {
        AuthEntityData data1 = new AuthEntityData();
        data1.setId("15A7029F-A34F-11E7-A967-02420B99179E");
        AuthEntityData data2 = new AuthEntityData();
        data2.setId("2");
        AuthEntityData data3 = new AuthEntityData();
        data3.setId("15A7029F-A34F-11E7-A967-02420B99179E");
        List<AuthEntityData> canAssigned = new ArrayList<>();
        canAssigned.add(data1);
        canAssigned.add(data2);
        List<AuthEntityData> assigned = new ArrayList<>();
        assigned.add(data3);
        Set<AuthEntityData> dataSet = new HashSet<>();
        dataSet.addAll(canAssigned);
        dataSet.removeAll(assigned);
        System.out.println(JsonUtils.toJson(dataSet));
    }

    @Test
    public void dateTest() {
        Date currentDte = DateUtils.getCurrentDate();
        Date startDate = DateUtils.nDaysAfter(-1, DateUtils.parseDate("2020-04-15"));
        Date endDate = DateUtils.nDaysAfter(1, DateUtils.parseDate("2020-04-15"));
        boolean canUse = currentDte.after(startDate) && currentDte.before(endDate);
        Assert.assertTrue(canUse);
    }

    @Test
    public void testQueryParam() {
        QueryParam queryParam = new QueryParam();
        System.out.println(JsonUtils.toJson(queryParam));
        String json = "{\"pageInfo\":{\"page\":1,\"rows\":15}}";
        QueryParam jsonQueryParam = JsonUtils.fromJson(json, QueryParam.class);
        Assert.assertNotNull(jsonQueryParam.getSortOrders());
    }
}