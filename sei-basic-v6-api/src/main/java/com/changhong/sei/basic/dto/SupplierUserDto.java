package com.changhong.sei.basic.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 实现功能: 供应商用户DTO
 *
 * @author 王锦光 wangjg
 * @version 2020-01-29 23:25
 */
@ApiModel(description = "供应商用户DTO")
public class SupplierUserDto extends BaseEntityDto {
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
    @Size(max = 100)
    @ApiModelProperty(value = "名称(max = 100)", required = true)
    private String name;
    /**
     * 供应商id
     */
    @Size(max = 36)
    @ApiModelProperty(value = "供应商id(max = 36)")
    private String supplierId;
    /**
     * 申请注册供应商ID
     *
     */
    @Size(max = 36)
    @ApiModelProperty(value = "申请注册供应商Id(max = 36)")
    private String supplierApplyId;
    /**
     * 供应商代码
     */
    @Size(max = 30)
    @ApiModelProperty(value = "供应商代码(max = 30)")
    private String supplierCode;
    /**
     * 供应商名称
     */
    @Size(max = 100)
    @ApiModelProperty(value = "供应商名称(max = 100)")
    private String supplierName;

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

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierApplyId() {
        return supplierApplyId;
    }

    public void setSupplierApplyId(String supplierApplyId) {
        this.supplierApplyId = supplierApplyId;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }
}
