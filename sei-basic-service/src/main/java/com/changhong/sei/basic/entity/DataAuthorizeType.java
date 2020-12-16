package com.changhong.sei.basic.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.ICodeUnique;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：数据权限类型
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017-05-31 9:34      王锦光(wangj)                新建
 * <p/>
 * *************************************************************************************************
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "data_authorize_type")
@DynamicUpdate
@DynamicInsert
public class DataAuthorizeType extends BaseAuditableEntity implements ICodeUnique {
    private static final long serialVersionUID = -7300782337397315866L;
    /**
     * 代码
     */
    @Column(name = "code", length = 50, nullable = false, unique = true)
    private String code;

    /**
     * 名称
     */
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    /**
     * 权限对象类型Id
     */
    @Column(name = "authorize_entity_type_id", length = 36, nullable = false)
    private String authorizeEntityTypeId;

    /**
     * 权限对象类型
     */
    @ManyToOne
    @JoinColumn(name = "authorize_entity_type_id", nullable = false, insertable = false, updatable = false)
    private AuthorizeEntityType authorizeEntityType;

    /**
     * 功能码（界定数据权限适用范围）
     */
    @Column(name = "feature_code", length = 36)
    private String featureCode;

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

    public String getAuthorizeEntityTypeId() {
        return authorizeEntityTypeId;
    }

    public void setAuthorizeEntityTypeId(String authorizeEntityTypeId) {
        this.authorizeEntityTypeId = authorizeEntityTypeId;
    }

    public AuthorizeEntityType getAuthorizeEntityType() {
        return authorizeEntityType;
    }

    public void setAuthorizeEntityType(AuthorizeEntityType authorizeEntityType) {
        this.authorizeEntityType = authorizeEntityType;
    }

    public String getFeatureCode() {
        return featureCode;
    }

    public void setFeatureCode(String featureCode) {
        this.featureCode = featureCode;
    }
}
