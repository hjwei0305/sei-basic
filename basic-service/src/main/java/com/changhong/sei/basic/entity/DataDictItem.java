package com.changhong.sei.basic.entity;

import com.changhong.sei.core.dto.IRank;
import com.changhong.sei.core.entity.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 实现功能：数据字典
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "data_dict_item")
@DynamicUpdate
@DynamicInsert
public class DataDictItem extends BaseAuditableEntity implements ITenant, ICodeUnique, IFrozen, IRank, IDataDict {
    public static final String DEFAULT_TENANT = "global";
    /**
     * 租户代码
     * 默认租户代码为 global
     */
    @Column(name = "tenant_code", nullable = false)
    private String tenantCode = DEFAULT_TENANT;
    /**
     * 字典分类code
     */
    @Column(name = "dict_category_code", length = 50, nullable = false)
    private String categoryCode;
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
     * 值
     */
    @Column(name = "dict_value", length = 100, nullable = false)
    private String value;
    /**
     * 值名称
     */
    @Column(name = "dict_value_name", length = 100, nullable = false)
    private String valueName;
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
    @Column(name = "remark")
    private String remark;

    @Override
    public String getTenantCode() {
        return tenantCode;
    }

    @Override
    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
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

    public String getValue() {
        if (value == null || value.length() == 0) {
            value = getCode();
        }
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueName() {
        if (valueName == null || valueName.length() == 0) {
            valueName = getName();
        }
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
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
