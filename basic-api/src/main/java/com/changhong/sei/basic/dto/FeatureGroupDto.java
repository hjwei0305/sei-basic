package com.changhong.sei.basic.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 实现功能: 功能项组DTO
 *
 * @author 王锦光 wangjg
 * @version 2020-01-19 16:05
 */
@ApiModel(description = "功能项组DTO")
public class FeatureGroupDto extends BaseEntityDto {
    /**
     * 代码
     */
    @NotBlank
    @Size(max = 30)
    @ApiModelProperty(value = "代码(max = 30)", required = true)
    private String code;

    /**
     * 名称
     */
    @NotBlank
    @Size(max = 30)
    @ApiModelProperty(value = "名称(max = 30)", required = true)
    private String name;

    /**
     * 应用模块Id
     */
    @NotBlank
    @Size(max = 36)
    @ApiModelProperty(value = "应用模块Id(max = 36)", required = true)
    private String appModuleId;

    /**
     * 应用模块代码
     */
    @ApiModelProperty(value = "应用模块代码")
    private String appModuleCode;

    /**
     * 应用模块名称
     */
    @ApiModelProperty(value = "应用模块名称")
    private String appModuleName;

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
}
