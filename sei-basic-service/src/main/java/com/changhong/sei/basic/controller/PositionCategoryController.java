package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.PositionCategoryApi;
import com.changhong.sei.basic.dto.PositionCategoryDto;
import com.changhong.sei.basic.entity.PositionCategory;
import com.changhong.sei.basic.service.PositionCategoryService;
import com.changhong.sei.core.controller.DefaultBaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 实现功能: 岗位类别API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-29 9:56
 */
@RestController
@Api(value = "PositionCategoryService", tags = "岗位类别API服务实现")
public class PositionCategoryController implements DefaultBaseEntityController<PositionCategory, PositionCategoryDto>,
        PositionCategoryApi {
    @Autowired
    private PositionCategoryService service;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * 根据岗位类别id列表获取岗位类别
     *
     * @param ids
     */
    @Override
    public ResultData<List<PositionCategoryDto>> findByIds(List<String> ids) {
        return ResultData.success(convertToDtos(service.findByIds(ids)));
    }

    @Override
    public BaseEntityService<PositionCategory> getService() {
        return service;
    }

    @Override
    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    /**
     * 获取数据实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<PositionCategory> getEntityClass() {
        return PositionCategory.class;
    }

    /**
     * 获取传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<PositionCategoryDto> getDtoClass() {
        return PositionCategoryDto.class;
    }

    /**
     * 获取所有业务实体
     *
     * @return 业务实体清单
     */
    @Override
    public ResultData<List<PositionCategoryDto>> findAll() {
        return ResultData.success(convertToDtos(service.findAll()));
    }

    /**
     * 获取所有未冻结的业务实体
     *
     * @return 业务实体清单
     */
    @Override
    public ResultData<List<PositionCategoryDto>> findAllUnfrozen() {
        return ResultData.success(convertToDtos(service.findAllUnfrozen()));
    }
}
