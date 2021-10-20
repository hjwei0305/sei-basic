package com.changhong.sei.basic.service;

import com.changhong.sei.basic.entity.Menu;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.test.BaseUnitTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * 实现功能:
 *
 * @author 王锦光 wangjg
 * @version 2020-05-12 8:39
 */
public class MenuServiceTest extends BaseUnitTest {
    @Autowired
    private MenuService service;

    @Test
    public void findAll() {
        List<Menu> menus = service.findAll();
        LOG.debug("菜单数量：{}", menus.size());
    }

    @Test
    public void move() {
        String nodeId = "80D264D9-8430-11EA-A9CE-0242C0A84406";
        String parentId = "A26B9059-4C7E-11EA-A49D-0242C0A84420";
        OperateResult result = service.move(nodeId, parentId);
        LOG.debug(result.toString());
        Assert.assertTrue(result.successful());
    }
}