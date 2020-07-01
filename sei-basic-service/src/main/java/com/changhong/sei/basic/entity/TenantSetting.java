package com.changhong.sei.basic.entity;

import java.util.Date;
import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.ICodeUnique;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 租户配置(TenantSetting)实体类
 *
 * @author sei
 * @since 2020-06-29 13:54:44
 */
@Entity
@Table(name = "tenant_setting")
@DynamicInsert
@DynamicUpdate
public class TenantSetting extends BaseAuditableEntity implements Serializable, ICodeUnique {
private static final long serialVersionUID = -33069859473213493L;
    /**
     * 租户代码
     */
    @Column(name = "code")
    private String code;
    /**
     * logo图片base64
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "logo")
    private String logo;
    /**
     * 水印
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "watermark")
    private String watermark;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
        
    public String getWatermark() {
        return watermark;
    }

    public void setWatermark(String watermark) {
        this.watermark = watermark;
    }

}