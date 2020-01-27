package com.changhong.sei.basic.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.changhong.sei.core.dto.serializer.EnumJsonSerializer;
import com.chonghong.sei.enums.UserAuthorityPolicy;
import com.chonghong.sei.enums.UserType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 实现功能: 企业用户DTO
 *
 * @author 王锦光 wangjg
 * @version 2020-01-27 15:35
 */
@ApiModel(description = "企业用户DTO")
public class EmployeeDto extends BaseEntityDto {
    /**
     * 员工编号
     */
    @NotBlank
    @Size(max = 10)
    private String code;

    /**
     * 租户代码
     */
    private String tenantCode;

    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户类型,0代表员工，1代表合作伙伴
     */
    @JsonSerialize(using = EnumJsonSerializer.class)
    private UserType userType;

    /**
     * 用户权限策略
     */
    @JsonSerialize(using = EnumJsonSerializer.class)
    private UserAuthorityPolicy userAuthorityPolicy;

    /**
     * 组织机构Id
     */
    private String organizationId;

    /**
     * 组织机构代码
     */
    private String organizationCode;

    /**
     * 组织机构名称
     */
    private String organizationName;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public UserAuthorityPolicy getUserAuthorityPolicy() {
        return userAuthorityPolicy;
    }

    public void setUserAuthorityPolicy(UserAuthorityPolicy userAuthorityPolicy) {
        this.userAuthorityPolicy = userAuthorityPolicy;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
}
