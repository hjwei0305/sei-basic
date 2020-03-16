package com.changhong.sei.basic.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.changhong.sei.core.dto.serializer.EnumJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 实现功能: 功能项的树形节点
 *
 * @author 王锦光 wangjg
 * @version 2020-03-10 14:33
 */
@ApiModel("功能项的树形节点")
public class FeatureNode extends BaseEntityDto {
    /**
     * 功能项代码
     */
    @ApiModelProperty("功能项代码")
    private String code;

    /**
     * 功能项名称
     */
    @ApiModelProperty("功能项名称")
    private String name;

    /**
     * 功能项类型
     */
    @ApiModelProperty("功能项类型")
    @JsonSerialize(using = EnumJsonSerializer.class)
    private FeatureType featureType;

    /**
     * 子节点清单
     */
    @ApiModelProperty("子节点清单")
    private List<FeatureNode> children;

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

    public FeatureType getFeatureType() {
        return featureType;
    }

    public void setFeatureType(FeatureType featureType) {
        this.featureType = featureType;
    }

    public List<FeatureNode> getChildren() {
        return children;
    }

    public void setChildren(List<FeatureNode> children) {
        this.children = children;
    }
}
