package com.changhong.sei.basic.service;

import com.changhong.sei.basic.api.DataAuthorizeTypeService;
import com.changhong.sei.basic.dto.DataAuthorizeTypeDto;
import com.changhong.sei.basic.dto.DataAuthorizeTypeVo;
import com.changhong.sei.basic.entity.DataAuthorizeType;
import com.changhong.sei.basic.manager.DataAuthorizeTypeManager;
import com.changhong.sei.core.manager.BaseEntityManager;
import com.changhong.sei.core.service.DefaultBaseEntityService;
import com.changhong.sei.core.service.DefaultFindAllService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 实现功能: 数据权限类型API服务
 *
 * @author 王锦光 wangjg
 * @version 2020-01-20 14:27
 */
@Service
@Api(value = "DataAuthorizeTypeService", tags = "数据权限类型API服务")
public class DataAuthorizeTypeServiceImpl implements DefaultBaseEntityService<DataAuthorizeType, DataAuthorizeTypeDto>,
        DefaultFindAllService<DataAuthorizeType, DataAuthorizeTypeDto>,
        DataAuthorizeTypeService {
    @Autowired
    private DataAuthorizeTypeManager manager;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public BaseEntityManager<DataAuthorizeType> getManager() {
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
    public Class<DataAuthorizeType> getEntityClass() {
        return DataAuthorizeType.class;
    }

    /**
     * 获取传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<DataAuthorizeTypeDto> getDtoClass() {
        return DataAuthorizeTypeDto.class;
    }

    /**
     * 将数据实体转换成DTO
     *
     * @param entity 业务实体
     * @return DTO
     */
    @Override
    public DataAuthorizeTypeDto convertToDto(DataAuthorizeType entity) {
        ModelMapper custMapper = new ModelMapper();
        // 创建自定义映射规则
        PropertyMap<DataAuthorizeType,DataAuthorizeTypeDto> propertyMap = new PropertyMap<DataAuthorizeType,DataAuthorizeTypeDto>() {
            @Override
            protected void configure() {
                // 使用自定义转换规则
                map().setAuthorizeEntityTypeId(source.getAuthorizeEntityTypeId());
                map().setFeatureId(source.getFeatureId());
            }
        };
        // 添加映射器
        custMapper.addMappings(propertyMap);
        // 转换
        return custMapper.map(entity, DataAuthorizeTypeDto.class);
    }

    /**
     * 通过数据角色Id获取数据权限类型（VO）
     *
     * @param roleId 数据角色Id
     * @return 数据权限类型
     */
    @Override
    public List<DataAuthorizeTypeVo> getByDataRole(String roleId) {
        return null;
    }

    /**
     * 通过数据角色Id获取数据权限类型（VO）
     *
     * @param appModuleId
     * @param roleId      数据角色Id
     * @return 数据权限类型
     */
    @Override
    public List<DataAuthorizeTypeVo> getByAppModuleAndDataRole(String appModuleId, String roleId) {
        return null;
    }
}
