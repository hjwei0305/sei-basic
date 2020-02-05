package com.changhong.sei.basic.dao;

import com.changhong.sei.basic.entity.UserEmailAlert;

import java.util.List;

/**
 * Created by WangShuFa on 2018/7/11.
 */
public interface UserEmailAlertExtDao {
    /**
     * 通过用户ID列表获取邮件通知列表
     * @param userIds
     * @return
     */
    List<UserEmailAlert> findByUserIds(List<String> userIds);
}
