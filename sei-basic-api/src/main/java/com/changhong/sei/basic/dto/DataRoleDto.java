package com.changhong.sei.basic.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.changhong.sei.core.dto.serializer.EnumJsonSerializer;
import com.changhong.sei.enums.UserType;
import com.changhong.sei.util.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 实现功能: 数据角色DTO
 *
 * @author 王锦光 wangjg
 * @version 2020-01-21 8:34
 */
@ApiModel(description = "数据角色DTO")
public class DataRoleDto extends BaseEntityDto {
    /**
     * 租户代码
     */
    @Size(max = 10)
    @ApiModelProperty(value = "租户代码")
    private String tenantCode;
    /**
     * 代码
     */
    @NotBlank
    @Size(max = 50)
    @ApiModelProperty(value = "代码(max = 50)", required = true)
    private String code;
    /**
     * 名称
     */
    @NotBlank
    @Size(max = 50)
    @ApiModelProperty(value = "名称(max = 50)", required = true)
    private String name;
    /**
     * 数据角色组Id
     */
    @NotBlank
    @Size(max = 36)
    @ApiModelProperty(value = "数据角色组Id(max = 36)", required = true)
    private String dataRoleGroupId;
    /**
     * 数据角色组代码
     */
    @ApiModelProperty(value = "数据角色组代码")
    private String dataRoleGroupCode;
    /**
     * 数据角色组名称
     */
    @ApiModelProperty(value = "数据角色组名称")
    private String dataRoleGroupName;
    /**
     * 公共角色的用户类型
     */
    @ApiModelProperty(value = "公共角色的用户类型")
    @JsonSerialize(using = EnumJsonSerializer.class)
    private UserType publicUserType;
    /**
     * 公共角色的组织机构Id
     */
    @Size(max = 36)
    @ApiModelProperty(value = "公共角色的组织机构Id")
    private String publicOrgId;
    /**
     * 公共角色的组织机构代码
     */
    @ApiModelProperty(value = "公共角色的组织机构代码")
    private String publicOrgCode;
    /**
     * 公共角色的组织机构名称
     */
    @ApiModelProperty(value = "公共角色的组织机构名称")
    private String publicOrgName;

    /**
     * 授权分配关系Id
     */
    @ApiModelProperty("授权分配关系Id")
    private String relationId;
    /**
     * 分配授权的有效起始日期(传输属性)
     */
    @ApiModelProperty("分配授权的有效起始日期")
    @JsonFormat(timezone = DateUtils.DEFAULT_TIMEZONE, pattern = DateUtils.DEFAULT_DATE_FORMAT)
    private Date effectiveFrom;
    /**
     * 分配授权的有效截至日期(传输属性)
     */
    @ApiModelProperty("分配授权的有效截至日期")
    @JsonFormat(timezone = DateUtils.DEFAULT_TIMEZONE, pattern = DateUtils.DEFAULT_DATE_FORMAT)
    private Date effectiveTo;
    /**
     * 角色来源类型
     */
    @ApiModelProperty("角色来源类型")
    @JsonSerialize(using = EnumJsonSerializer.class)
    private RoleSourceType roleSourceType;

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

    public String getDataRoleGroupId() {
        return dataRoleGroupId;
    }

    public void setDataRoleGroupId(String dataRoleGroupId) {
        this.dataRoleGroupId = dataRoleGroupId;
    }

    public String getDataRoleGroupCode() {
        return dataRoleGroupCode;
    }

    public void setDataRoleGroupCode(String dataRoleGroupCode) {
        this.dataRoleGroupCode = dataRoleGroupCode;
    }

    public String getDataRoleGroupName() {
        return dataRoleGroupName;
    }

    public void setDataRoleGroupName(String dataRoleGroupName) {
        this.dataRoleGroupName = dataRoleGroupName;
    }

    public UserType getPublicUserType() {
        return publicUserType;
    }

    public void setPublicUserType(UserType publicUserType) {
        this.publicUserType = publicUserType;
    }

    public String getPublicOrgId() {
        return publicOrgId;
    }

    public void setPublicOrgId(String publicOrgId) {
        this.publicOrgId = publicOrgId;
    }

    public String getPublicOrgCode() {
        return publicOrgCode;
    }

    public void setPublicOrgCode(String publicOrgCode) {
        this.publicOrgCode = publicOrgCode;
    }

    public String getPublicOrgName() {
        return publicOrgName;
    }

    public void setPublicOrgName(String publicOrgName) {
        this.publicOrgName = publicOrgName;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public Date getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(Date effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public Date getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(Date effectiveTo) {
        this.effectiveTo = effectiveTo;
    }

    public RoleSourceType getRoleSourceType() {
        return roleSourceType;
    }

    public void setRoleSourceType(RoleSourceType roleSourceType) {
        this.roleSourceType = roleSourceType;
    }
}
