package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.FeatureGroupApi;
import com.changhong.sei.basic.dto.FeatureGroupDto;
import com.changhong.sei.basic.entity.AppModule;
import com.changhong.sei.basic.entity.FeatureGroup;
import com.changhong.sei.basic.service.AppModuleService;
import com.changhong.sei.basic.service.FeatureGroupService;
import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import io.swagger.annotations.Api;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 实现功能: 功能项组API服务
 *
 * @author 王锦光 wangjg
 * @version 2020-01-19 16:15
 */
@RestController
@Api(value = "FeatureGroupApi", tags = "功能项组API服务")
@RequestMapping(path = "featureGroup", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class FeatureGroupController extends BaseEntityController<FeatureGroup, FeatureGroupDto>
        implements FeatureGroupApi {
    @Autowired
    private FeatureGroupService service;
    @Autowired
    private AppModuleService appModuleService;
    @Override
    public BaseEntityService<FeatureGroup> getService() {
        return service;
    }

    /**
     * 模糊查询
     *
     * @param name
     * @return 实体清单
     */
    @Override
    public ResultData<List<FeatureGroupDto>> findByNameLike(String name) {
        List<FeatureGroup> featureGroups = service.findByNameLike(name);
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
        List<FeatureGroup> featureGroups = service.findByAppModuleId(appModuleId);
        List<FeatureGroupDto> dtos = featureGroups.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    /**
     * 自定义设置Entity转换为DTO的转换器
     */
    @Override
    protected void customConvertToDtoMapper() {
        // 创建自定义映射规则
        PropertyMap<FeatureGroup,FeatureGroupDto> propertyMap = new PropertyMap<FeatureGroup, FeatureGroupDto>() {
            @Override
            protected void configure() {
                // 使用自定义转换规则(明确AppModuleId来自source的appModuleId而不是source.appModule.id)
                map().setAppModuleId(source.getAppModuleId());
            }
        };
        // 添加映射器
        dtoModelMapper.addMappings(propertyMap);
    }

    /**
     * 自定义将数据实体转换成DTO
     *
     * @param entity 业务实体
     * @return DTO
     */
    public static FeatureGroupDto convertToDtoStatic(FeatureGroup entity) {
        if (Objects.isNull(entity)){
            return null;
        }
        // 转换
        return dtoModelMapper.map(entity,FeatureGroupDto.class);
    }

    /**
     * 获取所有业务实体
     *
     * @return 业务实体清单
     */
    @Override
    public ResultData<List<FeatureGroupDto>> findAll() {
        List<FeatureGroupDto> dtos = convertToDtos(service.findAll());
        // 排序:应用模块代码+组代码
        dtos.sort(Comparator.comparing(FeatureGroupDto::getAppModuleCode).thenComparing(FeatureGroupDto::getCode));
        return ResultData.success(dtos);
    }

    /**
     * 获取所有未冻结的业务实体
     *
     * @return 业务实体清单
     */
    @Override
    public ResultData<List<FeatureGroupDto>> findAllUnfrozen() {
        return ResultData.success(convertToDtos(service.findAllUnfrozen()));
    }

    /**
     * 保存业务实体
     *
     * @param dto 业务实体DTO
     * @return 操作结果
     */
    @Override
    public ResultData<FeatureGroupDto> save(FeatureGroupDto dto) {
        ResultData<FeatureGroupDto> resultData = super.save(dto);
        if (resultData.failed()) {
            return resultData;
        }
        FeatureGroupDto groupDto = resultData.getData();
        // 获取应用模块
        AppModule appModule = appModuleService.findOne(groupDto.getAppModuleId());
        groupDto.setAppModuleCode(appModule.getCode());
        groupDto.setAppModuleName(appModule.getName());
        return ResultData.success(groupDto);
    }
}
