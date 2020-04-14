package com.changhong.sei.basic.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.changhong.sei.util.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 实现功能: 授权分配关系有效期
 *
 * @author 王锦光 wangjg
 * @version 2020-04-14 9:46
 */
@ApiModel("授权分配关系有效期")
public class RelationEffective extends BaseEntityDto {
    /**
     * 分配授权的有效起始日期(传输属性)
     */
    @NotNull
    @ApiModelProperty(value = "分配授权的有效起始日期", required = true)
    @JsonFormat(timezone = DateUtils.DEFAULT_TIMEZONE, pattern = DateUtils.DEFAULT_DATE_FORMAT)
    private Date effectiveFrom;
    /**
     * 分配授权的有效截至日期(传输属性)
     */
    @NotNull
    @ApiModelProperty(value = "分配授权的有效截至日期", required = true)
    @JsonFormat(timezone = DateUtils.DEFAULT_TIMEZONE, pattern = DateUtils.DEFAULT_DATE_FORMAT)
    private Date effectiveTo;

    public Date getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(Date effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public Date getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(Date effectiveTo) {
        this.effectiveTo = effectiveTo;
    }
}
