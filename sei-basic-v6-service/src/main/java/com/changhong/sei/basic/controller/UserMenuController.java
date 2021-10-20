package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.UserMenuApi;
import com.changhong.sei.basic.dto.MenuDto;
import com.changhong.sei.basic.dto.UserDto;
import com.changhong.sei.basic.dto.UserMenuDto;
import com.changhong.sei.basic.entity.Menu;
import com.changhong.sei.basic.entity.User;
import com.changhong.sei.basic.entity.UserMenu;
import com.changhong.sei.basic.service.UserMenuService;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.controller.BaseRelationController;
import com.changhong.sei.core.dto.RelationParam;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseRelationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * 实现功能: 用户收藏的菜单API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-27 16:07
 */
@RestController
@Api(value = "UserMenuApi", tags = "用户收藏的菜单API服务实现")
@RequestMapping(path = "userMenu", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserMenuController extends BaseRelationController<UserMenu, User, Menu, UserMenuDto, UserDto, MenuDto>
        implements UserMenuApi {
    @Autowired
    private UserMenuService service;

    @Override
    public BaseRelationService<UserMenu, User, Menu> getService() {
        return service;
    }

    /**
     * 将子数据实体转换成DTO
     *
     * @param entity 业务实体
     * @return DTO
     */
    @Override
    public MenuDto convertChildToDto(Menu entity) {
        return MenuController.convertToDtoStatic(entity);
    }

    /**
     * 当前用户收藏一个菜单
     *
     * @param menuId 菜单Id
     * @return 处理结果
     */
    @Override
    public ResultData<?> insertMenu(String menuId) {
        RelationParam relationParam = new RelationParam();
        relationParam.setParentId(ContextUtil.getUserId());
        relationParam.setChildIds(Collections.singletonList(menuId));
        return insertRelations(relationParam);
    }

    /**
     * 当前用户移除一个已收藏的菜单项
     *
     * @param menuId 菜单Id
     * @return 处理结果
     */
    @Override
    public ResultData<?> removeMenu(String menuId) {
        RelationParam relationParam = new RelationParam();
        relationParam.setParentId(ContextUtil.getUserId());
        relationParam.setChildIds(Collections.singletonList(menuId));
        return removeRelations(relationParam);
    }

    /**
     * 获取当前用户收藏的菜单清单
     *
     * @return 菜单清单
     */
    @Override
    public ResultData<List<MenuDto>> getFavoriteMenus() {
        return getChildrenFromParentId(ContextUtil.getUserId());
    }
}
