package com.changhong.sei.basic.service.client.dto;

import java.io.Serializable;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-01-14 22:05
 */
public class SessionUserResponse implements Serializable {
    private static final long serialVersionUID = 6402761734842712786L;
    /**
     * 用户id，平台唯一
     */
    private String userId;
    /**
     * 用户账号
     */
    private String account;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 租户代码
     */
    private String tenantCode;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

}
