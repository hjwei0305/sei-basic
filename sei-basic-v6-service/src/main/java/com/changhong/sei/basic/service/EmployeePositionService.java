package com.changhong.sei.basic.service;

import com.changhong.sei.basic.connector.HRMSConnector;
import com.changhong.sei.basic.controller.EmployeePositionController;
import com.changhong.sei.basic.dao.EmployeeDao;
import com.changhong.sei.basic.dao.EmployeePositionDao;
import com.changhong.sei.basic.dto.EmployeeDto;
import com.changhong.sei.basic.dto.OrgDTO;
import com.changhong.sei.basic.entity.*;
import com.changhong.sei.basic.service.util.AuthorityUtil;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseRelationDao;
import com.changhong.sei.core.dto.ParentRelationParam;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.service.BaseRelationService;
import com.changhong.sei.core.service.bo.OperateResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：企业员工分配岗位的业务逻辑实现
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017/5/11 20:29      秦有宝                     新建
 * <p/>
 * *************************************************************************************************
 */
@Service
public class EmployeePositionService extends BaseRelationService<EmployeePosition, Employee, Position> {
    @Autowired
    private EmployeePositionDao dao;

    @Override
    protected BaseRelationDao<EmployeePosition, Employee, Position> getDao() {
        return dao;
    }

    @Autowired
    private PositionService positionService;
    @Autowired
    private EmployeePositionController controller;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private EssEmployeePositionService essService;

    /**
     * 获取可以分配的子实体清单
     *
     * @return 子实体清单
     */
    @Override
    protected List<Position> getCanAssignedChildren(String parentId) {
        return positionService.findAll();
    }

    /**
     * 创建分配关系
     *
     * @param parentId 父实体Id
     * @param childIds 子实体Id清单
     * @return 操作结果
     */
    @Override
    public OperateResult insertRelations(String parentId, List<String> childIds) {
        if (parentId.equals(ContextUtil.getUserId())) {
            //00027 = 不能给当前用户分配岗位！
            return OperateResult.operationFailure("00027");
        }
        OperateResult result = super.insertRelations(parentId, childIds);
        // 清除用户权限缓存
        AuthorityUtil.cleanUserAuthorizedCaches(parentId);
        return result;
    }

    /**
     * 移除分配关系
     *
     * @param parentId 父实体Id
     * @param childIds 子实体Id清单
     * @return 操作结果
     */
    @Override
    public OperateResult removeRelations(String parentId, List<String> childIds) {
        if (parentId.equals(ContextUtil.getUserId())) {
            //00028 = 不能移除当前用户的岗位！
            return OperateResult.operationFailure("00028");
        }
        OperateResult result = super.removeRelations(parentId, childIds);
        // 清除用户权限缓存
        AuthorityUtil.cleanUserAuthorizedCaches(parentId);
        return result;
    }

    /**
     * 通过父实体清单创建分配关系
     *
     * @param childId   子实体Id
     * @param parentIds 父实体Id清单
     * @return 操作结果
     */
    @Override
    public OperateResult insertRelationsByParents(String childId, List<String> parentIds) {
        for (String employeeId : parentIds) {
            if (employeeId.equals(ContextUtil.getUserId())) {
                //00029 = 不能为岗位分配当前用户！
                return OperateResult.operationFailure("00029");
            }
        }
        OperateResult result = super.insertRelationsByParents(childId, parentIds);
        parentIds.forEach(userId -> {
            // 清除用户权限缓存
            AuthorityUtil.cleanUserAuthorizedCaches(userId);
        });
        return result;
    }

    /**
     * 通过父实体清单移除分配关系
     *
     * @param childId   子实体Id
     * @param parentIds 父实体Id清单
     * @return 操作结果
     */
    @Override
    public OperateResult removeRelationsByParents(String childId, List<String> parentIds) {
        for (String employeeId : parentIds) {
            if (employeeId.equals(ContextUtil.getUserId())) {
                //00030 = 不能给岗位移除当前用户！
                return OperateResult.operationFailure("00030");
            }
        }
        OperateResult result = super.removeRelationsByParents(childId, parentIds);
        parentIds.forEach(userId -> {
            // 清除用户权限缓存
            AuthorityUtil.cleanUserAuthorizedCaches(userId);
        });
        return result;
    }

