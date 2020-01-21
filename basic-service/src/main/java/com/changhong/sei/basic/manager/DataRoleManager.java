package com.changhong.sei.basic.manager;

import com.changhong.sei.basic.api.OrganizationService;
import com.changhong.sei.basic.dao.DataRoleDao;
import com.changhong.sei.basic.entity.DataRole;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.context.SessionUser;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.manager.BaseEntityManager;
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
    private OrganizationService organizationService;
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
