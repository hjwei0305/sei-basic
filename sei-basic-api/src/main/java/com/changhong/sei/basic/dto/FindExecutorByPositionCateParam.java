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
public class FindExecutorByPositionCateParam implements Serializable {
    /**
     * 岗位类别Id清单
     */
    @ApiModelProperty("岗位类别Id清单")
    private List<String> postCatIds;

    /**
     * 组织机构Id
     */
    @ApiModelProperty("组织机构Id")
    private String orgId;

    public List<String> getPostCatIds() {
        return postCatIds;
    }

    public void setPostCatIds(List<String> postCatIds) {
        this.postCatIds = postCatIds;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}
