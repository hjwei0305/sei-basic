package com.changhong.sei.basic.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * (EssEmployeePosition)实体类
 *
 * @author sei
 * @since 2022-11-15 18:13:30
 */
@Entity
@Table(name = "ess_employee_position")
@DynamicInsert
@DynamicUpdate
public class EssEmployeePosition extends BaseAuditableEntity implements Serializable {
    private static final long serialVersionUID = -45779775623647520L;

    /**
     * 人事部门编码
     */
    @Column(name = "dept_code")
    private String deptCode;
    /**
     * 财务主管工号
     */
    @Column(name = "treasurer")
    private String treasurer;
    /**
     * 资产管理员工号
     */
    @Column(name = "asset_manager")
    private String assetManager;
    /**
     * 模具管理员工号
     */
    @Column(name = "mold_manager")
    private String moldManager;


    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getTreasurer() {
        return treasurer;
    }

    public void setTreasurer(String treasurer) {
        this.treasurer = treasurer;
    }

    public String getAssetManager() {
        return assetManager;
    }

    public void setAssetManager(String assetManager) {
        this.assetManager = assetManager;
    }

    public String getMoldManager() {
        return moldManager;
    }

    public void setMoldManager(String moldManager) {
        this.moldManager = moldManager;
    }

}
