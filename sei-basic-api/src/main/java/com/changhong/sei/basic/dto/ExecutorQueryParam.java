package com.changhong.sei.basic.dto;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.List;

/**
 * 实现功能: FLOW执行人查询参数
 *
 * @author 王锦光 wangjg
 * @version 2020-02-14 19:53
 */
@ApiModel("FLOW执行人查询参数")
public class ExecutorQueryParam implements Serializable {
    /**
     * 公司Id清单
     */
    private List<String> corpIds;

    /**
     * 岗位类别Id清单
     */
    private List<String> postCatIds;

    public List<String> getCorpIds() {
        return corpIds;
    }

    public void setCorpIds(List<String> corpIds) {
        this.corpIds = corpIds;
    }

    public List<String> getPostCatIds() {
        return postCatIds;
    }

    public void setPostCatIds(List<String> postCatIds) {
        this.postCatIds = postCatIds;
    }
}
