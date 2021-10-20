package com.changhong.sei.basic.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.changhong.sei.core.dto.RelationEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 实现功能: 企业员工用户分配岗位DTO
 *
 * @author 王锦光 wangjg
 * @version 2020-01-27 15:04
 */
@ApiModel(description = "企业员工用户分配岗位DTO")
public class EmployeePositionDto extends BaseEntityDto implements RelationEntityDto<EmployeeDto, PositionDto> {
    /**
     * 企业员工用户DTO
     */
    @ApiModelProperty(value = "企业员工用户DTO(EmployeeDto)", required = true)
    private EmployeeDto parent;

    /**
     * 岗位DTO
     */
    @ApiModelProperty(value = "岗位DTO(PositionDto)", required = true)
    private PositionDto child;

    @Override
    public EmployeeDto getParent() {
        return parent;
    }

    @Override
    public void setParent(EmployeeDto parent) {
        this.parent = parent;
    }

    @Override
    public PositionDto getChild() {
        return child;
    }

    @Override
    public void setChild(PositionDto child) {
        this.child = child;
    }
}