    /**
     * 初始化ESS角色
     */
    public void initEssPosition() {
        List<OrgDTO.DataDTO> hrmsOrgList = HRMSConnector.getOrg();
        HashMap<String, List<String>> treasurerHashMap = new HashMap<>();
        HashMap<String, List<String>> assetManagerHashMap = new HashMap<>();
        HashMap<String, List<String>> moldManagerHashMap = new HashMap<>();
        List<EssEmployeePosition> eipDataList = essService.findAll();
        List<Organization> organizationList = organizationService.findAllUnfrozen();
        List<Position> positionList = positionService.findAll();
        List<Employee> employeeList = employeeDao.findAll();
        for (EssEmployeePosition data : eipDataList) {
            //替换成HRMS编码
            Optional<OrgDTO.DataDTO> hrmsOrg = hrmsOrgList.stream().filter(c -> c.getAttribute1().equals(data.getDeptCode())).findFirst();
            if (hrmsOrg.isPresent()) {
                //替换成SEI组织编码
                Optional<Organization> optionalOrganization = organizationList.stream().filter(c -> c.getCode().equals(hrmsOrg.get().getCode())).findFirst();
                if (optionalOrganization.isPresent()) {
                    data.setDeptCode(optionalOrganization.get().getId());
                    //财务主管
                    if (data.getTreasurer() != null) {
                        if (data.getTreasurer().contains(",")) {
                            List<String> employeeAddList = new ArrayList<>();
                            String[] deptMangers = data.getTreasurer().split(",");
                            for (String deptManager : deptMangers) {
                                employeeAddList.add(deptManager.split(" ")[0]);
                            }
                            treasurerHashMap.put(data.getDeptCode(), employeeAddList);
                        } else {
                            List<String> employeeAddList = new ArrayList<>();
                            employeeAddList.add(data.getTreasurer());
                            treasurerHashMap.put(data.getDeptCode(), employeeAddList);
                        }
                    }

                    //资产管理员
                    if (data.getAssetManager() != null) {
                        if (data.getAssetManager().contains(",")) {
                            List<String> employeeAddList = new ArrayList<>();
                            assetManagerHashMap.put(data.getDeptCode(), employeeAddList);
                        } else {
                            List<String> employeeAddList = new ArrayList<>();
                            employeeAddList.add(data.getAssetManager().split(" ")[0]);
                            assetManagerHashMap.put(data.getDeptCode(), employeeAddList);
                        }
                    }
                    if (data.getMoldManager() != null) {
                        //模具管理员
                        if (data.getMoldManager().contains(",")) {
                            List<String> employeeAddList = new ArrayList<>();
                            String[] deptMangers = data.getMoldManager().split(",");
                            for (String deptManager : deptMangers) {
                                employeeAddList.add(deptManager.split(" ")[0]);
                            }
                            moldManagerHashMap.put(data.getDeptCode(), employeeAddList);
                        } else {
                            List<String> employeeAddList = new ArrayList<>();
                            employeeAddList.add(data.getMoldManager().split(" ")[0]);
                            moldManagerHashMap.put(data.getDeptCode(), employeeAddList);
                        }
                    }
                }
            }
        }

        for (Map.Entry<String, List<String>> dept : treasurerHashMap.entrySet()) {
            Optional<Position> positionDept = positionList.stream().filter(c -> c.getOrganizationId().equals(dept.getKey())).filter(n -> n.getName().equals("财务主管")).findFirst();
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
                    try {
                        insertRelationsByParents(relationParam.getChildId(), relationParam.getParentIds());
                      //  ResultData resultData = controller.insertRelationsByParents(relationParam);
                    } catch (Exception e) {

                    }
                }
            }
        }

        for (Map.Entry<String, List<String>> dept : assetManagerHashMap.entrySet()) {
            Optional<Position> positionUnit = positionList.stream().filter(c -> c.getOrganizationId().equals(dept.getKey())).filter(n -> n.getName().equals("资产管理员")).findFirst();
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
                    try {
                        insertRelationsByParents(relationParam.getChildId(), relationParam.getParentIds());
                    } catch (Exception e) {

                    }

                }
            }
        }


        for (Map.Entry<String, List<String>> dept : moldManagerHashMap.entrySet()) {
            Optional<Position> positionModule = positionList.stream().filter(c -> c.getOrganizationId().equals(dept.getKey())).filter(n -> n.getName().equals("模具负责人")).findFirst();
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
                    try {
                        insertRelationsByParents(relationParam.getChildId(), relationParam.getParentIds());
                    } catch (Exception e) {

                    }
                }
            }
        }
    }
    /**
     * 初始化人员岗位
     */
    public void initUserPosition() {
        //先处理一次EIP的数据
        List<OrgDTO.DataDTO> hrmsOrgList = HRMSConnector.getOrg();
        // List<OrgDTO.DataDTO> hrmsOrgList = HRMSConnector.getOrg().stream().filter(a->a.getIdpath().startsWith("1,265,7602,")).collect(Collectors.toList());
        HashMap<String, List<String>> deptManagerHashMap = new HashMap<>();
        HashMap<String, List<String>> unitManagerHashMap = new HashMap<>();
        HashMap<String, List<String>> moduleManagerHashMap = new HashMap<>();
        List<Organization> organizationList = organizationService.findAllUnfrozen();
        List<Position> positionList = positionService.findAll();
        List<ParentRelationParam> parentRelationParamList = new ArrayList<>();
        //找出所有涉及的人员
        //   List<Employee> employeeList = employeeDao.findAll();
        for (OrgDTO.DataDTO data : hrmsOrgList) {
            //过滤不用设置的组织
            if (StringUtils.isBlank(data.getDeptManager()) && StringUtils.isBlank(data.getUnitManager()) && StringUtils.isBlank(data.getUnitManager())) {
                continue;
            }
            Optional<Organization> optionalOrganization = organizationList.stream().filter(c -> c.getCode().equals(data.getCode())).findFirst();
            if (optionalOrganization.isPresent()) {
                //部门负责人
                if (data.getDeptManager() != null) {
                    if (data.getDeptManager().contains(",")) {
                        List<String> employeeAddList = new ArrayList<>();
                        String[] deptMangers = data.getDeptManager().split(",");
                        for (String deptManager : deptMangers) {
                            employeeAddList.add(deptManager.split(" ")[0]);
                        }
                        deptManagerHashMap.put(optionalOrganization.get().getId(), employeeAddList);
                    } else {
                        List<String> employeeAddList = new ArrayList<>();
                        employeeAddList.add(data.getDeptManager().split(" ")[0]);
                        deptManagerHashMap.put(optionalOrganization.get().getId(), employeeAddList);
                    }
                }
                //单位负责人
                if (data.getUnitManager() != null) {
                    if (data.getUnitManager().contains(",")) {
                        List<String> employeeAddList = new ArrayList<>();
                        String[] deptMangers = data.getUnitManager().split(",");
                        for (String deptManager : deptMangers) {
                            employeeAddList.add(deptManager.split(" ")[0]);
                        }
                        unitManagerHashMap.put(optionalOrganization.get().getId(), employeeAddList);
                    } else {
                        List<String> employeeAddList = new ArrayList<>();
                        employeeAddList.add(data.getUnitManager().split(" ")[0]);
                        unitManagerHashMap.put(optionalOrganization.get().getId(), employeeAddList);
                    }
                }
                if (data.getModuleManager() != null) {
                    //模块负责人
                    if (data.getModuleManager().contains(",")) {
                        List<String> employeeAddList = new ArrayList<>();
                        String[] deptMangers = data.getModuleManager().split(",");
                        for (String deptManager : deptMangers) {
                            employeeAddList.add(deptManager.split(" ")[0]);
                        }
                        moduleManagerHashMap.put(optionalOrganization.get().getId(), employeeAddList);
                    } else {
                        List<String> employeeAddList = new ArrayList<>();
                        employeeAddList.add(data.getModuleManager().split(" ")[0]);
                        moduleManagerHashMap.put(optionalOrganization.get().getId(), employeeAddList);
                    }
                }
            }
        }
        for (Map.Entry<String, List<String>> dept : deptManagerHashMap.entrySet()) {
            Optional<Position> positionDept = positionList.stream().filter(c -> c.getOrganizationId().equals(dept.getKey())).filter(n -> n.getName().equals("部门负责人")).findFirst();
            if (positionDept.isPresent()) {
                List<String> empList = new ArrayList<>();
                for (String emp : dept.getValue()) {
                    Optional<Employee> employee = Optional.ofNullable(employeeDao.findByCodeAndTenantCode(emp, "DONLIM"));
                    if (employee.isPresent()) {
                        empList.add(employee.get().getId());
                    }
                }
                if (empList.size() > 0) {
                    ParentRelationParam relationParam = new ParentRelationParam();
                    relationParam.setChildId(positionDept.get().getId());
                    relationParam.setParentIds(empList);
                    parentRelationParamList.add(relationParam);
                }
            }
        }
        for (Map.Entry<String, List<String>> dept : unitManagerHashMap.entrySet()) {
            Optional<Position> positionUnit = positionList.stream().filter(c -> c.getOrganizationId().equals(dept.getKey())).filter(n -> n.getName().equals("单位负责人")).findFirst();
            if (positionUnit.isPresent()) {
                List<String> empList = new ArrayList<>();
                for (String emp : dept.getValue()) {
                    Optional<Employee> employee = Optional.ofNullable(employeeDao.findByCodeAndTenantCode(emp, "DONLIM"));
                    if (employee.isPresent()) {
                        empList.add(employee.get().getId());
                    }
                }
                if (empList.size() > 0) {
                    ParentRelationParam relationParam = new ParentRelationParam();
                    relationParam.setChildId(positionUnit.get().getId());
                    relationParam.setParentIds(empList);
                    parentRelationParamList.add(relationParam);
                }
            }
        }
        for (Map.Entry<String, List<String>> dept : moduleManagerHashMap.entrySet()) {
            Optional<Position> positionModule = positionList.stream().filter(c -> c.getOrganizationId().equals(dept.getKey())).filter(n -> n.getName().equals("模块负责人")).findFirst();
            if (positionModule.isPresent()) {
                List<String> empList = new ArrayList<>();
                for (String emp : dept.getValue()) {
                    Optional<Employee> employee = Optional.ofNullable(employeeDao.findByCodeAndTenantCode(emp, "DONLIM"));
                    if (employee.isPresent()) {
                        empList.add(employee.get().getId());
                    }
                }
                if (empList.size() > 0) {
                    ParentRelationParam relationParam = new ParentRelationParam();
                    relationParam.setChildId(positionModule.get().getId());
                    relationParam.setParentIds(empList);
                    parentRelationParamList.add(relationParam);
                }
            }
        }
        for (ParentRelationParam parentRelationParam : parentRelationParamList) {
            try {
                List<Employee> existEmployeeList = getParentsFromChildId(parentRelationParam.getChildId());
                if(existEmployeeList.size()>0){
                    List<String>newEmpList=parentRelationParam.getParentIds();
                    List<String>removeEmpList=new ArrayList<>();
                    for (Employee existEmployee : existEmployeeList) {
                       if(newEmpList.contains(existEmployee.getId())) {
                           //已经存在的先排除,不用更新
                           newEmpList.remove(existEmployee.getId());
                       }else {
                           //不存在的,需要移除
                           EmployeePosition employeePosition = getRelation(existEmployee.getId(), parentRelationParam.getChildId());
                           //如果不是admin创建的,则不移除
                           if (employeePosition != null && "admin".equals(employeePosition.getCreatorAccount())) {
                               removeEmpList.add(existEmployee.getId());
                           }
                       }
                    }
                    //执行移除
                    if(removeEmpList.size()>0){
                        removeRelationsByParents(parentRelationParam.getChildId(),removeEmpList);
                    }
                    //添加
                    if(newEmpList.size()>0){
                        insertRelationsByParents(parentRelationParam.getChildId(),newEmpList);
                    }
                }else{
                    insertRelationsByParents(parentRelationParam.getChildId(),parentRelationParam.getParentIds());
                }
            } catch (Exception e) {
                LogUtil.error("更新组织架构负责人失败{}", e.getMessage());
            }
        }
    }
}
