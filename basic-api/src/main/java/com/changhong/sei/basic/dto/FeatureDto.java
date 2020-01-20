package com.changhong.sei.basic.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.changhong.sei.core.dto.serializer.EnumJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;

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
    private String code;

    /**
     * 功能项名称
     */
    @NotBlank
    @Size(max = 30)
    private String name;

    /**
     * 分组代码
     */
    private String groupCode;

    /**
     * 资源
     */
    private String url;

    /**
     * 是否菜单项
     */
    private Boolean canMenu = Boolean.FALSE;

    /**
     * 功能项类型：0：操作(Operate),1：业务(Business),2:页面(Page)
     */
    @NotNull
    @JsonSerialize(using = EnumJsonSerializer.class)
    private FeatureType featureType = FeatureType.Operate;

    /**
     * 功能项组Id
     */
    @NotBlank
    @Size(max = 36)
    private String featureGroupId;

    /**
     * 租户可用
     */
    @NotNull
    private Boolean tenantCanUse = Boolean.FALSE;

    /**
     * 应用模块名称
     */
    private String appModuleName;

    /**
     * 功能项组代码
     */
    private String featureGroupCode;
    /**
     * 功能项组名称
     */
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
