package com.changhong.sei.basic.entity;

import com.changhong.sei.core.dto.IRank;
import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.ICodeUnique;
import com.changhong.sei.core.entity.IFrozen;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 实现功能：数据字典
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "data_dict")
@DynamicUpdate
@DynamicInsert
public class DataDict extends BaseAuditableEntity implements ICodeUnique, IFrozen, IRank {
    /**
     * 代码
     */
    @Column(name = "code", unique = true, length = 50, nullable = false)
    private String code;
    /**
     * 名称
     */
    @Column(name = "name", length = 100, nullable = false)
    private String name;
    /**
     * 是否冻结
     */
    @Column(name = "frozen")
    private Boolean frozen = Boolean.FALSE;
    /**
     * 排序
     */
    @Column(name = "rank")
    private Integer rank = 0;
    /**
     * 备注
     */
    @Column(name = "remark", length = 100)
    private String remark;

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

    @Override
    public Boolean getFrozen() {
        return frozen;
    }

    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

    @Override
    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
