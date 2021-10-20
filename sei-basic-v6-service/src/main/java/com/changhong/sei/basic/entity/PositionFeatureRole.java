package com.changhong.sei.basic.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.RelationEntity;
import com.changhong.sei.util.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：岗位分配功能角色
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017-05-04 11:11      王锦光(wangj)                新建
 * <p/>
 * *************************************************************************************************
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "position_feature_role")
@DynamicInsert
@DynamicUpdate
public class PositionFeatureRole extends BaseAuditableEntity implements RelationEntity<Position, FeatureRole> {
    /**
     * 岗位
     */
    @ManyToOne
    @JoinColumn(name = "position_id",nullable = false)
    private Position parent;
    /**
     * 功能角色
     */
    @ManyToOne
    @JoinColumn(name = "feature_role_id",nullable = false)
    private FeatureRole child;
    /**
     * 有效起始日期
     */
    @JsonFormat(timezone = DateUtils.DEFAULT_TIMEZONE, pattern = DateUtils.DEFAULT_DATE_FORMAT)
    @Temporal(TemporalType.DATE)
    @Column(name = "effective_from")
    private Date effectiveFrom;
    /**
     * 有效截至日期
     */
    @JsonFormat(timezone = DateUtils.DEFAULT_TIMEZONE, pattern = DateUtils.DEFAULT_DATE_FORMAT)
    @Temporal(TemporalType.DATE)
    @Column(name = "effective_to")
    private Date effectiveTo;

    /**
     * 父实体
     *
     * @return 父实体
     */
    @Override
    public Position getParent() {
        return parent;
    }

    @Override
    public void setParent(Position parent) {
        this.parent = parent;
    }

    /**
     * 子实体
     *
     * @return 子实体
     */
    @Override
    public FeatureRole getChild() {
        return child;
    }

    @Override
    public void setChild(FeatureRole child) {
        this.child = child;
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
}
