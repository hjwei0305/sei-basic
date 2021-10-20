package com.changhong.sei.basic.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 实现功能: 执行人查询参数
 *
 * @author 王锦光 wangjg
 * @version 2020-02-14 19:53
 */
@ApiModel("执行人查询参数")
public class FindExecutorByPositionParam implements Serializable {
    /**
     * 岗位Id清单
     */
    @ApiModelProperty("岗位Id清单")
    private List<String> positionIds;

    /**
     * 组织机构Id
     */
    @ApiModelProperty("组织机构Id")
    private String orgId;

    /**
     * 组织维度(默认为：[{"id":"0","name":"业务部门"}] )
     */
    @ApiModelProperty("组织维度(默认为：[{\"id\":\"0\",\"name\":\"业务部门\"}] )")
    private List<String> orgDimIds;

    public List<String> getPositionIds() {
        return positionIds;
    }

    public void setPositionIds(List<String> positionIds) {
        this.positionIds = positionIds;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public List<String> getOrgDimIds() {
        return orgDimIds;
    }

    public void setOrgDimIds(List<String> orgDimIds) {
        this.orgDimIds = orgDimIds;
    }
}
