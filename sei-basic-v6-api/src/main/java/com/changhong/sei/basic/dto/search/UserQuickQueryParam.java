package com.changhong.sei.basic.dto.search;

import com.changhong.sei.core.dto.serach.QuickQueryParam;
import com.changhong.sei.core.dto.serializer.EnumJsonSerializer;
import com.changhong.sei.enums.UserType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 实现功能: 平台用户的分页查询参数
 *
 * @author 王锦光 wangjg
 * @version 2020-03-30 11:20
 */
@ApiModel("平台用户的分页查询参数")
public class UserQuickQueryParam extends QuickQueryParam {
    /**
     * 用户类型
     */
    @NotNull
    @ApiModelProperty(value = "用户类型")
    @JsonSerialize(using = EnumJsonSerializer.class)
    private UserType userType;

    /**
     * 需要排除用户的功能角色Id
     */
    @ApiModelProperty(value = "需要排除用户的功能角色Id")
    private String excludeFeatureRoleId;

    /**
     * 需要排除用户的数据角色Id
     */
    @ApiModelProperty(value = "需要排除用户的数据角色Id")
    private String excludeDataRoleId;

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getExcludeFeatureRoleId() {
        return excludeFeatureRoleId;
    }

    public void setExcludeFeatureRoleId(String excludeFeatureRoleId) {
        this.excludeFeatureRoleId = excludeFeatureRoleId;
    }

    public String getExcludeDataRoleId() {
        return excludeDataRoleId;
    }

    public void setExcludeDataRoleId(String excludeDataRoleId) {
        this.excludeDataRoleId = excludeDataRoleId;
    }
}
