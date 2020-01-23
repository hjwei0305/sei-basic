package com.changhong.sei.basic.dto;

import java.io.Serializable;
import java.util.List;

/**
 * <strong>实现功能:</strong>
 * <p>岗位复制参数</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2019-11-08 14:19
 */
public class PositionCopyParam implements Serializable {
    /**
     * 源岗位Id
     */
    private String positionId;

    /**
     * 目标组织机构Id清单
     */
    private List<String> targetOrgIds;

    /**
     * 同时复制功能角色
     */
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
