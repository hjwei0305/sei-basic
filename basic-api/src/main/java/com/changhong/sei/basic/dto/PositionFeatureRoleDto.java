package com.changhong.sei.basic.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.changhong.sei.core.dto.RelationEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 实现功能: 岗位分配的功能角色DTO
 *
 * @author 王锦光 wangjg
 * @version 2020-01-29 10:02
 */
@ApiModel(description = "岗位分配的功能角色DTO")
public class PositionFeatureRoleDto extends BaseEntityDto implements RelationEntityDto<PositionDto, FeatureRoleDto> {
    /**
     * 岗位DTO
     */
    @ApiModelProperty(value = "岗位DTO", required = true)
    private PositionDto parent;

    /**
     * 功能角色DTO
     */
    @ApiModelProperty(value = "功能角色DTO", required = true)
    private FeatureRoleDto child;

    @Override
    public PositionDto getParent() {
        return parent;
    }

    @Override
    public void setParent(PositionDto parent) {
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
