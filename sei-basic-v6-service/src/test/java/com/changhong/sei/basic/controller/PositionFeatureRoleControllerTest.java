package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.connector.HRMSConnector;
import com.changhong.sei.basic.constant.HRMSConstant;
import com.changhong.sei.basic.dto.OrgDTO;
import com.changhong.sei.basic.entity.EipData;
import com.changhong.sei.basic.entity.Employee;
import com.changhong.sei.basic.entity.Organization;
import com.changhong.sei.basic.entity.Position;
import com.changhong.sei.basic.service.EipDataService;
import com.changhong.sei.basic.service.EmployeeService;
import com.changhong.sei.basic.service.OrganizationService;
import com.changhong.sei.basic.service.PositionService;
import com.changhong.sei.core.dto.ParentRelationParam;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * 实现功能:
 *
 * @author 王锦光 wangjg
 * @version 2020-02-07 23:45
 */
public class PositionFeatureRoleControllerTest extends BaseUnitTest {
    @Autowired
    private PositionFeatureRoleController controller;
    @Autowired
    private EipDataService eipDataService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private EmployeeService employeeService;
    @Test
    public void insertRelationsByParents() {

        // childId为职ID,parentIds为用户ID
        // {childId: "87910181-2287-11ED-A70D-0242AC14000B", parentIds: ["5512EF96-237A-11ED-B087-34C93D8809B5"]}
        ParentRelationParam relationParam = new ParentRelationParam();
        relationParam.setChildId("282648C6-4881-11EA-B817-0242C0A84603");
        relationParam.setParentIds(Arrays.asList("B3677B30-4823-11EA-B817-0242C0A84603"));
        ResultData resultData = controller.insertRelationsByParents(relationParam);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    /**
     * 根据EIP数据来初始化部门负责人
     */
    @Test
    public void initUserPosition() {
        //先处理一次EIP的数据
        List<OrgDTO.DataDTO> hrmsOrgList = HRMSConnector.getOrg();
        HashMap<String, List<String>> deptManagerHashMap = new HashMap<>();
        HashMap<String, List<String>> unitManagerHashMap = new HashMap<>();
        HashMap<String, List<String>> moduleManagerHashMap = new HashMap<>();
        List<EipData> eipDataList = eipDataService.findAll();
        for (EipData data : eipDataList) {
            //替换成HRMS编码
            Optional<OrgDTO.DataDTO> hrmsOrg = hrmsOrgList.stream().filter(c -> c.getAttribute1().equals(data.getDeptCode())).findFirst();
            if (hrmsOrg.isPresent()) {
                //替换成SEI组织编码
                Optional<Organization> optionalOrganization = organizationService.findOrgTreeWithoutFrozen().stream().filter(c -> c.getCode().equals(hrmsOrg.get().getCode())).findFirst();
                if (optionalOrganization.isPresent()) {
                    data.setDeptCode(optionalOrganization.get().getId());
                    //部门负责人
                    if (data.getDeptManager().contains(",")) {
                        List<String> employeeList = new ArrayList<>();
                        String[] deptMangers = data.getDeptManager().split(",");
                        for (String deptManager : deptMangers) {
                            employeeList.add(deptManager.split(" ")[0]);
                        }
                        deptManagerHashMap.put(data.getDeptCode(), employeeList);
                    } else {
                        List<String> employeeList = new ArrayList<>();
                        employeeList.add(data.getDeptManager().split(" ")[0]);
                        deptManagerHashMap.put(data.getDeptCode(), employeeList);
                    }
                    //单位负责人
                    if (data.getUnitManager().contains(",")) {
                        List<String> employeeList = new ArrayList<>();
                        String[] deptMangers = data.getDeptManager().split(",");
                        for (String deptManager : deptMangers) {
                            employeeList.add(deptManager.split(" ")[0]);
                        }
                        unitManagerHashMap.put(data.getDeptCode(), employeeList);
                    } else {
                        List<String> employeeList = new ArrayList<>();
                        employeeList.add(data.getDeptManager().split(" ")[0]);
                        unitManagerHashMap.put(data.getDeptCode(), employeeList);
                    }
                    //模块负责人
                    if (data.getModuleManager().contains(",")) {
                        List<String> employeeList = new ArrayList<>();
                        String[] deptMangers = data.getDeptManager().split(",");
                        for (String deptManager : deptMangers) {
                            employeeList.add(deptManager.split(" ")[0]);
                        }
                        moduleManagerHashMap.put(data.getDeptCode(), employeeList);
                    } else {
                        List<String> employeeList = new ArrayList<>();
                        employeeList.add(data.getDeptManager().split(" ")[0]);
                        moduleManagerHashMap.put(data.getDeptCode(), employeeList);
                    }

                }

            }
            List<Position> positionList = positionService.findAll();
            List<Employee>employeeList=employeeService.findAllUnfrozen();
            for (Map.Entry<String, List<String>> dept : deptManagerHashMap.entrySet()) {
                List<Position> orgPositionList = positionList.stream().filter(c -> c.getOrganizationId().equals(dept.getKey())).collect(Collectors.toList());
                for (Position position : orgPositionList) {
                    if (position.getName().equals("部门负责人")) {
                        List<String>empList=new ArrayList<>();
                        for(String emp: dept.getValue()){
                            Optional<Employee> employee = employeeList.stream().filter(a -> a.getCode().equals(emp)).findFirst();
                            if(employee.isPresent()){
                                empList.add(employee.get().getId());
                            }
                        }
                        if(empList.size()>0){
                            ParentRelationParam relationParam = new ParentRelationParam();
                            relationParam.setChildId(position.getId());
                            relationParam.setParentIds(empList);
                        }
                    }
                }
            }

            for (Map.Entry<String, List<String>> dept : unitManagerHashMap.entrySet()) {
                List<Position> orgPositionList = positionList.stream().filter(c -> c.getOrganizationId().equals(dept.getKey())).collect(Collectors.toList());
                for (Position position : orgPositionList) {
                    if (position.getName().equals("单位负责人")) {
                        List<String>empList=new ArrayList<>();
                        for(String emp: dept.getValue()){
                            Optional<Employee> employee = employeeList.stream().filter(a -> a.getCode().equals(emp)).findFirst();
                            if(employee.isPresent()){
                                empList.add(employee.get().getId());
                            }
                        }
                        if(empList.size()>0){
                            ParentRelationParam relationParam = new ParentRelationParam();
                            relationParam.setChildId(position.getId());
                            relationParam.setParentIds(empList);
                            ResultData resultData = controller.insertRelationsByParents(relationParam);
                        }
                    }
                }
            }

            for (Map.Entry<String, List<String>> dept : moduleManagerHashMap.entrySet()) {
                List<Position> orgPositionList = positionList.stream().filter(c -> c.getOrganizationId().equals(dept.getKey())).collect(Collectors.toList());
                for (Position position : orgPositionList) {
                    if (position.getName().equals("模块负责人")) {
                        List<String>empList=new ArrayList<>();
                        for(String emp: dept.getValue()){
                            Optional<Employee> employee = employeeList.stream().filter(a -> a.getCode().equals(emp)).findFirst();
                            if(employee.isPresent()){
                                empList.add(employee.get().getId());
                            }
                        }
                        if(empList.size()>0){
                            ParentRelationParam relationParam = new ParentRelationParam();
                            relationParam.setChildId(position.getId());
                            relationParam.setParentIds(empList);
                            ResultData resultData = controller.insertRelationsByParents(relationParam);
                        }
                    }
                }
            }

        }
    }

    @Test
    public void getRelationsByParentId() {
        String parentId = "282648C6-4881-11EA-B817-0242C0A84603";
        ResultData resultData = controller.getRelationsByParentId(parentId);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }
}
