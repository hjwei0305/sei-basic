package com.changhong.sei.basic.service;

import com.changhong.sei.basic.api.PositionCategoryService;
import com.changhong.sei.basic.dto.PositionCategoryDto;
import com.changhong.sei.basic.entity.PositionCategory;
import com.changhong.sei.basic.manager.PositionCategoryManager;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.manager.BaseEntityManager;
import com.changhong.sei.core.service.DefaultBaseEntityService;
import com.changhong.sei.core.service.DefaultFindAllService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 实现功能: 岗位类别API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-29 9:56
 */
@Service
@Api(value = "PositionCategoryService", tags = "岗位类别API服务实现")
public class PositionCategoryServiceImpl implements DefaultBaseEntityService<PositionCategory, PositionCategoryDto>,
        DefaultFindAllService<PositionCategory, PositionCategoryDto>,
        PositionCategoryService {
    @Autowired
    private PositionCategoryManager manager;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * 根据岗位类别id列表获取岗位类别
     *
     * @param ids
     */
    @Override
    public ResultData<List<PositionCategoryDto>> findByIds(List<String> ids) {
        return ResultData.success(convertToDtos(manager.findByIds(ids)));
    }

    @Override
    public BaseEntityManager<PositionCategory> getManager() {
        return manager;
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
}
