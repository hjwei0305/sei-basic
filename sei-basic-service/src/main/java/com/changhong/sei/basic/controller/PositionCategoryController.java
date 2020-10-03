package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.PositionCategoryApi;
import com.changhong.sei.basic.dto.PositionCategoryDto;
import com.changhong.sei.basic.entity.PositionCategory;
import com.changhong.sei.basic.service.PositionCategoryService;
import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 实现功能: 岗位类别API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-29 9:56
 */
@RestController
@Api(value = "PositionCategoryApi", tags = "岗位类别API服务实现")
@RequestMapping(path = "positionCategory", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PositionCategoryController extends BaseEntityController<PositionCategory, PositionCategoryDto>
        implements PositionCategoryApi {
    @Autowired
    private PositionCategoryService service;

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
