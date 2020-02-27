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
    private String authorizeEntityTypeBeTree;

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

    public String getAuthorizeEntityTypeBeTree() {
        return authorizeEntityTypeBeTree;
    }

    public void setAuthorizeEntityTypeBeTree(String authorizeEntityTypeBeTree) {
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
}
