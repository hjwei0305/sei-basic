package com.changhong.sei.basic.dto;

import java.util.Date;
import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;

/**
 * 租户配置(TenantSetting)DTO类
 *
 * @author sei
 * @since 2020-06-29 13:54:30
 */
@ApiModel(description = "租户配置DTO")
public class TenantSettingDto extends BaseEntityDto {
private static final long serialVersionUID = 533710441253115570L;
    /**
     * 租户代码
     */
    @NotBlank
    @Size(max = 10)
    @ApiModelProperty(value = "租户代码(max = 10)", required = true)
    private String code;
    /**
     * logo图片base64
     */
    @ApiModelProperty(value = "logo图片base64")
    private String logo;
    /**
     * 水印
     */
    @ApiModelProperty(value = "水印")
    private String watermark;

    /**
     * 流程引擎代码
     */
    @ApiModelProperty(value = "流程引擎代码")
    private String flowEngineCode;

        
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
        
    public String getWatermark() {
        return watermark;
    }

    public void setWatermark(String watermark) {
        this.watermark = watermark;
    }

    public String getFlowEngineCode() {
        return flowEngineCode;
    }

    public void setFlowEngineCode(String flowEngineCode) {
        this.flowEngineCode = flowEngineCode;
    }
}