package com.changhong.sei.basic.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 实现功能: 功能角色组DTO
 *
 * @author 王锦光 wangjg
 * @version 2020-01-28 10:48
 */
@ApiModel(description = "功能角色组DTO")
public class FeatureRoleGroupDto extends BaseEntityDto {
    /**
     * 租户代码
     */
    private String tenantCode;
    /**
     * 代码
     */
    @NotBlank
    @Size(max = 20)
    private String code;
    /**
     * 名称
     */
    @NotBlank
    @Size(max = 50)
    private String name;

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

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
}
