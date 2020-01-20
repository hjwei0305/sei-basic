package com.changhong.sei.basic.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 实现功能: 权限对象类型DTO
 *
 * @author 王锦光 wangjg
 * @version 2020-01-20 11:14
 */
@ApiModel(description = "权限对象类型DTO")
public class AuthorizeEntityTypeDto extends BaseEntityDto {
    /**
     * 实体类名
     */
    @NotBlank
    @Size(max = 100)
    private String entityClassName;

    /**
     * 名称
     */
    @NotBlank
    @Size(max = 50)
    private String name;

    /**
     * 应用模块Id
     */
    @NotBlank
    @Size(max = 36)
    private String appModuleId;

    /**
     * 应用模块代码
     */
    private String appModuleCode;

    /**
     * 应用模块名称
     */
    private String appModuleName;

    /**
     * 是树形结构
     */
    private Boolean beTree = Boolean.FALSE;

    /**
     * API服务路径
     */
    @NotBlank
    @Size(max = 100)
    private String apiPath;

    public String getEntityClassName() {
        return entityClassName;
    }

    public void setEntityClassName(String entityClassName) {
        this.entityClassName = entityClassName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppModuleId() {
        return appModuleId;
    }

    public void setAppModuleId(String appModuleId) {
        this.appModuleId = appModuleId;
    }

    public String getAppModuleCode() {
        return appModuleCode;
    }

    public void setAppModuleCode(String appModuleCode) {
        this.appModuleCode = appModuleCode;
    }

    public String getAppModuleName() {
        return appModuleName;
    }

    public void setAppModuleName(String appModuleName) {
        this.appModuleName = appModuleName;
    }

    public Boolean getBeTree() {
        return beTree;
    }

    public void setBeTree(Boolean beTree) {
        this.beTree = beTree;
    }

    public String getApiPath() {
        return apiPath;
    }

    public void setApiPath(String apiPath) {
        this.apiPath = apiPath;
    }
}
