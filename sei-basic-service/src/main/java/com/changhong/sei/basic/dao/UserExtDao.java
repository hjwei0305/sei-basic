package com.changhong.sei.basic.dao;

import com.changhong.sei.basic.dto.search.UserQuickQueryParam;
import com.changhong.sei.basic.entity.User;
import com.changhong.sei.core.dto.serach.PageResult;

import java.util.List;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：用户扩展接口
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017/5/26 13:59      秦有宝                     新建
 * <p/>
 * *************************************************************************************************
 */
public interface UserExtDao {
    /**
     * 分页查询用户
     *
     * @param queryParam 查询参数
     * @param excludeUserIds 排除的用户Id清单
     * @param tenantCode 租户代码
     * @return 用户
     */
    PageResult<User> queryUsers(UserQuickQueryParam queryParam, List<String> excludeUserIds, String tenantCode);
}
