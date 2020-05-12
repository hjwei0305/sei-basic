package com.changhong.sei.basic.service;

import com.changhong.sei.basic.entity.Menu;
import com.changhong.sei.core.test.BaseUnitTest;
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
}