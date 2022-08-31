package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.connector.HRMSConnector;
import com.changhong.sei.basic.dao.EmployeeDao;
import com.changhong.sei.basic.dto.OrgDTO;
import com.changhong.sei.basic.dto.PositionDto;
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

import static org.junit.Assert.*;

/**
 * 实现功能:
 *
 * @author 王锦光 wangjg
 * @version 2020-03-19 10:34
 */
public class EmployeePositionControllerTest extends BaseUnitTest {
    @Autowired
    private EmployeePositionController controller;
    @Autowired
    private EipDataService eipDataService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeDao employeeDao;
    @Test
    public void getChildrenFromParentId() {
        String parentId = "0C0E05FA-5494-11EA-9A58-0242C0A84605";
        ResultData<List<PositionDto>> resultData = controller.getChildrenFromParentId(parentId);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }
    @Test
    public void insertRelationsByParents() {
        ParentRelationParam relationParam = new ParentRelationParam();
        //  relationParam.setChildId("7F042912-228F-11ED-A6D6-34C93D8809B5");
        //  relationParam.setParentIds(Arrays.asList("16358528-243B-11ED-8396-34C93D8809B5"));

        relationParam.setChildId("87910181-2287-11ED-A70D-0242AC14000B1");
        relationParam.setParentIds(Arrays.asList("0F90CD6C-2296-11ED-A70D-0242AC14000B"));
        controller.insertRelationsByParents(relationParam);
    }

