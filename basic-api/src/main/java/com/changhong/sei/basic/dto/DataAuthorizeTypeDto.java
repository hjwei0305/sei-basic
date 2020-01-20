package com.changhong.sei.basic.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;

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
    private String code;

    /**
     * 名称
     */
    @NotBlank
    @Size(max = 50)
    private String name;

    /**
     * 权限对象类型Id
     */
    @NotBlank
    @Size(max = 36)
    private String authorizeEntityTypeId;

    /**
     * 权限对象类型代码
     */
    private String authorizeEntityTypeCode;

    /**
     * 权限对象类型名称
     */
    private String authorizeEntityTypeName;

    /**
     * 功能项Id
     */
    @Size(max = 36)
    private String featureId;

    /**
     * 功能项代码
     */
    private String featureCode;

    /**
     * 功能项名称
     */
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

    public String getAuthorizeEntityTypeCode() {
        return authorizeEntityTypeCode;
    }

    public void setAuthorizeEntityTypeCode(String authorizeEntityTypeCode) {
        this.authorizeEntityTypeCode = authorizeEntityTypeCode;
    }

    public String getAuthorizeEntityTypeName() {
        return authorizeEntityTypeName;
    }

    public void setAuthorizeEntityTypeName(String authorizeEntityTypeName) {
        this.authorizeEntityTypeName = authorizeEntityTypeName;
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
