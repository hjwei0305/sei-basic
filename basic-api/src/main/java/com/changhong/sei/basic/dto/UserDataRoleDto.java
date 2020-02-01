package com.changhong.sei.basic.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.changhong.sei.core.dto.RelationEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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
    @ApiModelProperty(value = "用户DTO", required = true)
    private UserDto parent;
    /**
     * 数据角色DTO
     */
    @ApiModelProperty(value = "数据角色DTO", required = true)
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
