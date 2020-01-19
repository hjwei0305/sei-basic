package com.changhong.sei.basic.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 实现功能: 租户DTO
 *
 * @author 王锦光 wangjg
 * @version 2020-01-19 14:47
 */
@ApiModel(description = "租户DTO")
public class TenantDto extends BaseEntityDto {
    /**
     * 租户代码
     */
    @NotBlank
    @Size(max = 10)
    private String code;
    /**
     * 租户名称
     */
    @NotBlank
    @Size(max = 200)
    private String name;

    /**
     * 是否冻结，0代表未冻结，1代表冻结
     */
    private Boolean frozen = Boolean.FALSE;

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
}
