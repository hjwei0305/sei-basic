package com.changhong.sei.basic.entity;

import com.changhong.sei.basic.dto.RoleSourceType;
import com.changhong.sei.core.dto.auth.IDataAuthEntity;
import com.changhong.sei.core.dto.serializer.EnumJsonSerializer;
import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.ICodeUnique;
import com.changhong.sei.core.entity.ITenant;
import com.changhong.sei.enums.UserType;
import com.changhong.sei.util.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

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
public class DataRole extends BaseAuditableEntity implements ITenant, ICodeUnique, IDataAuthEntity {
    private static final long serialVersionUID = -5087547715894221158L;
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
     * 授权分配关系Id
     */
    @Transient
    private String relationId;
    /**
     * 分配授权的有效起始日期(传输属性)
     */
    @Transient
    @JsonFormat(timezone = DateUtils.DEFAULT_TIMEZONE, pattern = DateUtils.DEFAULT_DATE_FORMAT)
    private Date effectiveFrom;
    /**
     * 分配授权的有效截至日期(传输属性)
     */
    @Transient
    @JsonFormat(timezone = DateUtils.DEFAULT_TIMEZONE, pattern = DateUtils.DEFAULT_DATE_FORMAT)
    private Date effectiveTo;
    /**
     * 角色来源类型
     */
    @Transient
    @JsonSerialize(using = EnumJsonSerializer.class)
    private RoleSourceType roleSourceType;

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
