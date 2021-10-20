package com.changhong.sei.basic.service;

import com.changhong.sei.basic.dao.PositionDataRoleDao;
import com.changhong.sei.basic.dto.RoleSourceType;
import com.changhong.sei.basic.entity.DataRole;
import com.changhong.sei.basic.entity.EmployeePosition;
import com.changhong.sei.basic.entity.Position;
import com.changhong.sei.basic.entity.PositionDataRole;
import com.changhong.sei.basic.service.util.AuthorityUtil;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseRelationDao;
import com.changhong.sei.core.service.BaseRelationService;
import com.changhong.sei.core.service.bo.OperateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：岗位分配的数据角色的业务逻辑实现
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017/5/16 10:04      秦有宝                     新建
 * <p/>
 * *************************************************************************************************
 */
@Service
public class PositionDataRoleService extends BaseRelationService<PositionDataRole, Position, DataRole> {

    @Autowired
    private PositionDataRoleDao dao;
    @Autowired
    private EmployeePositionService employeePositionService;

    @Override
    protected BaseRelationDao<PositionDataRole, Position, DataRole> getDao() {
        return dao;
    }

    @Autowired
    private DataRoleService dataRoleService;

    /**
     * 获取可以分配的子实体清单
     *
     * @return 子实体清单
     */
    @Override
    protected List<DataRole> getCanAssignedChildren(String parentId) {
        return dataRoleService.findAll();
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
        EmployeePosition employeePosition = employeePositionService.getRelation(ContextUtil.getUserId(), parentId);
        if (Objects.nonNull(employeePosition)) {
            //00035 = 不能给当前用户的岗位分配数据角色！
            return OperateResult.operationFailure("00035");
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
        EmployeePosition employeePosition = employeePositionService.getRelation(ContextUtil.getUserId(), parentId);
        if (Objects.nonNull(employeePosition)) {
            //00036 = 不能给当前用户的岗位移除数据角色！
            return OperateResult.operationFailure("00036");
        }
        OperateResult result = super.removeRelations(parentId, childIds);
        // 清除用户权限缓存
        AuthorityUtil.cleanAuthorizedCachesByPositionId(parentId);
        return result;
    }

    /**
     * 通过父实体Id清单获取子实体清单
     *
     * @param parentIds 父实体Id清单
     * @return 子实体清单
     */
    @Override
    public List<DataRole> getChildrenFromParentIds(List<String> parentIds) {
        List<DataRole> roles = super.getChildrenFromParentIds(parentIds);
        // 设置来源类型
        roles.forEach(role-> role.setRoleSourceType(RoleSourceType.POSITION));
        return roles;
    }
}
