package com.changhong.sei.basic.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.changhong.sei.core.dto.serializer.EnumJsonSerializer;
import com.chonghong.sei.enums.UserAuthorityPolicy;
import com.chonghong.sei.enums.UserType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 实现功能: 用户DTO
 *
 * @author 王锦光 wangjg
 * @version 2020-01-28 10:58
 */
@ApiModel(description = "用户DTO")
public class UserDto extends BaseEntityDto {
    /**
     * 用户姓名
     */
    @NotBlank
    @Size(max = 50)
    private String userName;

    /**
     * 是否冻结
     */
    @NotNull
    private Boolean frozen = Boolean.FALSE;

    /**
     * 租户代码
     */
    private String tenantCode;

    /**
     * 用户类型,0代表员工，1代表合作伙伴
     */
    @NotNull
    @JsonSerialize(using = EnumJsonSerializer.class)
    private UserType userType;

    /**
     * 用户权限策略
     */
    @NotNull
    @JsonSerialize(using = EnumJsonSerializer.class)
    private UserAuthorityPolicy userAuthorityPolicy;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getFrozen() {
        return frozen;
    }

    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
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
