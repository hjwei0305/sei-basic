package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.connector.HRMSConnector;
import com.changhong.sei.basic.constant.HRMSConstant;
import com.changhong.sei.basic.dao.EmployeeDao;
import com.changhong.sei.basic.dao.UserDao;
import com.changhong.sei.basic.dto.EmployeeBriefInfo;
import com.changhong.sei.basic.dto.EmployeeDto;
import com.changhong.sei.basic.dto.HrmsEmployeeDto;
import com.changhong.sei.basic.dto.search.EmployeeBriefInfoQueryParam;
import com.changhong.sei.basic.dto.search.EmployeeQuickQueryParam;
import com.changhong.sei.basic.entity.Employee;
import com.changhong.sei.basic.entity.Organization;
import com.changhong.sei.basic.entity.User;
import com.changhong.sei.basic.entity.UserProfile;
import com.changhong.sei.basic.service.EmployeeService;
import com.changhong.sei.basic.service.OrganizationService;
import com.changhong.sei.basic.service.UserService;
import com.changhong.sei.basic.service.client.dto.CreateAccountRequest;
import com.changhong.sei.basic.service.client.dto.UpdateAccountRequest;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageInfo;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.SearchOrder;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.enums.UserAuthorityPolicy;
import com.changhong.sei.enums.UserType;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 实现功能:
 *
 * @author 王锦光 wangjg
 * @version 2020-01-28 9:26
 */
public class EmployeeControllerTest extends BaseUnitTest {
    @Autowired
    private EmployeeController controller;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private UserService userService;


    @Test
    public void findByCode() {
        String code = "123";
        ResultData<EmployeeDto> resultData = controller.findByCode(code);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void getEmployeeOrgCodes() {
        String userId = "B54E8964-D14D-11E8-A64B-0242C0A8441B";
        ResultData resultData = controller.getEmployeeOrgCodes(userId);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void getEmployeePositionCodes() {
        String userId = "B54E8964-D14D-11E8-A64B-0242C0A8441B";
        ResultData resultData = controller.getEmployeePositionCodes(userId);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void saveTenantAdmin() {
        EmployeeDto dto = new EmployeeDto();
        dto.setCode("20313494");
        dto.setUserName("易成");
        dto.setOrganizationId("322C8C8B-4A79-11EA-B122-0242C0A84607");
        dto.setEmail("93336406@qq.com");
        dto.setTenantCode("20313494");
        ResultData resultData = controller.saveTenantAdmin(dto);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void save() {
        String json = "{\"organizationName\":\"四川虹信软件股份有限公司\",\"code\":\"testid\",\"userName\":\"test\",\"frozen\":false,\"organizationId\":\"877035BF-A40C-11E7-A8B9-02420B99179E\"}";
        EmployeeDto dto = JsonUtils.fromJson(json, EmployeeDto.class);
        dto.setFrozen(true);
        ResultData resultData = controller.save(dto);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void init() {
        employeeService.initEmployee();
/*        List<Organization> allOrgs = organizationService.getChildrenNodes4Unfrozen("734FB618-BA26-11EC-9755-0242AC14001A");
        List<User> userList = userService.findAllUnfrozen();
        List<Employee> empList = employeeService.findAllUnfrozen();
        List<HrmsEmployeeDto.DataDTO> hrmsEmpList = HRMSConnector.getEmp().stream().filter(c -> c.getIdpath().startsWith(HRMSConstant.IT_IDPATH)).collect(Collectors.toList());
        for (HrmsEmployeeDto.DataDTO emp : hrmsEmpList) {
            Employee entity = new Employee();
            entity.setPassword(DigestUtils.md5Hex("123456"));
            entity.setUserType(UserType.Employee);
            if (Objects.isNull(entity.getUserAuthorityPolicy())) {
                entity.setUserAuthorityPolicy(UserAuthorityPolicy.NormalUser);
            }
            entity.setCode(emp.getEmployeeCode());
            entity.setUserName(emp.getEmployeeName());
            //根据组织编码匹配
            Optional<Organization> organization = allOrgs.stream().filter(c -> c.getCode().equals(emp.getOrgcode())).findFirst();
            if (organization.isPresent()) {
                entity.setOrganizationId(organization.get().getId());
            } else {
                continue;
            }
            entity.setFrozen(false);
            //根据组织编码匹配

            boolean isNew = entity.isNew();
            //检查该租户下员工编号不能重复
            if (employeeDao.isCodeExist(entity.getCode(), entity.getId())) {
                //00040 = 该员工编号【{0}】已存在，请重新输入！
                System.out.println("该员工编号【" + entity.getCode() + "】已存在，请重新输入");
                continue;
            }
            // 检查主账户是否已经存在
            if (isNew) {
                // 先保存user
                Optional<User> user = userList.stream().filter(a -> a.getAccount().equals(entity.getCode())).findFirst();
                if (user.isPresent()) {
                    entity.setId(user.get().getId());
                    try {
                      //  employeeService.saveEmp(entity, true);
                         employeeDao.save(entity, true);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(e.toString());
                    }
                }
                // 保存用户配置信息
                // 保存企业用户

            }
        }*/
    }

    @Test
    public void queryEmployees() {
        EmployeeQuickQueryParam queryParam = new EmployeeQuickQueryParam();
        queryParam.setOrganizationId("877035BF-A40C-11E7-A8B9-02420B99179E");
        queryParam.setIncludeSubNode(true);
        queryParam.setExcludePositionId("459FB69E-4664-11EA-983E-0242C0A84604");
        queryParam.setQuickSearchValue("测试");
        queryParam.setPageInfo(new PageInfo());
        List<SearchOrder> orders = new ArrayList<>();
        orders.add(new SearchOrder("e.code", SearchOrder.Direction.ASC));
        queryParam.setSortOrders(orders);
        ResultData<PageResult<EmployeeDto>> resultData = controller.queryEmployees(queryParam);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void queryEmployeeBriefInfos() {
        EmployeeBriefInfoQueryParam queryParam = new EmployeeBriefInfoQueryParam();
        queryParam.setIncludeFrozen(true);
        queryParam.setQuickSearchValue("王");
        queryParam.setCorporationCode("Q000");
        queryParam.setPageInfo(new PageInfo());
        ResultData<PageResult<EmployeeBriefInfo>> resultData = controller.queryEmployeeBriefInfos(queryParam);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }
}
