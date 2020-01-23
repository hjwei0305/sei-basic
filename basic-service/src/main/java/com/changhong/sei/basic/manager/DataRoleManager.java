package com.changhong.sei.basic.manager;

import com.changhong.sei.basic.dao.DataRoleDao;
import com.changhong.sei.basic.entity.DataRole;
import com.changhong.sei.basic.entity.Employee;
import com.changhong.sei.basic.entity.Organization;
import com.changhong.sei.basic.entity.User;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.context.SessionUser;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.manager.BaseEntityManager;
import com.changhong.sei.core.manager.bo.OperateResult;
import com.chonghong.sei.enums.UserAuthorityPolicy;
import com.chonghong.sei.enums.UserType;
import com.chonghong.sei.util.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：数据角色业务逻辑实现
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017-05-04 16:35      王锦光(wangj)                新建
 * <p/>
 * *************************************************************************************************
 */
@Component
public class DataRoleManager extends BaseEntityManager<DataRole> {

    @Autowired
    private DataRoleDao dao;
    @Override
    protected BaseEntityDao<DataRole> getDao() {
        return dao;
    }
    @Autowired
    private EmployeeManager employeeManager;
    @Autowired
    private OrganizationManager organizationManager;
    @Autowired
    private DataRoleAuthTypeValueManager dataRoleAuthTypeValueManager;
    @Autowired
    private PositionDataRoleManager positionDataRoleManager;
    @Autowired
    private UserDataRoleManager userDataRoleManager;
    /**
     * 通过角色组Id获取角色清单
     *
     * @param roleGroupId 角色组Id
     * @return 角色清单
     */

    public List<DataRole> findByDataRoleGroup(String roleGroupId) {
        SearchFilter filter = new SearchFilter("dataRoleGroup.id",roleGroupId, SearchFilter.Operator.EQ);
        List<DataRole> results =  findByFilter(filter);
        for (DataRole data : results) {
            if (data.getPublicUserType() != null) {
                data.setPublicUserTypeRemark(EnumUtils.getEnumItemRemark(UserType.class, data.getPublicUserType().ordinal()));
            }
        }
        return results;
    }

    /**
     * 删除数据保存数据之前额外操作回调方法 子类根据需要覆写添加逻辑即可
     *
     *
     * @param s 待删除数据对象主键
     */
    @Override
    protected OperateResult preDelete(String s) {
        if (dataRoleAuthTypeValueManager.isExistsByProperty("dataRole.id",s)){
            //数据角色存在数据角色分配权限类型的值，禁止删除！
            return OperateResult.operationFailure("00022");
        }
        if (positionDataRoleManager.isExistsByProperty("child.id",s)){
            //数据角色存在已分配的岗位，禁止删除！
            return OperateResult.operationFailure("00023");
        }
        if (userDataRoleManager.isExistsByProperty("child.id",s)){
            //数据角色存在已分配的用户，禁止删除！
            return OperateResult.operationFailure("00024");
        }
        return super.preDelete(s);
    }

    /**
     * 获取用户的公共功能角色
     * @param user 用户
     * @return 公共功能角色清单
     */
    List<DataRole> getPublicDataRoles(User user){
        List<DataRole> result = new ArrayList<>();
        //获取用户类型匹配的全局公共角色
        List<DataRole> publicRoles = dao.getPublicRoles(user);
        result.addAll(publicRoles);
        //获取用户的组织机构
        if (user.getUserType()== UserType.Employee){
            Employee employee =employeeManager.findOne(user.getId());
            if (employee!=null){
                //获取企业用户的组织机构
                List<Organization> orgs = organizationManager.getParentNodes(employee.getOrganization().getId(),true);
                List<DataRole> orgPubRoles = dao.getPublicRoles(user,orgs);
                result.addAll(orgPubRoles);
            }
        }
        return result;
    }

    /**
     * 获取用户本人创建的角色
     * @return 角色清单
     */
    List<DataRole> findByCreator(){
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
    public List<DataRole> getCanAssignedRoles(String roleGroupId){
        // 判断用户权限
        SessionUser sessionUser = ContextUtil.getSessionUser();
        if (sessionUser.isAnonymous()){
            return new ArrayList<>();
        }
        // 如果是租户管理员，返回所有
        if (sessionUser.getAuthorityPolicy() == UserAuthorityPolicy.TenantAdmin){
            return findByDataRoleGroup(roleGroupId);
        }
        // 如果是一般分级授权用户，返回本人创建的角色
        return dao.findByCreator(roleGroupId, sessionUser.getAccount(), sessionUser.getTenantCode());
    }
}
