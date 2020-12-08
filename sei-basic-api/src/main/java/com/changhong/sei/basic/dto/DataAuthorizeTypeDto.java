package com.changhong.sei.basic.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 实现功能: 数据权限类型DTO
 *
 * @author 王锦光 wangjg
 * @version 2020-01-20 14:11
 */
@ApiModel(description = "数据权限类型DTO")
public class DataAuthorizeTypeDto extends BaseEntityDto {
    private static final long serialVersionUID = -8595876493885390098L;
    /**
     * 代码
     */
    @NotBlank
    @Size(max = 50)
    @ApiModelProperty(value = "代码(max = 50)", required = true)
    private String code;

    /**
     * 名称
     */
    @NotBlank
    @Size(max = 50)
    @ApiModelProperty(value = "名称(max = 50)", required = true)
    private String name;

    /**
     * 权限对象类型Id
     */
    @NotBlank
    @Size(max = 36)
    @ApiModelProperty(value = "权限对象类型Id(max = 36)", required = true)
    private String authorizeEntityTypeId;

    /**
     * 权限对象类型类名
     */
    @ApiModelProperty(value = "权限对象类型类名")
    private String authorizeEntityTypeEntityClassName;

    /**
     * 权限对象类型名称
     */
    @ApiModelProperty(value = "权限对象类型名称")
    private String authorizeEntityTypeName;

    /**
     * 权限对象类型是否为树型
     */
    private Boolean authorizeEntityTypeBeTree = Boolean.FALSE;

    /**
     * 功能项Id
     */
    @Size(max = 36)
    @ApiModelProperty(value = "功能项Id(max = 36)")
    private String featureId;

    /**
     * 功能项代码
     */
    @ApiModelProperty(value = "功能项代码")
    private String featureCode;

    /**
     * 功能项名称
     */
    @ApiModelProperty(value = "功能项名称")
    private String featureName;
    /**
     * API基地址
     */
    @ApiModelProperty(value = "API基地址")
    private String apiBaseAddress;
    /**
     * API URL
     */
    @ApiModelProperty(value = "API Path")
    private String apiPath;

    /**
     * 应用模块Id
     */
    private String authorizeEntityTypeAppModuleId;

    /**
     * 应用模块名称
     */
    private String authorizeEntityTypeAppModuleName;

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

    public String getAuthorizeEntityTypeId() {
        return authorizeEntityTypeId;
    }

    public void setAuthorizeEntityTypeId(String authorizeEntityTypeId) {
        this.authorizeEntityTypeId = authorizeEntityTypeId;
    }

    public String getAuthorizeEntityTypeEntityClassName() {
        return authorizeEntityTypeEntityClassName;
    }

    public void setAuthorizeEntityTypeEntityClassName(String authorizeEntityTypeEntityClassName) {
        this.authorizeEntityTypeEntityClassName = authorizeEntityTypeEntityClassName;
    }

    public String getAuthorizeEntityTypeName() {
        return authorizeEntityTypeName;
    }

    public void setAuthorizeEntityTypeName(String authorizeEntityTypeName) {
        this.authorizeEntityTypeName = authorizeEntityTypeName;
    }

    public Boolean getAuthorizeEntityTypeBeTree() {
        return authorizeEntityTypeBeTree;
    }

    public void setAuthorizeEntityTypeBeTree(Boolean authorizeEntityTypeBeTree) {
        this.authorizeEntityTypeBeTree = authorizeEntityTypeBeTree;
    }

    public String getFeatureId() {
        return featureId;
    }

    public void setFeatureId(String featureId) {
        this.featureId = featureId;
    }

    public String getFeatureCode() {
        return featureCode;
    }

    public void setFeatureCode(String featureCode) {
        this.featureCode = featureCode;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getApiBaseAddress() {
        return apiBaseAddress;
    }

    public void setApiBaseAddress(String apiBaseAddress) {
        this.apiBaseAddress = apiBaseAddress;
    }

    public String getApiPath() {
        return apiPath;
    }

    public void setApiPath(String apiPath) {
        this.apiPath = apiPath;
    }

    public String getAuthorizeEntityTypeAppModuleId() {
        return authorizeEntityTypeAppModuleId;
    }

    public void setAuthorizeEntityTypeAppModuleId(String authorizeEntityTypeAppModuleId) {
        this.authorizeEntityTypeAppModuleId = authorizeEntityTypeAppModuleId;
    }

    public String getAuthorizeEntityTypeAppModuleName() {
        return authorizeEntityTypeAppModuleName;
    }

    public void setAuthorizeEntityTypeAppModuleName(String authorizeEntityTypeAppModuleName) {
        this.authorizeEntityTypeAppModuleName = authorizeEntityTypeAppModuleName;
    }
}
