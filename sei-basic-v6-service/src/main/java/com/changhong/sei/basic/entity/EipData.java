package com.changhong.sei.basic.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * (EipData)实体类
 *
 * @author sei
 * @since 2022-08-25 18:06:21
 */
@Entity
@Table(name = "eip_data")
@DynamicInsert
@DynamicUpdate
public class EipData extends BaseAuditableEntity implements Serializable {
    private static final long serialVersionUID = 851356577295570230L;

    @Column(name = "dept_code")
    private String deptCode;

    @Column(name = "dept_manager")
    private String deptManager;

    @Column(name = "unit_manager")
    private String unitManager;

    @Column(name = "module_manager")
    private String moduleManager;


    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptManager() {
        return deptManager;
    }

    public void setDeptManager(String deptManager) {
        this.deptManager = deptManager;
    }

    public String getUnitManager() {
        return unitManager;
    }

    public void setUnitManager(String unitManager) {
        this.unitManager = unitManager;
    }

    public String getModuleManager() {
        return moduleManager;
    }

    public void setModuleManager(String moduleManager) {
        this.moduleManager = moduleManager;
    }

}