    @Test
    public void initUserPosition() {
        //先处理一次EIP的数据
        List<OrgDTO.DataDTO> hrmsOrgList = HRMSConnector.getOrg();
        HashMap<String, List<String>> deptManagerHashMap = new HashMap<>();
        HashMap<String, List<String>> unitManagerHashMap = new HashMap<>();
        HashMap<String, List<String>> moduleManagerHashMap = new HashMap<>();
        List<EipData> eipDataList = eipDataService.findAll();
        List<Organization> organizationList = organizationService.findAllUnfrozen();
        List<Position> positionList = positionService.findAll();
        List<Employee> employeeList = employeeDao.findAll();
        for (EipData data : eipDataList) {
            //替换成HRMS编码
            Optional<OrgDTO.DataDTO> hrmsOrg = hrmsOrgList.stream().filter(c -> c.getAttribute1().equals(data.getDeptCode())).findFirst();
            if (hrmsOrg.isPresent()) {
                //替换成SEI组织编码
                Optional<Organization> optionalOrganization = organizationList.stream().filter(c -> c.getCode().equals(hrmsOrg.get().getCode())).findFirst();
                if (optionalOrganization.isPresent()) {
                    data.setDeptCode(optionalOrganization.get().getId());
                    //部门负责人
                    if(data.getDeptManager()!=null){
                        if ( data.getDeptManager().contains(",")) {
                            List<String> employeeAddList = new ArrayList<>();
                            String[] deptMangers = data.getDeptManager().split(",");
                            for (String deptManager : deptMangers) {
                                employeeAddList.add(deptManager.split(" ")[0]);
                            }
                            deptManagerHashMap.put(data.getDeptCode(), employeeAddList);
                        } else {
                            List<String> employeeAddList = new ArrayList<>();
                            employeeAddList.add(data.getDeptManager().split(" ")[0]);
                            deptManagerHashMap.put(data.getDeptCode(), employeeAddList);
                        }
                    }

                    //单位负责人
                    if(data.getUnitManager()!=null) {
                        if (data.getUnitManager().contains(",")) {
                            List<String> employeeAddList = new ArrayList<>();
                            String[] deptMangers = data.getUnitManager().split(",");
                            for (String deptManager : deptMangers) {
                                employeeAddList.add(deptManager.split(" ")[0]);
                            }
                            unitManagerHashMap.put(data.getDeptCode(), employeeAddList);
                        } else {
                            List<String> employeeAddList = new ArrayList<>();
                            employeeAddList.add(data.getUnitManager().split(" ")[0]);
                            unitManagerHashMap.put(data.getDeptCode(), employeeAddList);
                        }
                    }
                    if(data.getModuleManager()!=null) {
                        //模块负责人
                        if ( data.getModuleManager().contains(",")) {
                            List<String> employeeAddList = new ArrayList<>();
                            String[] deptMangers = data.getModuleManager().split(",");
                            for (String deptManager : deptMangers) {
                                employeeAddList.add(deptManager.split(" ")[0]);
                            }
                            moduleManagerHashMap.put(data.getDeptCode(), employeeAddList);
                        } else {
                            List<String> employeeAddList = new ArrayList<>();
                            employeeAddList.add(data.getModuleManager().split(" ")[0]);
                            moduleManagerHashMap.put(data.getDeptCode(), employeeAddList);
                        }
                    }
                }
            }
            for (Map.Entry<String, List<String>> dept : deptManagerHashMap.entrySet()) {
                Optional<Position> positionDept = positionList.stream().filter(c -> c.getOrganizationId().equals(dept.getKey())).filter(n -> n.getName().equals("部门负责人")).findFirst();
                if (positionDept.isPresent()) {
                    List<String> empList = new ArrayList<>();
                    for (String emp : dept.getValue()) {
                        Optional<Employee> employee = employeeList.stream().filter(a -> a.getCode().equals(emp)).findFirst();
                        if (employee.isPresent()) {
                            empList.add(employee.get().getId());
                        }
                    }
                    if (empList.size() > 0) {
                        ParentRelationParam relationParam = new ParentRelationParam();
                        relationParam.setChildId(positionDept.get().getId());
                        relationParam.setParentIds(empList);
                        System.out.println(relationParam.getChildId()+"   "+relationParam.getParentIds());
                        try{
                            ResultData resultData = controller.insertRelationsByParents(relationParam);
                        }catch (Exception e){

                        }
                    }
                }
            }
        }
        for (Map.Entry<String, List<String>> dept : unitManagerHashMap.entrySet()) {
            Optional<Position> positionUnit = positionList.stream().filter(c -> c.getOrganizationId().equals(dept.getKey())).filter(n -> n.getName().equals("单位负责人")).findFirst();
            if (positionUnit.isPresent()) {
                List<String> empList = new ArrayList<>();
                for (String emp : dept.getValue()) {
                    Optional<Employee> employee = employeeList.stream().filter(a -> a.getCode().equals(emp)).findFirst();
                    if (employee.isPresent()) {
                        empList.add(employee.get().getId());
                    }
                }
                if (empList.size() > 0) {
                    ParentRelationParam relationParam = new ParentRelationParam();
                    relationParam.setChildId(positionUnit.get().getId());
                    relationParam.setParentIds(empList);
                    try{
                        ResultData resultData = controller.insertRelationsByParents(relationParam);
                    }catch (Exception e){

                    }

                }
            }
        }


        for (Map.Entry<String, List<String>> dept : moduleManagerHashMap.entrySet()) {
            Optional<Position> positionModule = positionList.stream().filter(c -> c.getOrganizationId().equals(dept.getKey())).filter(n -> n.getName().equals("模块负责人")).findFirst();
            if (positionModule.isPresent()) {
                List<String> empList = new ArrayList<>();
                for (String emp : dept.getValue()) {
                    Optional<Employee> employee = employeeList.stream().filter(a -> a.getCode().equals(emp)).findFirst();
                    if (employee.isPresent()) {
                        empList.add(employee.get().getId());
                    }
                }
                if (empList.size() > 0) {
                    ParentRelationParam relationParam = new ParentRelationParam();
                    relationParam.setChildId(positionModule.get().getId());
                    relationParam.setParentIds(empList);
                    System.out.println(relationParam.getChildId()+"   "+relationParam.getParentIds());
                    try{
                        ResultData resultData = controller.insertRelationsByParents(relationParam);
                    }catch (Exception e){

                    }
                }
            }
        }
    }
}
