package com.changhong.sei.basic.dao;

import com.changhong.sei.basic.entity.DataRole;
import com.changhong.sei.basic.entity.User;
import com.changhong.sei.basic.entity.UserDataRole;
import com.changhong.sei.core.dao.BaseRelationDao;
import org.springframework.stereotype.Repository;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：用户分配数据角色
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017-05-04 13:26      王锦光(wangj)                新建
 * <p/>
 * *************************************************************************************************
 */
@Repository
public interface UserDataRoleDao extends BaseRelationDao<UserDataRole, User, DataRole> {
}
