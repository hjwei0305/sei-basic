package com.changhong.sei.basic.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.changhong.sei.core.dto.serializer.EnumJsonSerializer;
import com.changhong.sei.enums.UserAuthorityPolicy;
import com.changhong.sei.enums.UserType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @ApiModelProperty(value = "员工编号(max = 10)", required = true)
    private String code;

    /**
     * 租户代码
     */
    @ApiModelProperty(value = "租户代码")
    private String tenantCode;

    /**
     * 用户名称
     */
    @NotBlank
    @ApiModelProperty(value = "用户名称")
    private String userName;
    /**
     * 用户类型
     */
    @ApiModelProperty(value = "用户类型(enum)")
    @JsonSerialize(using = EnumJsonSerializer.class)
    private UserType userType;

    /**
     * 用户权限策略
     */
    @ApiModelProperty(value = "用户权限策略(enum)")
    @JsonSerialize(using = EnumJsonSerializer.class)
    private UserAuthorityPolicy userAuthorityPolicy;

    /**
     * 组织机构Id
     */
    @NotBlank
    @Size(max = 36)
    @ApiModelProperty(value = "组织机构Id")
    private String organizationId;

    /**
     * 组织机构代码
     */
    @ApiModelProperty(value = "组织机构代码")
    private String organizationCode;

    /**
     * 组织机构名称
     */
    @ApiModelProperty(value = "组织机构名称")
    private String organizationName;

    /**
     * 组织机构代码路径
     */
    @ApiModelProperty(value = "组织机构代码路径")
    private String organizationCodePath;

    /**
     * 组织机构名称路径
     */
    @ApiModelProperty(value = "组织机构名称路径")
    private String organizationNamePath;

    /**
     * 是否是创建租户管理员
     */
    @ApiModelProperty(value = "是否为创建租户管理员")
    private Boolean createAdmin = Boolean.FALSE;

    /**
     * 是否冻结
     */
    @NotNull
    @ApiModelProperty(value = "是否冻结", required = true)
    private Boolean frozen = Boolean.FALSE;

    /**
     * 邮箱,创建租户管理员时发送邮件
     */
    @ApiModelProperty(value = "邮箱")
    private String email;
    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String mobile;
    /**
     * 性别 ，true表示男，false表示女
     */
    @ApiModelProperty(value = "性别")
    private Boolean gender = Boolean.FALSE;

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

    public Boolean getCreateAdmin() {
        return createAdmin;
    }

    public String getOrganizationCodePath() {
        return organizationCodePath;
    }

    public void setOrganizationCodePath(String organizationCodePath) {
        this.organizationCodePath = organizationCodePath;
    }

    public String getOrganizationNamePath() {
        return organizationNamePath;
    }

    public void setOrganizationNamePath(String organizationNamePath) {
        this.organizationNamePath = organizationNamePath;
    }

    public void setCreateAdmin(Boolean createAdmin) {
        this.createAdmin = createAdmin;
    }

    public Boolean getFrozen() {
        return frozen;
    }

    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }
}
