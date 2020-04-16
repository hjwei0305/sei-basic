package com.changhong.sei.basic.service;

import com.changhong.sei.basic.dao.UserFeatureRoleDao;
import com.changhong.sei.basic.dto.RelationEffective;
import com.changhong.sei.basic.entity.FeatureRole;
import com.changhong.sei.basic.entity.User;
import com.changhong.sei.basic.entity.UserDataRole;
import com.changhong.sei.basic.entity.UserFeatureRole;
import com.changhong.sei.basic.service.util.AuthorityUtil;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseRelationDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseRelationService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.utils.ResultDataUtil;
import com.changhong.sei.util.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：用户分配的功能角色的业务逻辑实现
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017/5/16 10:20      秦有宝                     新建
 * <p/>
 * *************************************************************************************************
 */
@Service
public class UserFeatureRoleService extends BaseRelationService<UserFeatureRole, User, FeatureRole> {
    @Autowired
    private UserFeatureRoleDao dao;

    @Override
    protected BaseRelationDao<UserFeatureRole, User, FeatureRole> getDao() {
        return dao;
    }

    @Autowired
    private FeatureRoleService featureRoleService;

    /**
     * 获取可以分配的子实体清单
     *
     * @return 子实体清单
     */
    @Override
    protected List<FeatureRole> getCanAssignedChildren(String parentId) {
        // 判断用户权限
        return featureRoleService.findByCreator();
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
            //00031 = 不能为当前用户分配功能角色！
            return OperateResult.operationFailure("00031");
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
            //00032 = 不能移除当前用户的功能角色！
            return OperateResult.operationFailure("00032");
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
    public List<FeatureRole> getChildrenFromParentId(String parentId) {
        // 获取分配关系
        List<UserFeatureRole> userFeatureRoles = getRelationsByParentId(parentId);
        // 设置授权有效期
        List<FeatureRole> featureRoles = new LinkedList<>();
        userFeatureRoles.forEach(r-> {
            FeatureRole featureRole = r.getChild();
            featureRole.setRelationId(r.getId());
            featureRole.setEffectiveFrom(r.getEffectiveFrom());
            featureRole.setEffectiveTo(r.getEffectiveTo());
            featureRoles.add(featureRole);
        });
        return featureRoles;
    }

    /**
     * 获取当前有效的授权功能角色清单
     * @param parentId 用户Id
     * @return 有效的授权功能角色清单
     */
    public List<FeatureRole> getEffectiveChildren(String parentId) {
        List<FeatureRole> featureRoles = new LinkedList<>();
        // 获取分配的功能项
        List<FeatureRole> children = getChildrenFromParentId(parentId);
        if (CollectionUtils.isEmpty(children)) {
            return children;
        }
        // 判断有效期
        children.forEach(c-> {
            if (Objects.isNull(c.getEffectiveFrom())
                    || Objects.isNull(c.getEffectiveTo())) {
                featureRoles.add(c);
            } else {
                Date currentDate = DateUtils.getCurrentDate();
                Date fromDate = DateUtils.nDaysAfter(-1, c.getEffectiveFrom());
                Date toDate = DateUtils.nDaysAfter(1, c.getEffectiveTo());
                if (currentDate.after(fromDate)
                        && currentDate.before(toDate)) {
                    featureRoles.add(c);
                }
            }
        });
        return featureRoles;
    }

    /**
     * 保存授权有效期
     *
     * @param effective 授权有效期
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<String> saveEffective(RelationEffective effective) {
        // 获取分配关系
        UserFeatureRole relation = dao.findOne(effective.getId());
        if (Objects.isNull(relation)) {
            // 保存有效期的授权分配关系不存在！
            return ResultDataUtil.fail("00106");
        }
        // 设置有效期
        relation.setEffectiveFrom(effective.getEffectiveFrom());
        relation.setEffectiveTo(effective.getEffectiveTo());
        dao.save(relation);
        return ResultData.success(effective.getId());
    }
}
