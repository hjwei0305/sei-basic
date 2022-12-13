package com.changhong.sei.basic.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * (EssEmployeePosition)DTO类
 *
 * @author sei
 * @since 2022-11-15 18:17:48
 */
@ApiModel(description = "DTO")
public class EssEmployeePositionDto extends BaseEntityDto {
    private static final long serialVersionUID = -28867837472474770L;
    /**
     * 人事部门编码
     */
    @ApiModelProperty(value = "人事部门编码")
    private String deptCode;
    /**
     * 财务主管工号
     */
    @ApiModelProperty(value = "财务主管工号")
    private String treasurer;
    /**
     * 资产管理员工号
     */
    @ApiModelProperty(value = "资产管理员工号")
    private String assetManager;
    /**
     * 模具管理员工号
     */
    @ApiModelProperty(value = "模具管理员工号")
    private String moldManager;


    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getTreasurer() {
        return treasurer;
    }

    public void setTreasurer(String treasurer) {
        this.treasurer = treasurer;
    }

    public String getAssetManager() {
        return assetManager;
    }

    public void setAssetManager(String assetManager) {
        this.assetManager = assetManager;
    }

    public String getMoldManager() {
        return moldManager;
    }

    public void setMoldManager(String moldManager) {
        this.moldManager = moldManager;
    }

}
