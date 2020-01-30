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
    private String email;
    /**
     * 性别 ，true表示男，false表示女
     */
    private Boolean gender = Boolean.FALSE;
    /**
     * 语言代码
     */
    @Size(max = 10)
    private String languageCode;
    /**
     * 身份证号码
     */
    @Size(max = 20)
    private String idCard;
    /**
     * 移动电话
     */
    @Size(max = 20)
    private String mobile;
    /**
     * 记账用户
     */
    @Size(max = 20)
    private String accountor;

    /**
     * 关联的用户Id
     */
    @NotBlank
    @Size(max = 36)
    private String userId;

    /**
     * 关联的用户名称
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
}
