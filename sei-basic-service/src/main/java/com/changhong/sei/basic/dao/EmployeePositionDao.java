package com.changhong.sei.basic.dao;

import com.changhong.sei.basic.entity.Employee;
import com.changhong.sei.basic.entity.EmployeePosition;
import com.changhong.sei.basic.entity.Position;
import com.changhong.sei.core.dao.BaseRelationDao;
import org.springframework.stereotype.Repository;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：企业员工用户分配岗位数据访问接口
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017/5/11 20:16      秦有宝                     新建
 * <p/>
 * *************************************************************************************************
 */
@Repository
public interface EmployeePositionDao extends BaseRelationDao<EmployeePosition, Employee, Position> {
}
