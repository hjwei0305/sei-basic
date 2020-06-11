package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.MenuDto;
import com.changhong.sei.basic.dto.UserDto;
import com.changhong.sei.basic.dto.UserMenuDto;
import com.changhong.sei.core.api.BaseRelationApi;
import com.changhong.sei.core.dto.ResultData;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * 实现功能: 用户收藏的菜单API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-27 15:03
 */
@FeignClient(name = "sei-basic", path = "userMenu")
public interface UserMenuApi extends BaseRelationApi<UserMenuDto, UserDto, MenuDto> {
    /**
     * 当前用户收藏一个菜单
     * @param menuId 菜单Id
     * @return 处理结果
     */
    @PostMapping(path = "insertMenu/{menuId}")
    @ApiOperation(value = "当前用户收藏一个菜单", notes = "当前用户添加一个菜单项到收藏夹")
    ResultData<?> insertMenu(@PathVariable("menuId") String menuId);

    /**
     * 当前用户移除一个已收藏的菜单项
     * @param menuId 菜单Id
     * @return 处理结果
     */
    @PostMapping(path = "removeMenu/{menuId}")
    @ApiOperation(value = "当前用户移除一个已收藏的菜单项", notes = "当前用户从收藏夹移除一个菜单项")
    ResultData<?> removeMenu(@PathVariable("menuId") String menuId);

    /**
     * 获取当前用户收藏的菜单清单
     * @return 菜单清单
     */
    @GetMapping(path = "getFavoriteMenus")
    @ApiOperation(value = "获取当前用户收藏的菜单清单", notes = "获取当前用户收藏的菜单项清单")
    ResultData<List<MenuDto>> getFavoriteMenus();
}
