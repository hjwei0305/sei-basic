package com.changhong.sei.basic.entity;

import com.changhong.sei.basic.dto.RoleType;
import com.changhong.sei.core.dto.serializer.EnumJsonSerializer;
import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.ICodeUnique;
import com.changhong.sei.core.entity.ITenant;
import com.chonghong.sei.enums.UserType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：功能角色
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
@Table(name = "feature_role")
@DynamicInsert
@DynamicUpdate
public class FeatureRole extends BaseAuditableEntity implements ITenant, ICodeUnique {
    /**
     * 租户代码
     */
    @Column(name = "tenant_code",length = 10,nullable = false)
    private String tenantCode;
    /**
     * 代码
     */
    @Column(name = "code",unique = true,length = 50,nullable = false)
    private String code;
    /**
     * 名称
     */
    @Column(name = "name",length = 50,nullable = false)
    private String name;
    /**
     * 功能角色组Id
     */
    @Column(name = "feature_role_group_id", length = 36, nullable = false)
    private String featureGroupId;
    /**
     * 功能角色组
     */
    @ManyToOne
    @JoinColumn(name = "feature_role_group_id",nullable = false, insertable = false, updatable = false)
    private FeatureRoleGroup featureRoleGroup;
    /**
     * 角色类型(0-可使用，1-可分配)
     */
    @Enumerated
    @JsonSerialize(using = EnumJsonSerializer.class)
    @Column(name = "role_type",nullable = false)
    private RoleType roleType;
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

    public String getFeatureGroupId() {
        return featureGroupId;
    }

    public void setFeatureGroupId(String featureGroupId) {
        this.featureGroupId = featureGroupId;
    }

    public FeatureRoleGroup getFeatureRoleGroup() {
        return featureRoleGroup;
    }

    public void setFeatureRoleGroup(FeatureRoleGroup featureRoleGroup) {
        this.featureRoleGroup = featureRoleGroup;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
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

}
