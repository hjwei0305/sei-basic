package com.changhong.sei.basic.dto;

import com.changhong.sei.core.dto.serializer.EnumJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:xiaogang.su@changhong.com">粟小刚</a>
 * @description 实现功能:授权树形VO
 * @date 2019/03/19 11:18
 */
public class AuthTreeVo implements Serializable {

    /**
     * id
     */
    private String id;
    /**
     * 功能项代码
     */
    private String code;

    /**
     * 功能项名称
     */
    private String name;

    /**
     * 资源
     */
    private String url;


    /**
     * 功能项类型：0：操作(Operate),1：业务(Business),2:页面(Page)
     */
    @JsonSerialize(using = EnumJsonSerializer.class)
    private FeatureType featureType;

    /**
     * 树层级
     */
    private Integer nodeLevel;

    /**
     * 是否已勾选
     */
    private Boolean assigned = Boolean.FALSE;
    /**
     * 子节点列表
     */
    private List<AuthTreeVo> children;

    /**
     * 构造函数
     *
     * @param feature 功能项
     */
    public AuthTreeVo(FeatureDto feature) {
        this.id = feature.getId();
        this.code = feature.getCode();
        this.name = feature.getName();
        this.url = feature.getUrl();
        this.featureType = feature.getFeatureType();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public FeatureType getFeatureType() {
        return featureType;
    }

    public void setFeatureType(FeatureType featureType) {
        this.featureType = featureType;
    }

    public Integer getNodeLevel() {
        return nodeLevel;
    }

    public void setNodeLevel(Integer nodeLevel) {
        this.nodeLevel = nodeLevel;
    }

    public List<AuthTreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<AuthTreeVo> children) {
        this.children = children;
    }

    public Boolean getAssigned() {
        return assigned;
    }

    public void setAssigned(Boolean assigned) {
        this.assigned = assigned;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
