package com.changhong.sei.basic.service;

import com.changhong.sei.core.test.BaseUnitTest;
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
        String userId = "0C0E05FA-5494-11EA-9A58-0242C0A84605";
        service.clearUserAuthorizedCaches(userId);
    }
}