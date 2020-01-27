package com.changhong.sei.basic.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.changhong.sei.core.dto.RelationEntityDto;
import io.swagger.annotations.ApiModel;

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
    private EmployeeDto parent;

    /**
     * 岗位DTO
     */
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
