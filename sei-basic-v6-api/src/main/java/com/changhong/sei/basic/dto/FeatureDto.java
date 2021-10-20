package com.changhong.sei.basic.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.changhong.sei.core.dto.annotation.QueryFieldMapping;
import com.changhong.sei.core.dto.serializer.EnumJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 实现功能: 功能项DTO
 *
 * @author 王锦光 wangjg
 * @version 2020-01-19 20:51
 */
@ApiModel(description = "功能项DTO")
public class FeatureDto extends BaseEntityDto {
    /**
     * 功能项代码
     */
    @NotBlank
    @Size(max = 50)
    @ApiModelProperty(value = "功能项代码(max = 50)", required = true)
    private String code;

    /**
     * 功能项名称
     */
    @NotBlank
    @Size(max = 30)
    @ApiModelProperty(value = "功能项名称(max = 30)", required = true)
    private String name;

    /**
     * 分组代码
     */
    @ApiModelProperty(value = "分组代码(max = 128)")
    private String groupCode;

    /**
     * 资源
     */
    @ApiModelProperty(value = "资源(max = 400)")
    private String url;

    /**
     * 是否菜单项
     */
    @ApiModelProperty(value = "是否菜单项", required = true)
    private Boolean canMenu = Boolean.FALSE;

    /**
     * 功能项类型：0：操作(Operate),1：业务(Business),2:页面(Page)
     */
    @ApiModelProperty(value = "功能项类型(enum)", required = true)
    @NotNull
    @JsonSerialize(using = EnumJsonSerializer.class)
    private FeatureType featureType = FeatureType.Operate;

    /**
     * 功能项组Id
     */
    @NotBlank
    @Size(max = 36)
    @ApiModelProperty(value = "功能项组Id(max = 36)", required = true)
    private String featureGroupId;

    /**
     * 租户可用
     */
    @NotNull
    @ApiModelProperty(value = "租户可用", required = true)
    private Boolean tenantCanUse = Boolean.FALSE;

    /**
     * 应用模块名称
     */
    @ApiModelProperty(value = "应用模块名称")
    @QueryFieldMapping("featureGroup.appModule.name")
    private String appModuleName;

    /**
     * 功能项组代码
     */
    @ApiModelProperty(value = "功能项组代码")
    @QueryFieldMapping("featureGroup.code")
    private String featureGroupCode;
    /**
     * 功能项组名称
     */
    @ApiModelProperty(value = "功能项组名称")
    @QueryFieldMapping("featureGroup.name")
    private String featureGroupName;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getCanMenu() {
        return canMenu;
    }

    public void setCanMenu(Boolean canMenu) {
        this.canMenu = canMenu;
    }

    public FeatureType getFeatureType() {
        return featureType;
    }

    public void setFeatureType(FeatureType featureType) {
        this.featureType = featureType;
    }

    public Boolean getTenantCanUse() {
        return tenantCanUse;
    }

    public void setTenantCanUse(Boolean tenantCanUse) {
        this.tenantCanUse = tenantCanUse;
    }

    public String getAppModuleName() {
        return appModuleName;
    }

    public void setAppModuleName(String appModuleName) {
        this.appModuleName = appModuleName;
    }

    public String getFeatureGroupId() {
        return featureGroupId;
    }

    public void setFeatureGroupId(String featureGroupId) {
        this.featureGroupId = featureGroupId;
    }

    public String getFeatureGroupCode() {
        return featureGroupCode;
    }

    public void setFeatureGroupCode(String featureGroupCode) {
        this.featureGroupCode = featureGroupCode;
    }

    public String getFeatureGroupName() {
        return featureGroupName;
    }

    public void setFeatureGroupName(String featureGroupName) {
        this.featureGroupName = featureGroupName;
    }
}
