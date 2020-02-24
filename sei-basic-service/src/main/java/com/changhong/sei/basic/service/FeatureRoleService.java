package com.changhong.sei.basic.service;

import com.changhong.sei.basic.dao.FeatureRoleDao;
import com.changhong.sei.basic.dto.RoleType;
import com.changhong.sei.basic.entity.*;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.context.SessionUser;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.enums.UserAuthorityPolicy;
import com.changhong.sei.enums.UserType;
import com.changhong.sei.util.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：功能角色业务逻辑实现
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017-05-04 16:35      王锦光(wangj)                新建
 * <p/>
 * *************************************************************************************************
 */
@Service
public class FeatureRoleService extends BaseEntityService<FeatureRole> {

    @Autowired
    private FeatureRoleDao dao;
    @Override
    protected BaseEntityDao<FeatureRole> getDao() {
        return dao;
    }
    @Autowired
    private UserFeatureRoleService userFeatureRoleService;
    @Autowired
    private PositionFeatureRoleService positionFeatureRoleService;
    @Autowired
    private FeatureRoleFeatureService featureRoleFeatureService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private OrganizationService organizationService;

    /**
     * 通过角色组Id获取角色清单
     *
     * @param roleGroupId 角色组Id
     * @return 角色清单
     */
    public List<FeatureRole> findByFeatureRoleGroup(String roleGroupId) {
        SearchFilter filter = new SearchFilter("featureRoleGroup.id",roleGroupId, SearchFilter.Operator.EQ);
        return findByFilter(filter);
    }

    /**
     * 删除数据保存数据之前额外操作回调方法 子类根据需要覆写添加逻辑即可
     *
     * @param s 待删除数据对象主键
     */
    @Override
    protected OperateResult preDelete(String s) {
        if (featureRoleFeatureService.isExistByParent(s)) {
            //功能角色存在已经分配的功能项，禁止删除！
            return OperateResult.operationFailure("00009");
        }
        List list = userFeatureRoleService.getParentsFromChildId(s);
        if (list != null && list.size() > 0) {
            //功能角色存在已经分配的功能项，禁止删除！
            return OperateResult.operationFailure("00009");
        }
        list = positionFeatureRoleService.getParentsFromChildId(s);
        if (list != null && list.size() > 0) {
            //功能角色存在已经分配的功能项，禁止删除！
            return OperateResult.operationFailure("00009");
        }
        return super.preDelete(s);
    }

    /**
     * 根据功能角色的id获取已分配的用户
     *
     * @param featureRoleId 功能角色的id
     * @return 员工用户清单
     */
    public List<User> getAssignedEmployeesByFeatureRole(String featureRoleId) {
        return userFeatureRoleService.getParentsFromChildId(featureRoleId);
    }

    /**
     * 根据功能角色的id获取已分配的岗位
     *
     * @param featureRoleId 功能角色的id
     * @return 岗位清单
     */
    public List<Position> getAssignedPositionsByFeatureRole(String featureRoleId) {
        return positionFeatureRoleService.getParentsFromChildId(featureRoleId);
    }

    /**
     * 根据代码查询实体
     *
     * @param code 代码
     * @return 实体
     */
    public FeatureRole findByCode(String code) {
        if (StringUtils.isNotBlank(code)) {
            return super.findByProperty(FeatureRole.CODE_FIELD, code);
        }
        return null;
    }

    /**
     * 获取用户的公共功能角色
     * @param user 用户
     * @return 公共功能角色清单
     */
    List<FeatureRole> getPublicFeatureRoles(User user){
        List<FeatureRole> result = new ArrayList<>();
        //获取用户类型匹配的全局公共角色
        List<FeatureRole> publicRoles = dao.getPublicRoles(user);
        result.addAll(publicRoles);
        //获取用户的组织机构
        if (user.getUserType()== UserType.Employee){
            Employee employee = employeeService.findOne(user.getId());
            if (employee!=null){
                //获取企业用户的组织机构
                List<Organization> orgs = organizationService.getParentNodes(employee.getOrganization().getId(),true);
                List<FeatureRole> orgPubRoles = dao.getPublicRoles(user,orgs);
                result.addAll(orgPubRoles);
            }
        }
        return result;
    }

    /**
     * 获取用户本人创建的角色
     * @return 角色清单
     */
    List<FeatureRole> findByCreator(){
        // 判断用户权限
        SessionUser sessionUser = ContextUtil.getSessionUser();
        if (sessionUser.isAnonymous()){
            return new ArrayList<>();
        }
        // 如果是租户管理员，返回所有
        if (sessionUser.getAuthorityPolicy() == UserAuthorityPolicy.TenantAdmin){
            return findAll();
        }
        // 如果是一般分级授权用户，返回本人创建的角色
        return dao.findByCreatorAccountAndTenantCode(sessionUser.getAccount(), sessionUser.getTenantCode());
    }

    /**
     * 获取用户本人可以分配的角色
     * @param roleGroupId 角色组Id
     * @return 可以分配的角色
     */
    public List<FeatureRole> getCanAssignedRoles(String roleGroupId){
        // 判断用户权限
        SessionUser sessionUser = ContextUtil.getSessionUser();
        if (sessionUser.isAnonymous()){
            return new ArrayList<>();
        }
        // 如果是租户管理员，返回所有
        if (sessionUser.getAuthorityPolicy() == UserAuthorityPolicy.TenantAdmin){
            return findByFeatureRoleGroup(roleGroupId);
        }
        // 如果是一般分级授权用户，返回本人创建的角色
        return dao.findByCreator(roleGroupId, sessionUser.getAccount(), sessionUser.getTenantCode());
    }

    /**
     * 获取角色类型
     * @return 用户角色类型列表
     */
    public OperateResultWithData getRoleTypeList() {
        return OperateResultWithData.operationSuccessWithData(EnumUtils.getEnumDataList(RoleType.class), "ecmp_context_00001");
    }

    /**
     * 获取用户类型
     * @return 用户类型列表
     */
    public OperateResultWithData listAllUserType() {
        return OperateResultWithData.operationSuccessWithData(EnumUtils.getEnumDataList(UserType.class), "ecmp_context_00001");

    }

}
