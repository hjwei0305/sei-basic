package com.changhong.sei.basic.entity;

import com.changhong.sei.core.dto.serializer.EnumJsonSerializer;
import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.ICodeUnique;
import com.changhong.sei.core.entity.ITenant;
import com.changhong.sei.enums.UserType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：数据角色
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017-05-04 10:56      王锦光(wangj)                新建
 * <p/>
 * *************************************************************************************************
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "data_role")
@DynamicUpdate
@DynamicInsert
public class DataRole extends BaseAuditableEntity implements ITenant, ICodeUnique {
    /**
     * 租户代码
     */
    @Column(name = "tenant_code", length = 10, nullable = false)
    private String tenantCode;
    /**
     * 代码
     */
    @Column(name = "code", unique = true, length = 50, nullable = false)
    private String code;
    /**
     * 名称
     */
    @Column(name = "name", length = 50, nullable = false)
    private String name;
    /**
     * 数据角色组Id
     */
    @Column(name = "data_role_group_id", length = 36, nullable = false)
    private String dataRoleGroupId;
    /**
     * 数据角色组
     */
    @ManyToOne
    @JoinColumn(name = "data_role_group_id", nullable = false, insertable = false, updatable = false)
    private DataRoleGroup dataRoleGroup;
    /**
     * 公共角色的用户类型
     */
    @Enumerated
    @JsonSerialize(using = EnumJsonSerializer.class)
    @Column(name = "public_user_type")
    private UserType publicUserType;
    /**
     * 公共角色的组织机构Id
     */
    @Column(name = "public_org_id", length = 36)
    private String publicOrgId;
    /**
     * 公共角色的组织机构
     */
    @ManyToOne
    @JoinColumn(name = "public_org_id", insertable = false, updatable = false)
    private Organization publicOrg;
    /**
     * 用户类型描述
     */
    @Transient
    private String publicUserTypeRemark;

    @Override
    public String getTenantCode() {
        return tenantCode;
    }

    @Override
    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
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

    public DataRoleGroup getDataRoleGroup() {
        return dataRoleGroup;
    }

    public void setDataRoleGroup(DataRoleGroup dataRoleGroup) {
        this.dataRoleGroup = dataRoleGroup;
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

    public Organization getPublicOrg() {
        return publicOrg;
    }

    public void setPublicOrg(Organization publicOrg) {
        this.publicOrg = publicOrg;
    }

    public String getPublicUserTypeRemark() {
        return publicUserTypeRemark;
    }

    public void setPublicUserTypeRemark(String publicUserTypeRemark) {
        this.publicUserTypeRemark = publicUserTypeRemark;
    }

}
