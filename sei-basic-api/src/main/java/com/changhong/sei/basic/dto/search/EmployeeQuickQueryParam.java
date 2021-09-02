package com.changhong.sei.basic.dto.search;

import com.changhong.sei.core.dto.serach.QuickQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 实现功能: 企业用户的分页查询参数
 *
 * @author 王锦光 wangjg
 * @version 2020-03-30 11:20
 */
@ApiModel("企业用户的分页查询参数")
public class EmployeeQuickQueryParam extends QuickQueryParam {
    private static final long serialVersionUID = 5275654378932854144L;
    /**
     * 组织机构Id
     */
    @NotBlank
    @ApiModelProperty(value = "组织机构Id", required = true)
    private String organizationId;

    /**
     * 包含下级组织节点的用户
     */
    @NotNull
    @ApiModelProperty(value = "包含下级组织节点的用户", required = true)
    private Boolean includeSubNode = Boolean.FALSE;

    /**
     * 包含冻结的用户
     */
    @NotNull
    @ApiModelProperty(value = "包含冻结的用户", required = true)
    private Boolean includeFrozen = Boolean.FALSE;

    /**
     * 需要排除企业用户的岗位Id
     */
    @ApiModelProperty(value = "需要排除企业用户的岗位Id")
    private String excludePositionId;


    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public Boolean getIncludeSubNode() {
        return includeSubNode;
    }

    public void setIncludeSubNode(Boolean includeSubNode) {
        this.includeSubNode = includeSubNode;
    }

    public Boolean getIncludeFrozen() {
        return includeFrozen;
    }

    public void setIncludeFrozen(Boolean includeFrozen) {
        this.includeFrozen = includeFrozen;
    }

    public String getExcludePositionId() {
        return excludePositionId;
    }

    public void setExcludePositionId(String excludePositionId) {
        this.excludePositionId = excludePositionId;
    }
}
