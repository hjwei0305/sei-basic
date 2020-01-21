package com.changhong.sei.basic.dao;

import com.changhong.sei.basic.entity.UserProfile;
import com.changhong.sei.core.dao.BaseEntityDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：用户配置数据访问接口
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间                  变更人                 变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017/4/14 15:45            高银军                  新建
 * <p/>
 * *************************************************************************************************
 */
@Repository
public interface UserProfileDao extends BaseEntityDao<UserProfile>, UserProfileExtDao {

    /**
     * 根据用户的id查询用户配置
     *
     * @param userId 用户id
     * @return 用户配置
     */
    UserProfile findByUserId(String userId);

    /**
     *  根据邮箱查询用户配置
     * @param email 用户邮箱
     * @return 用户配置
     */
    List<UserProfile> findByEmail(String email);
}
