package com.changhong.sei.basic.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 实现功能: 数据字典DTO
 *
 * @author 王锦光 wangjg
 * @version 2020-01-26 19:46
 */
@ApiModel(description = "数据字典DTO")
public class DataDictDto extends BaseEntityDto {
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
    @Size(max = 100)
    private String name;
    /**
     * 是否冻结
     */
    @NotNull
    private Boolean frozen = Boolean.FALSE;
    /**
     * 排序
     */
    @NotNull
    private Integer rank = 0;
    /**
     * 备注
     */
    @Size(max = 100)
    private String remark;

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
