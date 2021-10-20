package com.changhong.sei.basic.service;

import com.changhong.sei.basic.dto.FeatureRoleDto;
import com.changhong.sei.basic.entity.FeatureRole;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * 实现功能:
 *
 * @author 王锦光 wangjg
 * @version 2020-04-13 21:02
 */
public class UserFeatureRoleServiceTest extends BaseUnitTest {
    @Autowired
    private UserFeatureRoleService service;

    @Test
    public void getChildrenFromParentId() {
        String parentId = "C1C6F9B8-7480-11EA-8858-0242C0A84603";
        List<FeatureRole> featureRoles = service.getChildrenFromParentId(parentId);
        LOG.debug(JsonUtils.toJson(featureRoles));
    }

    @Test
    public void getEffectiveChildren() {
        String parentId = "C1C6F9B8-7480-11EA-8858-0242C0A84603";
        List<FeatureRole> featureRoles = service.getEffectiveChildren(parentId);
        LOG.debug(JsonUtils.toJson(featureRoles));
    }
}