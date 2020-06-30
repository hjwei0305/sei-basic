package com.changhong.sei.basic.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.changhong.sei.core.dto.serializer.EnumJsonSerializer;
import com.changhong.sei.enums.UserAuthorityPolicy;
import com.changhong.sei.enums.UserType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 实现功能: 用户配置DTO
 *
 * @author 王锦光 wangjg
 * @version 2020-01-30 9:41
 */
@ApiModel(description = "用户配置DTO")
public class UserProfileDto extends BaseEntityDto {
    /**
     * 邮箱
     */
    @Size(max = 100)
    @ApiModelProperty(value = "邮箱(max = 100)")
    private String email;
    /**
     * 性别 ，true表示男，false表示女
     */
    @ApiModelProperty(value = "性别", required = true)
    private Boolean gender = Boolean.FALSE;
    /**
     * 语言代码
     */
    @Size(max = 10)
    @ApiModelProperty(value = "语言代码(max = 10)")
    private String languageCode;
    /**
     * 身份证号码
     */
    @Size(max = 20)
    @ApiModelProperty(value = "身份证号码(max = 20)")
    private String idCard;
    /**
     * 移动电话
     */
    @Size(max = 20)
    @ApiModelProperty(value = "移动电话(max = 20)")
    private String mobile;
    /**
     * 记账用户
     */
    @Size(max = 20)
    @ApiModelProperty(value = "记账用户(max = 20)")
    private String accountor;

    /**
     * 关联的用户Id
     */
    @NotBlank
    @Size(max = 36)
    @ApiModelProperty(value = "关联的用户Id(max = 36)", required = true)
    private String userId;

    /**
     * 关联的用户名称
     */
    @ApiModelProperty(value = "关联的用户名称")
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
     * 企业员工编号
     */
    @ApiModelProperty(value = "企业员工编号")
    private String employeeCode;

    /**
     * 员工组织机构名称
     */
    @ApiModelProperty(value = "员工组织机构名称")
    private String organizationName;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String portrait;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAccountor() {
        return accountor;
    }

    public void setAccountor(String accountor) {
        this.accountor = accountor;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
}
