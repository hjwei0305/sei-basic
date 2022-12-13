package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.dto.OrganizationDto;
import com.changhong.sei.basic.dto.PositionCopyParam;
import com.changhong.sei.basic.dto.PositionDto;
import com.changhong.sei.basic.dto.UserDto;
import com.changhong.sei.basic.dto.search.PositionQuickQueryParam;
import com.changhong.sei.basic.dto.search.UserQuickQueryParam;
import com.changhong.sei.basic.service.PositionService;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageInfo;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.enums.UserType;
import javassist.bytecode.stackmap.BasicBlock;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * 实现功能:
 *
 * @author 王锦光 wangjg
 * @version 2020-02-03 17:11
 */
public class PositionControllerTest extends BaseUnitTest {
    @Autowired
    private PositionController controller;
    @Autowired
    private OrganizationController organizationController;
@Autowired PositionService positionService;
    @Test
    public void findByOrganizationId() {
        String orgId = "C34F75A2-4703-11EA-911F-0242C0A84604";
        ResultData resultData = controller.findByOrganizationId(orgId);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void copyToOrgNodes() {
        PositionCopyParam copyParam = new PositionCopyParam();
        copyParam.setPositionId("946F71E0-514B-11EA-8018-0242C0A84607");
        copyParam.setTargetOrgIds(Arrays.asList("4227A5ED-5DE5-11EA-B7CF-0242C0A84605"));
        copyParam.setCopyFeatureRole(true);
        ResultData resultData = controller.copyToOrgNodes(copyParam);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void save() {

        positionService.initPostion();
    }

    @Test
    public void queryPositions() {
        PositionQuickQueryParam queryParam = new PositionQuickQueryParam();
        queryParam.setOrganizationId("877035BF-A40C-11E7-A8B9-02420B99179E");
        queryParam.setIncludeSubNode(true);
        queryParam.setExcludeFeatureRoleId("49AB6A05-7272-11EA-9AB3-0242C0A84603");
        queryParam.setExcludeDataRoleId("3B5121E6-727E-11EA-9AB3-0242C0A84603");
        queryParam.setQuickSearchValue("测试");
        queryParam.setPageInfo(new PageInfo());
        ResultData<PageResult<PositionDto>> resultData = controller.queryPositions(queryParam);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }
}
