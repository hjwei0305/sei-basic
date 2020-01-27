package com.changhong.sei.basic.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;

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
    private String code;

    /**
     * 名称
     */
    @NotBlank
    @Size(max = 60)
    private String name;

    /**
     * 国家货币代码
     */
    @NotBlank
    @Size(max = 5)
    private String currencyCode;

    /**
     * 国家货币名称
     */
    @NotBlank
    @Size(max = 150)
    private String currencyName;

    /**
     * 是否国外
     */
    @NotNull
    private Boolean toForeign = Boolean.FALSE;

    /**
     * 排序
     */
    @NotNull
    private Integer rank = 0;

    /**
     * 租户代码
     */
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
