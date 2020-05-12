package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.dto.FeatureType;
import com.changhong.sei.basic.dto.MenuDto;
import com.changhong.sei.basic.entity.Feature;
import com.changhong.sei.basic.entity.Menu;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现功能:
 *
 * @author 王锦光 wangjg
 * @version 2020-01-20 9:29
 */
public class MenuControllerTest extends BaseUnitTest {
    @Autowired
    private MenuController controller;

    @Test
    public void convertToDto() {
        Menu menu = new Menu();
        menu.setCode("001");
        menu.setName("一级菜单001");
        menu.setNodeLevel(1);

        List<Menu> children = new ArrayList<>();
        Menu menu1 = new Menu();
        menu1.setCode("001/001");
        menu1.setName("二级菜单001");
        menu1.setNodeLevel(2);
        menu1.setFeatureId("1234");
        Feature feature = new Feature();
        feature.setId("123");
        feature.setCode("test-001");
        feature.setName("测试功能项转换DTO");
        feature.setFeatureType(FeatureType.Business);
        menu1.setFeature(feature);
        children.add(menu1);
        Menu menu2 = new Menu();
        menu2.setCode("001/002");
        menu2.setName("二级菜单002");
        menu2.setNodeLevel(2);
        children.add(menu2);
        menu.setChildren(children);

        MenuDto menuDto = controller.convertToDto(menu);
        System.out.println(JsonUtils.toJson(menuDto));
    }

    @Test
    public void getMenuTree(){
        ResultData<List<MenuDto>> resultData = controller.getMenuTree();
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }
}