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
 * 实现功能：岗位分配数据角色
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
@Table(name = "position_data_role")
@DynamicInsert
@DynamicUpdate
public class PositionDataRole extends BaseAuditableEntity implements RelationEntity<Position, DataRole> {
    /**
     * 岗位
     */
    @ManyToOne
    @JoinColumn(name = "position_id",nullable = false)
    private Position parent;
    /**
     * 数据角色
     */
    @ManyToOne
    @JoinColumn(name = "data_role_id",nullable = false)
    private DataRole child;
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
    public DataRole getChild() {
        return child;
    }

    @Override
    public void setChild(DataRole child) {
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
