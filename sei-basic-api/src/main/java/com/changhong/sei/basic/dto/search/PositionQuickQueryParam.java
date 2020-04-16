package com.changhong.sei.basic.dto.search;

import com.changhong.sei.core.dto.serach.QuickQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 实现功能: 岗位的分页查询参数
 *
 * @author 王锦光 wangjg
 * @version 2020-03-30 11:20
 */
@ApiModel("岗位的分页查询参数")
public class PositionQuickQueryParam extends QuickQueryParam {
    /**
     * 组织机构Id
     */
    @ApiModelProperty(value = "组织机构Id")
    private String organizationId;

    /**
     * 包含下级组织节点的岗位
     */
    @NotNull
    @ApiModelProperty(value = "包含下级组织节点的岗位", required = true)
    private Boolean includeSubNode = Boolean.FALSE;

    /**
     * 需要排除用户的功能角色Id
     */
    @ApiModelProperty(value = "需要排除用户的功能角色Id")
    private String excludeFeatureRoleId;

    /**
     * 需要排除用户的数据角色Id
     */
    @ApiModelProperty(value = "需要排除用户的数据角色Id")
    private String excludeDataRoleId;

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

    public String getExcludeFeatureRoleId() {
        return excludeFeatureRoleId;
    }

    public void setExcludeFeatureRoleId(String excludeFeatureRoleId) {
        this.excludeFeatureRoleId = excludeFeatureRoleId;
    }

    public String getExcludeDataRoleId() {
        return excludeDataRoleId;
    }

    public void setExcludeDataRoleId(String excludeDataRoleId) {
        this.excludeDataRoleId = excludeDataRoleId;
    }
}
