package com.changhong.sei.basic.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 实现功能: 专业领域DTO
 *
 * @author 王锦光 wangjg
 * @version 2020-01-29 22:34
 */
@ApiModel(description = "专业领域DTO")
public class ProfessionalDomainDto extends BaseEntityDto {
    /**
     * 代码
     */
    @NotBlank
    @Size(max = 30)
    @ApiModelProperty(value = "代码(max = 30)", required = true)
    private String code;
    /**
     * 名称
     */
    @NotBlank
    @Size(max = 100)
    @ApiModelProperty(value = "名称(max = 100)", required = true)
    private String name;

    /**
     * 代码路径
     */
    @ApiModelProperty(value = "代码路径")
    private String codePath;

    /**
     * 名称路径
     */
    @ApiModelProperty(value = "名称路径")
    private String namePath;

    /**
     * 层级
     */
    @NotNull
    @Min(0)
    @ApiModelProperty(value = "层级")
    private Integer nodeLevel = 0;

    /**
     * 排序号
     */
    @NotNull
    @Min(0)
    @ApiModelProperty(value = "排序号")
    private Integer rank = 0;

    /**
     * 父节点id
     */
    @ApiModelProperty(value = "父节点id")
    private String parentId;
    /**
     * 子节点列表
     */
    @ApiModelProperty(value = "子节点列表(List)")
    private List<ProfessionalDomainDto> children;

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

    public String getCodePath() {
        return codePath;
    }

    public void setCodePath(String codePath) {
        this.codePath = codePath;
    }

    public String getNamePath() {
        return namePath;
    }

    public void setNamePath(String namePath) {
        this.namePath = namePath;
    }

    public Integer getNodeLevel() {
        return nodeLevel;
    }

    public void setNodeLevel(Integer nodeLevel) {
        this.nodeLevel = nodeLevel;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<ProfessionalDomainDto> getChildren() {
        return children;
    }

    public void setChildren(List<ProfessionalDomainDto> children) {
        this.children = children;
    }
}
