package com.changhong.sei.basic.service;

import com.changhong.sei.basic.api.FeatureGroupService;
import com.changhong.sei.basic.dto.FeatureGroupDto;
import com.changhong.sei.basic.entity.FeatureGroup;
import com.changhong.sei.basic.manager.FeatureGroupManager;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.manager.BaseEntityManager;
import com.changhong.sei.core.service.DefaultBaseEntityService;
import com.changhong.sei.core.service.DefaultFindAllService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 实现功能: 功能项组API服务
 *
 * @author 王锦光 wangjg
 * @version 2020-01-19 16:15
 */
@Service
@Api(value = "FeatureGroupService", tags = "功能项组API服务")
public class FeatureGroupServiceImpl implements DefaultBaseEntityService<FeatureGroup, FeatureGroupDto>,
        DefaultFindAllService<FeatureGroup, FeatureGroupDto>,
        FeatureGroupService {
    @Autowired
    private FeatureGroupManager manager;
    @Autowired
    private ModelMapper modelMapper;
    /**
     * 模糊查询
     *
     * @param name
     * @return 实体清单
     */
    @Override
    public ResultData<List<FeatureGroupDto>> findByNameLike(String name) {
        List<FeatureGroup> featureGroups = manager.findByNameLike(name);
        List<FeatureGroupDto> dtos = featureGroups.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    /**
     * 根据应用模块id查询功能项组
     *
     * @param appModuleId 应用模块id
     * @return 功能项组
     */
    @Override
    public ResultData<List<FeatureGroupDto>> findByAppModuleId(String appModuleId) {
        List<FeatureGroup> featureGroups = manager.findByAppModuleId(appModuleId);
        List<FeatureGroupDto> dtos = featureGroups.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    @Override
    public BaseEntityManager<FeatureGroup> getManager() {
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
    public Class<FeatureGroup> getEntityClass() {
        return FeatureGroup.class;
    }

    /**
     * 获取传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<FeatureGroupDto> getDtoClass() {
        return FeatureGroupDto.class;
    }

    /**
     * 将数据实体转换成DTO
     *
     * @param entity 业务实体
     * @return DTO
     */
    @Override
    public FeatureGroupDto convertToDto(FeatureGroup entity) {
        return FeatureGroupServiceImpl.custConvertToDto(entity);
    }

    /**
     * 自定义将数据实体转换成DTO
     *
     * @param entity 业务实体
     * @return DTO
     */
    public static FeatureGroupDto custConvertToDto(FeatureGroup entity) {
        if (Objects.isNull(entity)){
            return null;
        }
        ModelMapper custMapper = new ModelMapper();
        // 创建自定义映射规则
        PropertyMap<FeatureGroup,FeatureGroupDto> propertyMap = new PropertyMap<FeatureGroup, FeatureGroupDto>() {
            @Override
            protected void configure() {
                // 使用自定义转换规则(明确AppModuleId来自source的appModuleId而不是source.appModule.id)
                map().setAppModuleId(source.getAppModuleId());
            }
        };
        // 添加映射器
        custMapper.addMappings(propertyMap);
        // 转换
        return custMapper.map(entity,FeatureGroupDto.class);
    }
}
