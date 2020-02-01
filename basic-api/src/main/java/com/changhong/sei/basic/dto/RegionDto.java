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
 * 实现功能: 行政区域DTO
 *
 * @author 王锦光 wangjg
 * @version 2020-01-29 22:53
 */
@ApiModel(description = "行政区域DTO")
public class RegionDto extends BaseEntityDto {
    /**
     * 代码
     */
    @NotBlank
    @Size(max = 10)
    @ApiModelProperty(value = "代码(max = 10)", required = true)
    private String code;

    /**
     * 名称
     */
    @NotBlank
    @Size(max = 90)
    @ApiModelProperty(value = "名称(max = 90)", required = true)
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
     * 父节点Id
     */
    @ApiModelProperty(value = "父节点Id")
    private String parentId;

    /**
     * 层级
     */
    @NotNull
    @Min(0)
    @ApiModelProperty(value = "层级")
    private Integer nodeLevel = 0;

    /**
     * 国家Id
     */
    @NotBlank
    @Size(max = 36)
    @ApiModelProperty(value = "国家Id(max = 36)", required = true)
    private String countryId;

    /**
     * 关联国家代码
     */
    @ApiModelProperty(value = "关联国家代码")
    private String countryCode;

    /**
     * 关联国家名称
     */
    @ApiModelProperty(value = "关联国家名称")
    private String countryName;

    /**
     * 排序
     */
    @NotNull
    @Min(0)
    @ApiModelProperty(value = "排序")
    private Integer rank = 0;


    /**
     * 缩写
     */
    @Size(max = 30)
    @ApiModelProperty(value = "缩写(max = 30)")
    private String shortName;

    /**
     * 拼音
     */
    @Size(max = 200)
    @ApiModelProperty(value = "拼音(max = 200)")
    private String pinYin;

    /**
     * 租户代码
     */
    @ApiModelProperty(value = "租户代码")
    private String tenantCode;

    /**
     * 子节点列表
     */
    @ApiModelProperty(value = "子节点列表(List)")
    private List<RegionDto> children;

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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getNodeLevel() {
        return nodeLevel;
    }

    public void setNodeLevel(Integer nodeLevel) {
        this.nodeLevel = nodeLevel;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getPinYin() {
        return pinYin;
    }

    public void setPinYin(String pinYin) {
        this.pinYin = pinYin;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public List<RegionDto> getChildren() {
        return children;
    }

    public void setChildren(List<RegionDto> children) {
        this.children = children;
    }
}
