package com.changhong.sei.basic.dao;

import com.changhong.sei.basic.entity.Menu;
import com.changhong.sei.basic.entity.User;
import com.changhong.sei.basic.entity.UserMenu;
import com.changhong.sei.core.dao.BaseRelationDao;
import org.springframework.stereotype.Repository;

/**
 * 实现功能： 用户收藏的菜单数据访问接口
 */
@Repository
public interface UserMenuDao extends BaseRelationDao<UserMenu, User, Menu> {
}
