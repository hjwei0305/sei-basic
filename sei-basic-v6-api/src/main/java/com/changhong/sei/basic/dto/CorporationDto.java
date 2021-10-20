package com.changhong.sei.basic.dto;

import com.changhong.sei.basic.dto.cust.CorporationCustDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 实现功能: 公司DTO
 */
@ApiModel(description = "公司DTO")
public class CorporationDto extends CorporationCustDto {
    private static final long serialVersionUID = 7327363565908833222L;
    /**
     * 代码
     */
    @NotBlank
    @Size(max = 20)
    @ApiModelProperty(value = "代码(max = 20)", required = true)
    private String code;

    /**
     * 名称
     */
    @NotBlank
    @Size(max = 100)
    @ApiModelProperty(value = "名称(max = 100)", required = true)
    private String name;

    /**
     * 简称
     */
    @ApiModelProperty(value = "简称(max = 100)")
    private String shortName;

    /**
     * 租户代码
     */
    @ApiModelProperty(value = "租户代码")
    private String tenantCode;

    /**
     * ERP公司代码
     */
    @NotBlank
    @Size(max = 10)
    @ApiModelProperty(value = "ERP公司代码(max = 10)", required = true)
    private String erpCode;

    /**
     * 税号
     */
    @Size(max = 50)
    @ApiModelProperty(value = "税号")
    private String taxNo;

    /**
     * 排序
     */
    @NotNull
    @Min(0)
    @ApiModelProperty(value = "排序(min = 0)", required = true)
    private Integer rank = 0;

    /**
     * 冻结标志
     */
    @NotNull
    @ApiModelProperty(value = "冻结标志", required = true)
    private Boolean frozen = Boolean.FALSE;

    /**
     * 本位币货币代码
     */
    @NotBlank
    @Size(max = 5)
    @ApiModelProperty(value = "本位币货币代码(max = 5)", required = true)
    private String baseCurrencyCode;

    /**
     * 本位币货币名称
     */
    @NotBlank
    @Size(max = 40)
    @ApiModelProperty(value = "本位币货币名称(max = 40)", required = true)
    private String baseCurrencyName;

    /**
     * 默认贸易伙伴代码
     */
    @Size(max = 10)
    @ApiModelProperty(value = "默认贸易伙伴代码(max = 10)")
    private String defaultTradePartner;

    /**
     * 关联交易贸易伙伴
     */
    @Size(max = 10)
    @ApiModelProperty(value = "关联交易贸易伙伴(max = 10)")
    private String relatedTradePartner;

    /**
     * 微信用户凭证
     */
    @Size(max = 50)
    @ApiModelProperty(value = "微信用户凭证(max = 50)")
    private String weixinAppid;

    /**
     * 微信用户凭证密钥
     */
    @Size(max = 100)
    @ApiModelProperty(value = "微信用户凭证密钥(max = 100)")
    private String weixinSecret;

    /**
     * 内部供应商代码
     */
    @Size(max = 20)
    @ApiModelProperty(value = "内部供应商代码(max = 20)")
    private String internalSupplier;

    /**
     * 是否为模板公司
     */
    @NotNull
    @ApiModelProperty(value = "是否为模板公司", required = true)
    private Boolean templateSign = Boolean.FALSE;

    /**
     * 组织机构Id
     */
    @ApiModelProperty(value = "组织机构Id")
    private String organizationId;

    /**
     * 组织机构
     */
    @ApiModelProperty(value = "组织机构")
    private OrganizationDto organization;


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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String getErpCode() {
        return erpCode;
    }

    public void setErpCode(String erpCode) {
        this.erpCode = erpCode;
    }

    public String getTaxNo() {
        return taxNo;
    }

    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Boolean getFrozen() {
        return frozen;
    }

    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

    public String getBaseCurrencyCode() {
        return baseCurrencyCode;
    }

    public void setBaseCurrencyCode(String baseCurrencyCode) {
        this.baseCurrencyCode = baseCurrencyCode;
    }

    public String getBaseCurrencyName() {
        return baseCurrencyName;
    }

    public void setBaseCurrencyName(String baseCurrencyName) {
        this.baseCurrencyName = baseCurrencyName;
    }

    public String getDefaultTradePartner() {
        return defaultTradePartner;
    }

    public void setDefaultTradePartner(String defaultTradePartner) {
        this.defaultTradePartner = defaultTradePartner;
    }

    public String getRelatedTradePartner() {
        return relatedTradePartner;
    }

    public void setRelatedTradePartner(String relatedTradePartner) {
        this.relatedTradePartner = relatedTradePartner;
    }

    public String getWeixinAppid() {
        return weixinAppid;
    }

    public void setWeixinAppid(String weixinAppid) {
        this.weixinAppid = weixinAppid;
    }

    public String getWeixinSecret() {
        return weixinSecret;
    }

    public void setWeixinSecret(String weixinSecret) {
        this.weixinSecret = weixinSecret;
    }

    public String getInternalSupplier() {
        return internalSupplier;
    }

    public void setInternalSupplier(String internalSupplier) {
        this.internalSupplier = internalSupplier;
    }

    public Boolean getTemplateSign() {
        return templateSign;
    }

    public void setTemplateSign(Boolean templateSign) {
        this.templateSign = templateSign;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public OrganizationDto getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationDto organization) {
        this.organization = organization;
    }
}
