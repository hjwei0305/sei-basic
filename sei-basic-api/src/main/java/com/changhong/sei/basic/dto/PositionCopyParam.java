package com.changhong.sei.basic.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.bouncycastle.cert.dane.DANEEntry;

import java.io.Serializable;
import java.util.List;

/**
 * <strong>实现功能:</strong>
 * <p>岗位复制参数</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2019-11-08 14:19
 */
@ApiModel(description = "岗位复制参数")
public class PositionCopyParam implements Serializable {
    /**
     * 源岗位Id
     */
    @ApiModelProperty(value = "源岗位Id", required = true)
    private String positionId;

    /**
     * 目标组织机构Id清单
     */
    @ApiModelProperty(value = "目标组织机构Id清单", required = true)
    private List<String> targetOrgIds;

    /**
     * 同时复制功能角色
     */
    @ApiModelProperty(value = "同时复制功能角色", required = true)
    private Boolean copyFeatureRole = Boolean.FALSE;

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public List<String> getTargetOrgIds() {
        return targetOrgIds;
    }

    public void setTargetOrgIds(List<String> targetOrgIds) {
        this.targetOrgIds = targetOrgIds;
    }

    public Boolean getCopyFeatureRole() {
        return copyFeatureRole;
    }

    public void setCopyFeatureRole(Boolean copyFeatureRole) {
        this.copyFeatureRole = copyFeatureRole;
    }
}
