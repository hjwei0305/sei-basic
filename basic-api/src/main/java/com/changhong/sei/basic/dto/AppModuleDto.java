package com.changhong.sei.basic.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * <strong>实现功能:</strong>
 * <p>应用模块DTO</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2019-12-23 15:56
 */
@ApiModel(description = "应用模块DTO")
public class AppModuleDto extends BaseEntityDto {
    /**
     * 代码
     */
    @NotBlank
    @Size(max = 20)
    private String code;

    /**
     * 名称
     */
    @NotBlank
    @Size(max = 30)
    private String name;

    /**
     * 备注
     */
    private String remark;


    /**
     * web基地址
     */
    private String webBaseAddress;

    /**
     * api基地址
     */
    private String apiBaseAddress;

    /**
     * 排序号
     */
    @NotNull
    @Min(1)
    private Integer rank;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getWebBaseAddress() {
        return webBaseAddress;
    }

    public void setWebBaseAddress(String webBaseAddress) {
        this.webBaseAddress = webBaseAddress;
    }

    public String getApiBaseAddress() {
        return apiBaseAddress;
    }

    public void setApiBaseAddress(String apiBaseAddress) {
        this.apiBaseAddress = apiBaseAddress;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}
