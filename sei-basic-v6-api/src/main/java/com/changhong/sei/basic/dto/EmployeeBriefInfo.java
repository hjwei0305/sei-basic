package com.changhong.sei.basic.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 实现功能: 企业用户简要信息
 *
 * @author 王锦光 wangjg
 * @version 2021-09-01 15:25
 */
@ApiModel("企业用户简要信息")
public class EmployeeBriefInfo extends BaseEntityDto {
    private static final long serialVersionUID = -3042865863494109442L;
    /**
     * 员工编号
     */
    @ApiModelProperty(value = "员工编号")
    private String code;
    /**
     * 员工名称
     */
    @ApiModelProperty(value = "员工名称")
    private String name;
    /**
     * 组织机构名称
     */
    @ApiModelProperty(value = "组织机构名称")
    private String organizationName;

    public EmployeeBriefInfo() {
    }

    public EmployeeBriefInfo(String id, String code, String name, String organizationName) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.organizationName = organizationName;
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

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
}
