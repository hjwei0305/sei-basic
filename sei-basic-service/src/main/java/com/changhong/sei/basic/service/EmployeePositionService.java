package com.changhong.sei.basic.service;

import com.changhong.sei.basic.dao.EmployeePositionDao;
import com.changhong.sei.basic.entity.Employee;
import com.changhong.sei.basic.entity.EmployeePosition;
import com.changhong.sei.basic.entity.Position;
import com.changhong.sei.basic.service.util.AuthorityUtil;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseRelationDao;
import com.changhong.sei.core.service.BaseRelationService;
import com.changhong.sei.core.service.bo.OperateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * 根据岗位的id获取已分配的员工
     *
     * @param positionId 岗位的id
     * @return 员工清单
     */
    public List<Employee> listAllAssignedEmployeesByPositionId(String positionId) {
        List<Employee> employees = this.getParentsFromChildId(positionId);
        for (Employee employee : employees) {
            employee.setUserName(employee.getUser().getUserName());
        }
        return employees;
    }

    /**
     * 通过企业员工Id获取已分配岗位清单
     *
     * @param employeeId 企业员工Id
     * @return 岗位清单
     */
    public List<Position> listAllAssignedPositionsByEmployeeId(String employeeId) {
        return this.getChildrenFromParentId(employeeId);
    }
}