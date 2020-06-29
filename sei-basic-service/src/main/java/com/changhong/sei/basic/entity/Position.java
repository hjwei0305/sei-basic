package com.changhong.sei.basic.entity;

import com.changhong.sei.core.dto.annotation.DataHistory;
import com.changhong.sei.core.dto.annotation.EnableDataHistory;
import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.ICodeUnique;
import com.changhong.sei.core.entity.ITenant;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：岗位实体
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间                  变更人                 变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017/4/17 11:02            高银军                  新建
 * <p/>
 * *************************************************************************************************
 */
@Access(AccessType.FIELD)
@Entity
@Table(name = "position_")
@DynamicInsert
@DynamicUpdate
@EnableDataHistory(name = "岗位")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Position extends BaseAuditableEntity
        implements ITenant, ICodeUnique {
    public static final String POSITION_CODE = "code";
    public static final String POSITION_CATEGORY_ID = "positionCategory.id";
    private static final long serialVersionUID = -5509706339136699175L;
    /**
     * 代码
     */
    @Column(name = "code",unique = true, length = 8, nullable = false)
    private String code;
    /**
     * 名称
     */
    @DataHistory(name = "名称")
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    /**
     * 租户代码
     */
    @Column(name = "tenant_code", length = 10, nullable = false)
    private String tenantCode;

    /**
     * 组织机构Id
     */
    @Column(name = "organization_id", length = 36, nullable = false)
    private String organizationId;

    /**
     * 组织机构
     */
    @ManyToOne
    @JoinColumn(name = "organization_Id",nullable = false, insertable = false, updatable = false)
    private Organization organization;

    /**
     * 岗位类别Id
     */
    @Column(name = "category_id", length = 36, nullable = false)
    @DataHistory(name = "岗位类别Id")
    private String positionCategoryId;

    /**
     * 岗位类别
     */
    @ManyToOne
    @JoinColumn(name = "category_id",nullable = false, insertable = false, updatable = false)
    @DataHistory(name = "岗位类别")
    private PositionCategory positionCategory;

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

    public String getPositionCategoryId() {
        return positionCategoryId;
    }

    public void setPositionCategoryId(String positionCategoryId) {
        this.positionCategoryId = positionCategoryId;
    }

    public PositionCategory getPositionCategory() {
        return positionCategory;
    }

    public void setPositionCategory(PositionCategory positionCategory) {
        this.positionCategory = positionCategory;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

}
