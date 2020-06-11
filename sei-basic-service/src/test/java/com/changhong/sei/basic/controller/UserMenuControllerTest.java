package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.dto.MenuDto;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * 实现功能: 用户收藏的菜单单元测试
 *
 * @author 王锦光 wangjg
 * @version 2020-06-11 9:36
 */
public class UserMenuControllerTest extends BaseUnitTest {
    @Autowired
    private UserMenuController controller;

    @Test
    public void getChildrenFromParentId() {
        String userId = "989C3B6A-6758-11EA-B205-0242C0A8460C";
        ResultData<List<MenuDto>> resultData = controller.getChildrenFromParentId(userId);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void insertMenu() {
        String menuId = "A3283815-49BD-11EA-B2F9-0242C0A84607";
        ResultData<?> resultData = controller.insertMenu(menuId);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void removeMenu() {
        String menuId = "A3283815-49BD-11EA-B2F9-0242C0A84607";
        ResultData<?> resultData = controller.removeMenu(menuId);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void getFavoriteMenus() {
        ResultData<List<MenuDto>> resultData = controller.getFavoriteMenus();
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }
}