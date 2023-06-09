package com.changhong.sei.basic.controller;

import com.changhong.sei.apitemplate.ApiTemplate;
import com.changhong.sei.basic.connector.HRMSConnector;
import com.changhong.sei.basic.dto.OrgDTO;
import com.changhong.sei.basic.dto.OrganizationDto;
import com.changhong.sei.basic.entity.Organization;
import com.changhong.sei.basic.service.OrganizationService;
import com.changhong.sei.basic.service.client.SerialGenerator;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.auth.AuthTreeEntityData;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.serial.sdk.SerialService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

/**
 * 实现功能:
 *
 * @author 王锦光 wangjg
 * @version 2020-02-11 13:56
 */
public class OrganizationControllerTest extends BaseUnitTest {
    @Autowired
    private OrganizationController controller;
    @Autowired
    private ApiTemplate apiTemplate;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private SerialGenerator serialGenerator;
    @Autowired
    private SerialService serialService;
    @Test
    //用SerialGenerator生成序列号
    public void testSerialGenerator(){
        String serialNumber = serialService.getNumber(Organization.class);
        System.out.println(serialNumber);
    }
    @Test
    public void findAllAuthTreeEntityData() {
        ResultData resultData = controller.findAllAuthTreeEntityData();
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void findAllAuthTreeEntityDataViaApi() {
        String path = "organization/findAllAuthTreeEntityData";
        ParameterizedTypeReference<ResultData<List<AuthTreeEntityData>>> typeReference = new ParameterizedTypeReference<ResultData<List<AuthTreeEntityData>>>() {};
        ResultData<List<AuthTreeEntityData>> resultData = apiTemplate.getByAppModuleCode("sei-basic", path, typeReference);
        //List<AuthTreeEntityData> data = JsonUtils.fromJson2List(JsonUtils.toJson(resultData.getData()), AuthTreeEntityData.class);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void save() {
        String code = "10926";
        ResultData<OrganizationDto> findResult = controller.findByCode(code);
        Assert.assertTrue(findResult.successful());
        OrganizationDto dto = findResult.getData();
        dto.setName("阿斯顿撒-new");
        dto.setFrozen(false);
        ResultData<?> resultData = controller.save(dto);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void getUserAuthorizedTreeNodeCodes() {
        String featureCode = null;
        ResultData<List<String>> resultData = controller.getUserAuthorizedTreeNodeCodes(featureCode);
        LOG.debug(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void getOrgAuthTreeByCorp() {
        String featureCode = null;
        ResultData<List<OrganizationDto>> resultData = controller.getOrgAuthTreeByCorp("Q600",featureCode);
        LOG.debug(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public  void getHrmsOrg(){
        organizationService.synOrg();



    }

}
