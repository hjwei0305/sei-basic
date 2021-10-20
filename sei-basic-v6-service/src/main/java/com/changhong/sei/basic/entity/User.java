package com.changhong.sei.basic.entity;

import com.changhong.sei.basic.entity.cust.UserCust;
import com.changhong.sei.core.dto.serializer.EnumJsonSerializer;
import com.changhong.sei.core.entity.IFrozen;
import com.changhong.sei.core.entity.ITenant;
import com.changhong.sei.enums.UserAuthorityPolicy;
import com.changhong.sei.enums.UserType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：用户实体
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017/4/14 9:22        秦有宝                      新建
 * <p/>
 * *************************************************************************************************
 */
@Access(AccessType.FIELD)
@Entity()
@Table(name = "user_")
@DynamicInsert
@DynamicUpdate
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User extends UserCust implements ITenant, IFrozen {

    private static final long serialVersionUID = 1L;
    /**
     * 用户姓名
     */
    @Column(name = "user_name", length = 50, nullable = false)
    private String userName;

    /**
     * 是否冻结
     */
    @Column(name = "frozen", nullable = false)
    private Boolean frozen = Boolean.FALSE;

    /**
     * 租户代码
     */
    @Column(name = "tenant_code", length = 10, nullable = false, unique = true)
    private String tenantCode;

    /**
     * 用户类型
     */
    @Enumerated
    @JsonSerialize(using = EnumJsonSerializer.class)
    @Column(name = "user_type", nullable = false)
    private UserType userType;

    /**
     * 用户权限策略
     */
    @Enumerated
    @JsonSerialize(using = EnumJsonSerializer.class)
    @Column(name = "user_authority_policy", nullable = false)
    private UserAuthorityPolicy userAuthorityPolicy;

    /**
     * 用户主账号
     */
    @Column(name = "account", length = 50)
    private String account;

    /**
     * 备注说明(传输属性)
     */
    @Transient
    private String remark;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getTenantCode() {
        return tenantCode;
    }

    @Override
    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public UserAuthorityPolicy getUserAuthorityPolicy() {
        return userAuthorityPolicy;
    }

    public void setUserAuthorityPolicy(UserAuthorityPolicy userAuthorityPolicy) {
        this.userAuthorityPolicy = userAuthorityPolicy;
    }

    @Override
    public Boolean getFrozen() {
        return frozen;
    }

    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
