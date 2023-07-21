package com.changhong.sei.basic.dao;

import com.changhong.sei.basic.entity.SysUser;

public interface SysUserExtDao {
    SysUser save(SysUser entity, boolean isNew);
}
