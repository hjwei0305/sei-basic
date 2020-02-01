package com.changhong.sei.basic.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.changhong.sei.core.dto.RelationEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 实现功能: 租户分配应用模块DTO
 *
 * @author 王锦光 wangjg
 * @version 2020-01-19 15:03
 */
@ApiModel(description = "租户分配应用模块DTO")
public class TenantAppModuleDto extends BaseEntityDto implements RelationEntityDto<TenantDto, AppModuleDto> {
    /**
     * 租户DTO
     */
    @ApiModelProperty(value = "租户DTO", required = true)
    private TenantDto parent;

    /**
     * 应用模块DTO
     */
    @ApiModelProperty(value = "应用模块DTO", required = true)
    private AppModuleDto child;

    @Override
    public TenantDto getParent() {
        return parent;
    }

    @Override
    public void setParent(TenantDto parent) {
        this.parent = parent;
    }

    @Override
    public AppModuleDto getChild() {
        return child;
    }

    @Override
    public void setChild(AppModuleDto child) {
        this.child = child;
    }
}
