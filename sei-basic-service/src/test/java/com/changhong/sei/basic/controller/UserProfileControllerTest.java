package com.changhong.sei.basic.controller;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 实现功能:
 *
 * @author 王锦光 wangjg
 * @version 2020-02-06 14:59
 */
public class UserProfileControllerTest extends BaseUnitTest {
    @Autowired
    private UserProfileController controller;

    @Test
    public void findNotifyInfoByUserIds() {
        List<String> userIds = new ArrayList<>();
        userIds.add("1F30A429-CDBB-11E8-B852-0242C0A8441B");
        userIds.add("B54E8964-D14D-11E8-A64B-0242C0A8441B");
        ResultData resultData = controller.findNotifyInfoByUserIds(userIds);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void findByUserId() {
        String userId = "0C0E05FA-5494-11EA-9A58-0242C0A84605";
        ResultData resultData = controller.findByUserId(userId);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }
}