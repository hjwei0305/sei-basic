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
       /* name: "部门负责人"
        organizationCode: "00012057"
        organizationId: "0094D154-1C44-11ED-B27F-005056C00001"
        organizationName: "新宝股份-营运管理中心-信息化管理中心-中心办"
        positionCategoryCode: "DEPTHD"
        positionCategoryId: "825C6ECD-1C5F-11ED-A70D-0242AC14000B"
        positionCategoryName: "部门负责人"*/
       // {"organizationId":"0094D154-1C44-11ED-B27F-005056C00001","organizationCode":"00012057","positionCategoryCode":"DEPTHD","positionCategoryId":"825C6ECD-1C5F-11ED-A70D-0242AC14000B","organizationName":"新宝股份-营运管理中心-信息化管理中心-中心办","positionCategoryName":"部门负责人","name":"部门负责人"}

        //String json = "{\"organizationName\":\"共享服务事业部\",\"positionCategoryName\":\"测试岗\",\"name\":\"123444\",\"organizationId\":\"4227A5ED-5DE5-11EA-B7CF-0242C0A84605\",\"organizationCode\":\"00011\",\"positionCategoryId\":\"8C4B6638-514B-11EA-8018-0242C0A84607\",\"positionCategoryCode\":\"测试岗\"}";
        //PositionDto dto = JsonUtils.fromJson(json, PositionDto.class);



       /* ResultData<List<OrganizationDto>> allOrgs = organizationController.getChildrenNodes4Unfrozen("734FB618-BA26-11EC-9755-0242AC14001A");
        for(OrganizationDto organizationDto :allOrgs.getData()){
           ResultData<List<PositionDto>> byOrganizationId = controller.findByOrganizationId(organizationDto.getId());
           long count=byOrganizationId.getData().stream().filter(a->a.getPositionCategoryName().equals("部门负责人")).count();
           if(count==0){
               PositionDto positionDto=new PositionDto();
               positionDto.setOrganizationName(organizationDto.getName());
               positionDto.setOrganizationCode(organizationDto.getCode());
               positionDto.setOrganizationId(organizationDto.getId());
               positionDto.setPositionCategoryCode("DEPTHD");
               positionDto.setPositionCategoryId("825C6ECD-1C5F-11ED-A70D-0242AC14000B");
               positionDto.setPositionCategoryName("部门负责人");
               positionDto.setName("部门负责人");
               ResultData resultData = controller.save(positionDto);
           }
       }*/

        positionService.initPostion();


    //    System.out.println(JsonUtils.toJson(resultData));
  //      Assert.assertTrue(resultData.successful());
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
