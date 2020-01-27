package com.changhong.sei.basic.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.changhong.sei.core.dto.IDataDict;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 实现功能: 数据字典项目DTO
 *
 * @author 王锦光 wangjg
 * @version 2020-01-26 19:50
 */
@ApiModel(description = "数据字典项目DTO")
public class DataDictItemDto extends BaseEntityDto implements IDataDict {
    public static final String DEFAULT_TENANT = "global";
    /**
     * 租户代码
     * 默认租户代码为 global
     */
    private String tenantCode = DEFAULT_TENANT;
    /**
     * 字典分类code
     */
    @NotBlank
    @Size(max = 50)
    private String categoryCode;
    /**
     * 代码
     */
    @NotBlank
    @Size(max = 50)
    private String code;
    /**
     * 名称
     */
    @NotBlank
    @Size(max = 50)
    private String name;
    /**
     * 值
     */
    @NotBlank
    @Size(max = 100)
    private String value;
    /**
     * 值名称
     */
    @NotBlank
    @Size(max = 100)
    private String valueName;
    /**
     * 是否冻结
     */
    @NotNull
    private Boolean frozen = Boolean.FALSE;
    /**
     * 排序
     */
    private Integer rank = 0;
    /**
     * 备注
     */
    private String remark;

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public Boolean getFrozen() {
        return frozen;
    }

    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
