package com.changhong.sei.basic.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：用户配置实体
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间                  变更人                 变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017/4/14 15:45            高银军                  新建
 * <p/>
 * *************************************************************************************************
 */
@Access(AccessType.FIELD)
@Entity
@Table(name = "user_profile")
@DynamicInsert
@DynamicUpdate
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserProfile extends BaseAuditableEntity {
    private static final long serialVersionUID = 2108920435312895964L;
    /**
     * 邮箱
     */
    @Column(name = "email", length = 100)
    private String email;
    /**
     * 性别 ，true表示男，false表示女
     */
    @Column(name = "gender")
    private Boolean gender = Boolean.FALSE;
    /**
     * 语言代码
     */
    @Column(name = "language_code", length = 10)
    private String languageCode = "zh_CN";
    /**
     * 身份证号码
     */
    @Column(name = "id_card", length = 20)
    private String idCard;
    /**
     * 移动电话
     */
    @Column(name = "mobile", length = 20)
    private String mobile;
    /**
     * 记账用户
     */
    @Column(name = "accountor", length = 20)
    private String accountor;

    /**
     * 关联的用户Id
     */
    @Column(name = "user_id", length = 36, nullable = false)
    private String userId;

    /**
     * 关联的用户
     */
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    private User user;

    /**
     * 偏好设置.json格式字符串
     *
     * @see com.changhong.sei.basic.dto.UserPreferenceEnum 枚举名为key
     * ex: {portrait:'data:image/png;base64,XXX', guide:'true'}
     */
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "preferences")
    private String preferences;
    /**
     * 头像临时字段
     */
    @Transient
    private String portrait;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAccountor() {
        return accountor;
    }

    public void setAccountor(String accountor) {
        this.accountor = accountor;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
}
