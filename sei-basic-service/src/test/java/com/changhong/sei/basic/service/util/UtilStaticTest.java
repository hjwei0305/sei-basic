package com.changhong.sei.basic.service.util;

import com.changhong.sei.core.dto.auth.AuthEntityData;
import com.changhong.sei.core.util.JsonUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
}