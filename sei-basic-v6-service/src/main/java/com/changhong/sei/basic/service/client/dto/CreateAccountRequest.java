package com.changhong.sei.basic.service.client.dto;

import com.changhong.sei.util.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Objects;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-01-14 21:04
 */
@ApiModel(description = "创建账户")
public class CreateAccountRequest extends AccountInfoDto {
    private static final long serialVersionUID = 2974541194405245535L;

    /**
     * 用户id
     */
    @ApiModelProperty(notes = "用户id", required = true)
    @NotBlank
    private String userId;

    /**
     * 名称
     */
    @ApiModelProperty(notes = "名称", required = true)
    @NotBlank
    private String name;
    /**
     * 冻结
     */
    @ApiModelProperty(notes = "冻结")
    private Boolean frozen = Boolean.FALSE;
    /**
     * 锁定
     */
    @ApiModelProperty(notes = "锁定")
    private Boolean locked = Boolean.FALSE;
    /**
     * 截止有效期
     */
    @ApiModelProperty(notes = "截止有效期", example = "2099-01-14")
    @JsonFormat(timezone = DateUtils.DEFAULT_TIMEZONE, pattern = DateUtils.DEFAULT_DATE_FORMAT)
    private Date accountExpired;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getFrozen() {
        return frozen;
    }

    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Date getAccountExpired() {
        return accountExpired;
    }

    public void setAccountExpired(Date accountExpired) {
        this.accountExpired = accountExpired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CreateAccountRequest response = (CreateAccountRequest) o;
        return Objects.equals(tenantCode, response.tenantCode) &&
                Objects.equals(account, response.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenantCode, account);
    }
}
