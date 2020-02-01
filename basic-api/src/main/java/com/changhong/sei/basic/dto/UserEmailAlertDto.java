package com.changhong.sei.basic.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 实现功能: 用户邮件提醒DTO
 *
 * @author 王锦光 wangjg
 * @version 2020-01-22 10:23
 */
@ApiModel(description = "用户邮件提醒DTO")
public class UserEmailAlertDto extends BaseEntityDto {
    /**
     * 用户Id
     */
    @NotBlank
    @Size(max = 36)
    @ApiModelProperty(value = "用户Id(max = 36)", required = true)
    private String userId;

    /**
     * 待办工作数量
     */
    @Min(0)
    @ApiModelProperty(value = "待办工作数量")
    private Integer toDoAmount=0;

    /**
     * 间隔时间（小时）
     */
    @Min(0)
    @ApiModelProperty(value = "间隔时间（小时）")
    private Integer hours=0;

    /**
     * 最后提醒时间
     */
    @ApiModelProperty(value = "最后提醒时间")
    private Date lastTime;

    /**
     * 租户代码
     */
    @ApiModelProperty(value = "租户代码")
    private String tenantCode;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getToDoAmount() {
        return toDoAmount;
    }

    public void setToDoAmount(Integer toDoAmount) {
        this.toDoAmount = toDoAmount;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }
}
