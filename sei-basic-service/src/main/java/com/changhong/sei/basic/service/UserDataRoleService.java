package com.changhong.sei.basic.service;

import com.changhong.sei.basic.dao.UserDataRoleDao;
import com.changhong.sei.basic.entity.*;
import com.changhong.sei.basic.service.util.AuthorityUtil;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseRelationDao;
import com.changhong.sei.core.service.BaseRelationService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.util.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：用户分配的数据角色的业务逻辑实现
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017/5/16 10:20      秦有宝                     新建
 * <p/>
 * *************************************************************************************************
 */
@Service
public class UserDataRoleService extends BaseRelationService<UserDataRole, User, DataRole> {
    @Autowired
    private UserDataRoleDao dao;

    @Override
    protected BaseRelationDao<UserDataRole, User, DataRole> getDao() {
        return dao;
    }

    @Autowired
    private DataRoleService dataRoleService;

    /**
     * 获取可以分配的子实体清单
     * @return 子实体清单
     */
    @Override
    protected List<DataRole> getCanAssignedChildren(String parentId){
        // 判断用户权限
        return dataRoleService.findByCreator();
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
        if(parentId.equals(ContextUtil.getUserId())){
            //00033 = 不能为当前用户分配数据角色！
            return OperateResult.operationFailure("00033");
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
        if(parentId.equals(ContextUtil.getUserId())){
            //00034= 不能移除当前用户的数据角色！
            return OperateResult.operationFailure("00034");
        }
        OperateResult result = super.removeRelations(parentId, childIds);
        // 清除用户权限缓存
        AuthorityUtil.cleanUserAuthorizedCaches(parentId);
        return result;
    }

    /**
     * 通过父实体Id获取子实体清单
     *
     * @param parentId 父实体Id
     * @return 子实体清单
     */
    @Override
    public List<DataRole> getChildrenFromParentId(String parentId) {
        // 获取分配关系
        List<UserDataRole> userDataRoles = getRelationsByParentId(parentId);
        // 设置授权有效期
        List<DataRole> dataRoles = new LinkedList<>();
        userDataRoles.forEach(r-> {
            DataRole dataRole = r.getChild();
            dataRole.setEffectiveFrom(r.getEffectiveFrom());
            dataRole.setEffectiveTo(r.getEffectiveTo());
            dataRoles.add(dataRole);
        });
        return dataRoles;
    }

    /**
     * 获取当前有效的授权数据角色清单
     * @param parentId 用户Id
     * @return 有效的授权数据角色清单
     */
    public List<DataRole> getEffectiveChildren(String parentId) {
        List<DataRole> dataRoles = new LinkedList<>();
        // 获取分配的功能项
        List<DataRole> children = getChildrenFromParentId(parentId);
        if (CollectionUtils.isEmpty(children)) {
            return children;
        }
        // 判断有效期
        children.forEach(c-> {
            if (Objects.isNull(c.getEffectiveFrom())
                    || Objects.isNull(c.getEffectiveTo())) {
                dataRoles.add(c);
            } else {
                Date currentDte = DateUtils.getCurrentDate();
                if (currentDte.after(c.getEffectiveFrom())
                        && currentDte.before(c.getEffectiveTo())) {
                    dataRoles.add(c);
                }
            }
        });
        return dataRoles;
    }
}
