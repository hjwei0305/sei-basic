package com.changhong.sei.basic.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.changhong.sei.core.dto.RelationEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 实现功能: 功能角色分配的功能项DTO
 *
 * @author 王锦光 wangjg
 * @version 2020-01-28 10:27
 */
@ApiModel(description = "功能角色分配的功能项DTO")
public class FeatureRoleFeatureDto extends BaseEntityDto
        implements RelationEntityDto<FeatureRoleDto, FeatureDto> {
    /**
     * 功能角色DTO
     */
    @ApiModelProperty(value = "功能角色DTO", required = true)
    private FeatureRoleDto parent;

    /**
     * 功能项DTO
     */
    @ApiModelProperty(value = "功能项DTO", required = true)
    private FeatureDto child;

    @Override
    public FeatureRoleDto getParent() {
        return parent;
    }

    @Override
    public void setParent(FeatureRoleDto parent) {
        this.parent = parent;
    }

    @Override
    public FeatureDto getChild() {
        return child;
    }

    @Override
    public void setChild(FeatureDto child) {
        this.child = child;
    }
}
