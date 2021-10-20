package com.changhong.sei.basic.service;

import com.changhong.sei.basic.dao.UserMenuDao;
import com.changhong.sei.basic.entity.Menu;
import com.changhong.sei.basic.entity.User;
import com.changhong.sei.basic.entity.UserMenu;
import com.changhong.sei.core.dao.BaseRelationDao;
import com.changhong.sei.core.service.BaseRelationService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 实现功能： 用户收藏的菜单业务逻辑实现
 */
@Service
public class UserMenuService extends BaseRelationService<UserMenu, User, Menu> {
    @Autowired
    private UserMenuDao dao;

    @Override
    protected BaseRelationDao<UserMenu, User, Menu> getDao() {
        return dao;
    }

    /**
     * 获取可以分配的子实体清单
     *
     * @param parentId 用户Id
     * @return 子实体清单
     */
    @Override
    protected List<Menu> getCanAssignedChildren(String parentId) {
        return new ArrayList<>();
    }

    /**
     * 设置菜单项的已收藏标识
     * @param menus 菜单清单
     * @param userId 用户Id
     */
    public void fetchFavoriteMenus(List<Menu> menus, String userId) {
        if (CollectionUtils.isEmpty(menus)) {
            return;
        }
        // 获取已收藏的菜单
        List<Menu> favoriteMenus = getChildrenFromParentId(userId);
        if (CollectionUtils.isEmpty(favoriteMenus)) {
            return;
        }
        // 匹配菜单
        menus.forEach(menu -> {
            if (favoriteMenus.contains(menu)) {
                menu.setFavorite(true);
            }
        });
    }
}