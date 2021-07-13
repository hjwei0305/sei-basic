package com.changhong.sei.basic.entity;

import com.changhong.sei.basic.entity.cust.EmployeeCust;
import com.changhong.sei.core.entity.ITenant;
import com.changhong.sei.enums.UserAuthorityPolicy;
import com.changhong.sei.enums.UserType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：企业用户
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017/5/5 11:35      秦有宝                     新建
 * <p/>
 * *************************************************************************************************
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "employee")
@DynamicUpdate
@DynamicInsert
public class Employee extends EmployeeCust implements ITenant {
    private static final long serialVersionUID = -8140546611047494378L;
    /**
     * 员工编号
     */
    @Column(name = "code", length = 20, nullable = false)
    private String code;

    /**
     * 租户代码
     */
    @Column(name = "tenant_code", length = 10, nullable = false, unique = true)
    private String tenantCode;

    /**
     * 关联的用户
     */
    @ElementCollection
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    /**
     * 组织机构Id
     */
    @Column(name = "organization_id", length = 36, nullable = false)
    private String organizationId;

    /**
     * 组织机构
     */
    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false, insertable = false, updatable = false)
    private Organization organization;

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return 租户代码
     */
    @Override
    public String getTenantCode() {
        return tenantCode;
    }

    /**
     * 用户姓名
     */
    @Transient
    private String userName;

    /**
     * 用户姓名
     */
    @Transient
    private String password;

    /**
     * 性别 ，true表示男，false表示女
     */
    @Transient
    private Boolean gender = Boolean.FALSE;

    /**
     * 是否冻结
     */
    @Transient
    private boolean frozen;

    /**
     * 是否是创建租户管理员
     */
    @Transient
    private boolean createAdmin;

    /**
     * 邮箱,创建租户管理员时发送邮件
     */
    @Transient
    private String email;
    /**
     * 手机号
     */
    @Transient
    private String mobile;

    /**
     * 用户说明
     */
    @Transient
    private String userRemark;

    /**
     * 用户类型
     */
    @Transient
    private UserType userType;

    /**
     * 用户权限策略
     */
    @Transient
    private UserAuthorityPolicy userAuthorityPolicy;

    /**
     * 设置租户代码
     *
     * @param tenantCode 租户代码
     */
    @Override
    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String getUserName() {
        if (getUser() != null) {
            return getUser().getUserName();
        }
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isCreateAdmin() {
        return createAdmin;
    }

    public void setCreateAdmin(boolean createAdmin) {
        this.createAdmin = createAdmin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isFrozen() {
        if (getUser() != null) {
            return getUser().getFrozen();
        }
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public String getUserRemark() {
        return userRemark;
    }

    public void setUserRemark(String userRemark) {
        this.userRemark = userRemark;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        if (getUser() != null) {
            return getUser().getUserType();
        }
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public UserAuthorityPolicy getUserAuthorityPolicy() {
        if (getUser() != null) {
            return getUser().getUserAuthorityPolicy();
        }
        return userAuthorityPolicy;
    }

    public void setUserAuthorityPolicy(UserAuthorityPolicy userAuthorityPolicy) {
        this.userAuthorityPolicy = userAuthorityPolicy;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }
}
