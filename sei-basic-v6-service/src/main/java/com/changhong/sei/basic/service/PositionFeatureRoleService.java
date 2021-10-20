package com.changhong.sei.basic.service;

import com.changhong.sei.basic.dao.PositionFeatureRoleDao;
import com.changhong.sei.basic.dto.RoleSourceType;
import com.changhong.sei.basic.entity.EmployeePosition;
import com.changhong.sei.basic.entity.FeatureRole;
import com.changhong.sei.basic.entity.Position;
import com.changhong.sei.basic.entity.PositionFeatureRole;
import com.changhong.sei.basic.service.util.AuthorityUtil;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseRelationDao;
import com.changhong.sei.core.dto.ParentRelationParam;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseRelationService;
import com.changhong.sei.core.service.bo.OperateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：岗位分配的功能角色的业务逻辑实现
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017/5/16 10:04      秦有宝                     新建
 * <p/>
 * *************************************************************************************************
 */
@Service
public class PositionFeatureRoleService extends BaseRelationService<PositionFeatureRole, Position, FeatureRole> {

    @Autowired
    private PositionFeatureRoleDao dao;
    @Autowired
    private EmployeePositionService employeePositionService;

    @Override
    protected BaseRelationDao<PositionFeatureRole, Position, FeatureRole> getDao() {
        return dao;
    }

    @Autowired
    private FeatureRoleService featureRoleService;

    /**
     * 获取可以分配的子实体清单
     * @return 子实体清单
     */
    @Override
    protected List<FeatureRole> getCanAssignedChildren(String parentId){
        return featureRoleService.findAll();
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
        EmployeePosition employeePosition = employeePositionService.getRelation(ContextUtil.getUserId(),parentId);
        if(Objects.nonNull(employeePosition)){
            //00025 = 不能给当前用户的岗位分配功能角色！
            return OperateResult.operationFailure("00025");
        }
        OperateResult result = super.insertRelations(parentId, childIds);
        // 清除用户权限缓存
        AuthorityUtil.cleanAuthorizedCachesByPositionId(parentId);
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
        EmployeePosition employeePosition = employeePositionService.getRelation(ContextUtil.getUserId(),parentId);
        if(Objects.nonNull(employeePosition)){
            //00026 = 不能给当前用户的岗位移除功能角色！
            return OperateResult.operationFailure("00026");
        }
        OperateResult result = super.removeRelations(parentId, childIds);
        // 清除用户权限缓存
        AuthorityUtil.cleanAuthorizedCachesByPositionId(parentId);
        return result;
    }

    /**
     * 通过父实体清单保存分配关系
     *
     * @param relationParam 父实体Id清单的分配参数
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public OperateResult saveRelationsByParents(ParentRelationParam relationParam) {
        // 先删除现有分配关系
        removeRelationsByParents(relationParam.getChildId(), relationParam.getParentIds());
        // 在插入输入的分配关系
        insertRelationsByParents(relationParam.getChildId(), relationParam.getParentIds());
        // 通过岗位Id清单保存分配关系成功！
        return OperateResult.operationSuccess("00107");
    }

    /**
     * 通过父实体Id清单获取子实体清单
     *
     * @param parentIds 父实体Id清单
     * @return 子实体清单
     */
    @Override
    public List<FeatureRole> getChildrenFromParentIds(List<String> parentIds) {
        List<FeatureRole> roles = super.getChildrenFromParentIds(parentIds);
        // 设置来源类型
        roles.forEach(role-> role.setRoleSourceType(RoleSourceType.POSITION));
        return roles;
    }
}
