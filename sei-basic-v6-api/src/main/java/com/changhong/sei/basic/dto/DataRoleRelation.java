package com.changhong.sei.basic.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：数据角色分配关系类
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017-06-01 8:10      王锦光(wangj)                新建
 * <p/>
 * *************************************************************************************************
 */
@ApiModel(description = "数据角色分配关系类输入参数")
public class DataRoleRelation implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 数据角色Id
     */
    @ApiModelProperty(value = "数据角色Id(max = 36)", required = true)
    private String dataRoleId;
    /**
     * 数据权限类型Id
     */
    @ApiModelProperty(value = "数据权限类型Id(max = 36)", required = true)
    private String dataAuthorizeTypeId;
    /**
     * 权限对象实体Id清单
     */
    @ApiModelProperty(value = "权限对象实体Id清单(List)", required = true)
    private List<String> entityIds;

    public String getDataRoleId() {
        return dataRoleId;
    }

    public void setDataRoleId(String dataRoleId) {
        this.dataRoleId = dataRoleId;
    }

    public String getDataAuthorizeTypeId() {
        return dataAuthorizeTypeId;
    }

    public void setDataAuthorizeTypeId(String dataAuthorizeTypeId) {
        this.dataAuthorizeTypeId = dataAuthorizeTypeId;
    }

    public List<String> getEntityIds() {
        return entityIds;
    }

    public void setEntityIds(List<String> entityIds) {
        this.entityIds = entityIds;
    }
}
