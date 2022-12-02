package com.changhong.sei.basic.dao;

import com.changhong.sei.basic.entity.SysUser;
import com.changhong.sei.core.dao.BaseEntityDao;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * 系统用户(SysUser)数据库访问类
 *
 * @author sei
 * @since 2022-12-02 14:34:52
 */
@Repository
public interface SysUserDao extends BaseEntityDao<SysUser> {
    Optional<SysUser> findByEmployeeCode(String employeeCode);
}
