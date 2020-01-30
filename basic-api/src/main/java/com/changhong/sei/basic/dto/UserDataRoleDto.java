package com.changhong.sei.basic.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.changhong.sei.core.dto.RelationEntityDto;
import io.swagger.annotations.ApiModel;

/**
 * 实现功能: 用户分配的数据角色DTO
 *
 * @author 王锦光 wangjg
 * @version 2020-01-30 9:22
 */
@ApiModel(description = "用户分配的数据角色DTO")
public class UserDataRoleDto extends BaseEntityDto implements RelationEntityDto<UserDto, DataRoleDto> {
    /**
     * 用户DTO
     */
    private UserDto parent;
    /**
     * 数据角色DTO
     */
    private DataRoleDto child;

    @Override
    public UserDto getParent() {
        return parent;
    }

    @Override
    public void setParent(UserDto parent) {
        this.parent = parent;
    }

    @Override
    public DataRoleDto getChild() {
        return child;
    }

    @Override
    public void setChild(DataRoleDto child) {
        this.child = child;
    }
}
