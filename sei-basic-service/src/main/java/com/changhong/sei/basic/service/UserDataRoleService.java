package com.changhong.sei.basic.service;

import com.changhong.sei.basic.dao.UserDataRoleDao;
import com.changhong.sei.basic.entity.DataRole;
import com.changhong.sei.basic.entity.User;
import com.changhong.sei.basic.entity.UserDataRole;
import com.changhong.sei.basic.service.util.AuthorityUtil;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseRelationDao;
import com.changhong.sei.core.service.BaseRelationService;
import com.changhong.sei.core.service.bo.OperateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
