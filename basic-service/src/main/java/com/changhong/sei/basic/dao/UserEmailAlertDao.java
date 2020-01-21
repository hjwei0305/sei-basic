package com.changhong.sei.basic.dao;

import com.changhong.sei.basic.entity.UserEmailAlert;
import com.changhong.sei.core.dao.BaseEntityDao;
import org.springframework.stereotype.Repository;

/**
 * Created by WangShuFa on 2018/7/11.
 */
@Repository
public interface UserEmailAlertDao extends BaseEntityDao<UserEmailAlert>,UserEmailAlertExtDao{
}
