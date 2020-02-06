package com.changhong.sei.basic.service;

import com.changhong.com.sei.core.test.BaseUnitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * 实现功能:
 *
 * @author 王锦光 wangjg
 * @version 2020-02-06 23:10
 */
public class UserServiceTest extends BaseUnitTest {
    @Autowired
    private UserService service;

    @Test
    public void clearUserAuthorizedCaches() {
        String userId = "B54E8964-D14D-11E8-A64B-0242C0A8441B";
        service.clearUserAuthorizedCaches(userId);
    }
}