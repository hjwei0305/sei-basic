package com.changhong.sei.basic.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.changhong.sei.core.dto.RelationEntityDto;
import io.swagger.annotations.ApiModel;

/**
 * 实现功能: 用户分配的功能角色DTO
 *
 * @author 王锦光 wangjg
 * @version 2020-01-30 9:22
 */
@ApiModel(description = "用户分配的功能角色DTO")
public class UserFeatureRoleDto extends BaseEntityDto implements RelationEntityDto<UserDto, FeatureRoleDto> {
    /**
     * 用户DTO
     */
    private UserDto parent;
    /**
     * 角色DTO
     */
    private FeatureRoleDto child;

    @Override
    public UserDto getParent() {
        return parent;
    }

    @Override
    public void setParent(UserDto parent) {
        this.parent = parent;
    }

    @Override
    public FeatureRoleDto getChild() {
        return child;
    }

    @Override
    public void setChild(FeatureRoleDto child) {
        this.child = child;
    }
}
