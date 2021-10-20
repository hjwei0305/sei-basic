package com.changhong.sei.basic.dto;

import com.changhong.sei.annotation.Remark;

/**
 * 实现功能: 角色来源类型枚举
 *
 * @author 王锦光 wangjg
 * @version 2020-05-20 11:12
 */
public enum RoleSourceType {
    @Remark(value = "公共角色")
    PUBLIC,

    @Remark(value = "岗位角色")
    POSITION,

    @Remark(value = "用户角色")
    USER;
}
