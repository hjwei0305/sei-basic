package com.changhong.sei.basic.service;

import com.changhong.sei.basic.dao.FeatureRoleDao;
import com.changhong.sei.basic.dto.RoleSourceType;
import com.changhong.sei.basic.entity.*;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.context.SessionUser;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.entity.ICodeUnique;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.DataAuthEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.enums.UserAuthorityPolicy;
import com.changhong.sei.enums.UserType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
public class FeatureRoleService extends BaseEntityService<FeatureRole> implements DataAuthEntityService {

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
    @Autowired
    private UserService userService;

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
            // 功能角色存在已经分配的功能项，禁止删除！
            return OperateResult.operationFailure("00096");
        }
        list = positionFeatureRoleService.getParentsFromChildId(s);
        if (list != null && list.size() > 0) {
            //功能角色存在已经分配的功能项，禁止删除！
            return OperateResult.operationFailure("00097");
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
        return dao.findFirstByCodeAndTenantCode(code, ContextUtil.getTenantCode());
    }

    /**
     * 获取用户的公共功能角色
     * @param user 用户
     * @return 公共功能角色清单
     */
    List<FeatureRole> getPublicFeatureRoles(User user){
        if (Objects.isNull(user) || StringUtils.isBlank(user.getId())) {
            return new ArrayList<>();
        }
        //获取用户类型匹配的全局公共角色
        List<FeatureRole> publicRoles = dao.getPublicRoles(user);
        List<FeatureRole> result = new ArrayList<>(publicRoles);
        String userId = user.getId();
        //获取用户的组织机构
        if (user.getUserType()== UserType.Employee){
            Employee employee = employeeService.findOne(userId);
            if (employee!=null){
                //获取企业用户的组织机构
                List<Organization> orgs = organizationService.getParentNodes(employee.getOrganization().getId(),true);
                List<FeatureRole> orgPubRoles = dao.getPublicRoles(user,orgs);
                result.addAll(orgPubRoles);
            }
        }
        // 设置来源类型
        result.forEach(role-> role.setRoleSourceType(RoleSourceType.PUBLIC));
        return result;
    }

    /**
     * 获取用户本人可以分配的角色清单
     * @return 角色清单
     */
    List<FeatureRole> getCanAssignedRoles(){
        return getCanAssignedRoles(null);
    }

    /**
     * 获取用户本人可以分配的角色
     * @param roleGroupId 角色组Id
     * @return 可以分配的角色
     */
    public List<FeatureRole> getCanAssignedRoles(String roleGroupId){
        // 判断用户权限
        SessionUser sessionUser = ContextUtil.getSessionUser();
        if (sessionUser.isAnonymous() || sessionUser.getAuthorityPolicy() == UserAuthorityPolicy.GlobalAdmin){
            return new ArrayList<>();
        }
        // 如果是租户管理员，返回所有
        if (sessionUser.getAuthorityPolicy() == UserAuthorityPolicy.TenantAdmin){
            if (StringUtils.isBlank(roleGroupId)) {
                return findAll();
            } else {
                return dao.findByFeatureRoleGroupId(roleGroupId);
            }
        }
        // 如果是一般分级授权用户，获取有数据权限的角色+本人创建的角色
        Set<FeatureRole> roles = new LinkedHashSet<>();
        List<FeatureRole> authRoles = getUserAuthorizedEntities(null);
        if (CollectionUtils.isNotEmpty(authRoles)) {
            if (StringUtils.isBlank(roleGroupId)) {
                roles.addAll(authRoles);
            } else {
                // 过滤角色组
                roles.addAll(authRoles.stream().filter(r-> StringUtils.equals(r.getFeatureRoleGroupId(), roleGroupId)).collect(Collectors.toList()));
            }
        }
        if (StringUtils.isBlank(roleGroupId)) {
            roles.addAll(dao.findByCreatorAccountAndTenantCode(sessionUser.getAccount(), sessionUser.getTenantCode()));
        } else {
            roles.addAll(dao.findByCreator(roleGroupId, sessionUser.getAccount(), sessionUser.getTenantCode()));
        }
        return new ArrayList<>(roles);
    }

    /**
     * 从平台基础应用获取一般用户有权限的数据实体Id清单
     * 对于数据权限对象的业务实体，需要override，使用BASIC提供的通用工具来获取
     *
     * @param entityClassName 权限对象实体类型
     * @param featureCode     功能项代码
     * @param userId          用户Id
     * @return 数据实体Id清单
     */
    @Override
    public List<String> getNormalUserAuthorizedEntitiesFromBasic(String entityClassName, String featureCode, String userId) {
        return userService.getNormalUserAuthorizedEntities(entityClassName, featureCode, userId);
    }
}
