package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.FeatureApi;
import com.changhong.sei.basic.dto.FeatureDto;
import com.changhong.sei.basic.dto.FeatureType;
import com.changhong.sei.basic.entity.Feature;
import com.changhong.sei.basic.service.FeatureService;
import com.changhong.sei.core.controller.DefaultBaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.service.BaseEntityService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 实现功能: 功能项API服务
 *
 * @author 王锦光 wangjg
 * @version 2020-01-19 21:18
 */
@RestController
@Api(value = "FeatureService", tags = "功能项API服务")
public class FeatureController implements DefaultBaseEntityController<Feature, FeatureDto>,
        FeatureApi {
    @Autowired
    private FeatureService service;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * 根据功能项组id查询功能项
     *
     * @param featureGroupId 功能项组的id
     * @return 查询的结果
     */
    @Override
    public ResultData<List<FeatureDto>> findByFeatureGroupId(String featureGroupId) {
        List<Feature> features = service.findByFeatureGroupId(featureGroupId);
        List<FeatureDto> dtos = features.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    /**
     * 根据功能项组id以及功能项类型查询功能项
     *
     * @param featureGroupId 功能项组的id
     * @param featureTypes   功能项类型清单
     * @return 查询的结果
     */
    @Override
    public ResultData<List<FeatureDto>> findByFeatureGroupAndType(String featureGroupId, List<FeatureType> featureTypes) {
        List<Feature> features = service.findByFeatureGroupAndType(featureGroupId, featureTypes);
        List<FeatureDto> dtos = features.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    /**
     * 根据应用模块id查询功能项
     *
     * @param appModuleId 应用模块id
     * @return 功能项清单
     */
    @Override
    public ResultData<List<FeatureDto>> findByAppModuleId(String appModuleId) {
        List<Feature> features = service.findByAppModuleId(appModuleId);
        List<FeatureDto> dtos = features.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    /**
     * 根据过滤条件获取功能项
     *
     * @param search 过滤条件
     * @return 功能项列表
     */
    @Override
    public ResultData<List<FeatureDto>> findByFilters(Search search) {
        List<Feature> features = service.findByFilters(search);
        List<FeatureDto> dtos = features.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    /**
     * 根据功能项id查询子功能项
     *
     * @param featureId 功能项的id
     * @return 功能项列表
     */
    @Override
    public ResultData<List<FeatureDto>> findChildByFeatureId(String featureId) {
        List<Feature> features = service.findChildByFeatureId(featureId);
        List<FeatureDto> dtos = features.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    @Override
    public BaseEntityService<Feature> getService() {
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
    public Class<Feature> getEntityClass() {
        return Feature.class;
    }

    /**
     * 获取传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<FeatureDto> getDtoClass() {
        return FeatureDto.class;
    }

    /**
     * 将数据实体转换成DTO
     *
     * @param entity 业务实体
     * @return DTO
     */
    @Override
    public FeatureDto convertToDto(Feature entity) {
        return FeatureController.custConvertToDto(entity);
    }

    /**
     * 自定义将数据实体转换成DTO
     *
     * @param entity 业务实体
     * @return DTO
     */
    public static FeatureDto custConvertToDto(Feature entity){
        if (Objects.isNull(entity)){
            return null;
        }
        ModelMapper custMapper = new ModelMapper();
        // 创建自定义映射规则
        PropertyMap<Feature, FeatureDto> propertyMap = new PropertyMap<Feature, FeatureDto>() {
            @Override
            protected void configure() {
                // 自定义转换规则(明确FeatureGroupId来自source的featureGroupId而不是source.featureGroup.id)
                map().setFeatureGroupId(source.getFeatureGroupId());
                map().setAppModuleName(source.getFeatureGroup().getAppModule().getName());
            }
        };
        // 添加映射器
        custMapper.addMappings(propertyMap);
        // 转换
        return custMapper.map(entity, FeatureDto.class);
    }

    /**
     * 分页查询业务实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @Override
    public ResultData<PageResult<FeatureDto>> findByPage(Search search) {
        return convertToDtoPageResult(service.findByPage(search));
    }
}
