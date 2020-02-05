package com.changhong.sei.basic.dao;

import com.changhong.sei.basic.entity.FeatureRole;
import com.changhong.sei.basic.entity.Position;
import com.changhong.sei.basic.entity.PositionFeatureRole;
import com.changhong.sei.core.dao.BaseRelationDao;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：岗位分配功能角色数据访问接口
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017-05-04 13:25      王锦光(wangj)                新建
 * <p/>
 * *************************************************************************************************
 */
public interface PositionFeatureRoleDao extends BaseRelationDao<PositionFeatureRole, Position, FeatureRole> {
}
