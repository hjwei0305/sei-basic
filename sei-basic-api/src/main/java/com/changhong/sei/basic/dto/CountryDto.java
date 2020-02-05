package com.changhong.sei.basic.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 实现功能: 国家DTO
 *
 * @author 王锦光 wangjg
 * @version 2020-01-26 19:34
 */
@ApiModel(description = "国家DTO")
public class CountryDto extends BaseEntityDto {
    /**
     * 代码
     */
    @NotBlank
    @Size(max = 4)
    @ApiModelProperty(value = "代码(max = 4)", required = true)
    private String code;

    /**
     * 名称
     */
    @NotBlank
    @Size(max = 60)
    @ApiModelProperty(value = "名称(max = 60)", required = true)
    private String name;

    /**
     * 国家货币代码
     */
    @NotBlank
    @Size(max = 5)
    @ApiModelProperty(value = "国家货币代码(max = 5)", required = true)
    private String currencyCode;

    /**
     * 国家货币名称
     */
    @NotBlank
    @Size(max = 20)
    @ApiModelProperty(value = "国家货币名称(max = 20)", required = true)
    private String currencyName;

    /**
     * 是否国外
     */
    @NotNull
    @ApiModelProperty(value = "是否国外", required = true)
    private Boolean toForeign = Boolean.FALSE;

    /**
     * 排序
     */
    @NotNull
    @ApiModelProperty(value = "排序", required = true)
    private Integer rank = 0;

    /**
     * 租户代码
     */
    @ApiModelProperty(value = "租户代码")
    private String tenantCode;

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

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public Boolean getToForeign() {
        return toForeign;
    }

    public void setToForeign(Boolean toForeign) {
        this.toForeign = toForeign;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }
}
