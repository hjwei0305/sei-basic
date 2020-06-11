package com.changhong.sei.basic.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.changhong.sei.core.dto.RelationEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 实现功能: 用户收藏的菜单DTO
 *
 * @author 王锦光 wangjg
 * @version 2020-01-27 15:04
 */
@ApiModel(description = "用户收藏的菜单DTO")
public class UserMenuDto extends BaseEntityDto implements RelationEntityDto<UserDto, MenuDto> {
    /**
     * 用户DTO
     */
    @ApiModelProperty(value = "用户DTO(EmployeeDto)", required = true)
    private UserDto parent;

    /**
     * 菜单DTO
     */
    @ApiModelProperty(value = "菜单DTO(MenuDto)", required = true)
    private MenuDto child;

    @Override
    public UserDto getParent() {
        return parent;
    }

    @Override
    public void setParent(UserDto parent) {
        this.parent = parent;
    }

    @Override
    public MenuDto getChild() {
        return child;
    }

    @Override
    public void setChild(MenuDto child) {
        this.child = child;
    }
}
