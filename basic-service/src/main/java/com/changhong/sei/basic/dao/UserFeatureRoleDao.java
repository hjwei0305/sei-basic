package com.changhong.sei.basic.dao;

import com.changhong.sei.basic.entity.FeatureRole;
import com.changhong.sei.basic.entity.User;
import com.changhong.sei.basic.entity.UserFeatureRole;
import com.changhong.sei.core.dao.BaseRelationDao;
import org.springframework.stereotype.Repository;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：用户分配功能角色
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017-05-04 13:26      王锦光(wangj)                新建
 * <p/>
 * *************************************************************************************************
 */
@Repository
public interface UserFeatureRoleDao extends BaseRelationDao<UserFeatureRole, User, FeatureRole> {
}
