package com.changhong.sei.basic.dao;

import com.changhong.sei.basic.entity.Menu;
import com.changhong.sei.basic.entity.User;
import com.changhong.sei.basic.entity.UserMenu;
import com.changhong.sei.core.dao.BaseRelationDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 实现功能： 用户收藏的菜单数据访问接口
 */
@Repository
public interface UserMenuDao extends BaseRelationDao<UserMenu, User, Menu> {
    /**
     * 通过父实体Id获取子实体清单
     *
     * @param parentId 父实体Id
     * @return 子实体清单
     */
    @Override
    @Query("select r.child from UserMenu r where r.parent.id=?1 order by r.createdDate desc ")
    List<Menu> getChildrenFromParentId(String parentId);
}
