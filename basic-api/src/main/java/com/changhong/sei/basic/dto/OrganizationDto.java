package com.changhong.sei.basic.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.changhong.sei.core.dto.auth.IDataAuthTreeEntity;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 实现功能: 组织机构DTO
 *
 * @author 王锦光 wangjg
 * @version 2020-01-20 16:17
 */
@ApiModel(description = "组织机构DTO")
public class OrganizationDto extends BaseEntityDto implements IDataAuthTreeEntity {
    /**
     * 组织机构代码
     */
    @Size(max = 6)
    private String code;

    /**
     * 组织机构名称
     */
    @NotBlank
    @Size(max = 100)
    private String name;

    /**
     * 简称
     */
    private String shortName;

    /**
     * 参考码
     */
    @Size(max = 50)
    private String refCode;

    /**
     * 层级
     */
    @NotNull
    @Min(0)
    private Integer nodeLevel = 0;

    /**
     * 代码路径
     */
    @Size(max = 100)
    private String codePath;

    /**
     * 名称路径
     */
    @Size(max = 1000)
    private String namePath;

    /**
     * 父节点Id
     */
    @Size(max = 36)
    private String parentId;

    /**
     * 排序
     */
    @NotNull
    @Min(0)
    private Integer rank = 0;

    /**
     * 租户代码
     */
    private String tenantCode;

    /**
     * 是否冻结
     */
    @NotNull
    private Boolean frozen = Boolean.FALSE;

    private List<OrganizationDto> children;

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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    public Integer getNodeLevel() {
        return nodeLevel;
    }

    public void setNodeLevel(Integer nodeLevel) {
        this.nodeLevel = nodeLevel;
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

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public Boolean getFrozen() {
        return frozen;
    }

    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

    public List<OrganizationDto> getChildren() {
        return children;
    }

    public void setChildren(List<OrganizationDto> children) {
        this.children = children;
    }
}
