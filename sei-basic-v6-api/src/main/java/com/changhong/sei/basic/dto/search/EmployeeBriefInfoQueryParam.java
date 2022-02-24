package com.changhong.sei.basic.dto.search;

import com.changhong.sei.core.dto.serach.QuickQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 实现功能: 企业用户简要信息查询参数
 *
 * @author 王锦光 wangjg
 * @version 2021-09-02 9:25
 */
@ApiModel("企业用户简要信息查询参数")
public class EmployeeBriefInfoQueryParam extends QuickQueryParam {
    private static final long serialVersionUID = -923374855831302352L;
    /**
     * 包含冻结的用户
     */
    @NotNull
    @ApiModelProperty(value = "包含冻结的用户", required = true)
    private Boolean includeFrozen = Boolean.FALSE;

    /**
     * 公司代码
     */
    @ApiModelProperty(value = "公司代码")
    private String corporationCode;

    public Boolean getIncludeFrozen() {
        return includeFrozen;
    }

    public void setIncludeFrozen(Boolean includeFrozen) {
        this.includeFrozen = includeFrozen;
    }

    public String getCorporationCode() {
        return corporationCode;
    }

    public void setCorporationCode(String corporationCode) {
        this.corporationCode = corporationCode;
    }
}
