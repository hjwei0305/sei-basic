package com.changhong.sei.basic.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.ICodeUnique;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * *************************************************************************************************
 * <br>
 * 实现功能：功能项组Entity定义
 * <br>
 * ------------------------------------------------------------------------------------------------
 * <br>
 * 版本          变更时间             变更人                     变更原因
 * <br>
 * ------------------------------------------------------------------------------------------------
 * <br>
 * 1.0.00     2017/4/19 15:52       余思豆(yusidou)                 新建
 * <br>
 * *************************************************************************************************<br>
 */
@Access(AccessType.FIELD)
@Entity
@Table(name = "feature_group")
@DynamicInsert
@DynamicUpdate
public class FeatureGroup extends BaseAuditableEntity implements ICodeUnique {

    private static final long serialVersionUID = -8462032742317218930L;
    /**
     * 代码
     */
    @Column(length = 30, nullable = false, unique = true)
    private String code;

    /**
     * 名称
     */
    @Column(length = 30, nullable = false)
    private String name;

    /**
     * 应用模块Id
     */
    @Column(name = "app_module_id", length = 36, nullable = false)
    private String appModuleId;

    /**
     * 应用模块
     */
    @ManyToOne
    @JoinColumn(name = "app_module_id", nullable = false, insertable = false, updatable = false)
    private AppModule appModule;

    public String getAppModuleId() {
        return appModuleId;
    }

    public void setAppModuleId(String appModuleId) {
        this.appModuleId = appModuleId;
    }

    public AppModule getAppModule() {
        return appModule;
    }

    public void setAppModule(AppModule appModule) {
        this.appModule = appModule;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
