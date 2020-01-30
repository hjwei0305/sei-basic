package com.changhong.sei.basic.service;

import com.changhong.sei.basic.api.UserProfileService;
import com.changhong.sei.basic.dto.UserProfileDto;
import com.changhong.sei.basic.entity.UserProfile;
import com.changhong.sei.basic.manager.UserProfileManager;
import com.changhong.sei.core.dto.IDataValue;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.manager.BaseEntityManager;
import com.changhong.sei.core.service.DefaultBaseEntityService;
import com.changhong.sei.notify.dto.UserNotifyInfo;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 实现功能: 用户配置API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-30 10:00
 */
@Service
@Api(value = "UserProfileService", tags = "用户配置API服务实现")
public class UserProfileServiceImpl implements DefaultBaseEntityService<UserProfile, UserProfileDto>,
        UserProfileService {
    @Autowired
    private UserProfileManager manager;
    @Autowired
    private ModelMapper modelMapper;
    /**
     * 获取支持的语言
     */
    @Override
    public ResultData<List<IDataValue>> getLanguages() {
        return manager.getLanguages();
    }

    /**
     * 查询一个用户配置
     *
     * @param userId 用户id
     * @return 用户配置
     */
    @Override
    public ResultData<UserProfileDto> findByUserId(String userId) {
        return ResultData.success(convertToDto(manager.findByUserId(userId)));
    }

    /**
     * 根据用户id列表获取通知信息
     *
     * @param userIds 用户id集合
     */
    @Override
    public ResultData<List<UserNotifyInfo>> findNotifyInfoByUserIds(List<String> userIds) {
        return ResultData.success(manager.findNotifyInfoByUserIds(userIds));
    }

    /**
     * 获取当前用户的记账用户
     *
     * @return 记账用户
     */
    @Override
    public ResultData<String> findAccountor() {
        return ResultData.success(manager.findAccountor());
    }

    @Override
    public BaseEntityManager<UserProfile> getManager() {
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
    public Class<UserProfile> getEntityClass() {
        return UserProfile.class;
    }

    /**
     * 获取传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<UserProfileDto> getDtoClass() {
        return UserProfileDto.class;
    }

    /**
     * 将数据实体转换成DTO
     *
     * @param entity 业务实体
     * @return DTO
     */
    @Override
    public UserProfileDto convertToDto(UserProfile entity) {
        return UserProfileServiceImpl.custConvertToDto(entity);
    }

    /**
     * 将数据实体转换成DTO
     * @param entity 数据实体
     * @return DTO
     */
    static UserProfileDto custConvertToDto(UserProfile entity){
        if (Objects.isNull(entity)){
            return null;
        }
        ModelMapper custMapper = new ModelMapper();
        // 创建自定义映射规则
        PropertyMap<UserProfile, UserProfileDto> propertyMap = new PropertyMap<UserProfile, UserProfileDto>() {
            @Override
            protected void configure() {
                // 使用自定义转换规则
                map().setId(source.getId());
            }
        };
        // 添加映射器
        custMapper.addMappings(propertyMap);
        // 转换
        return custMapper.map(entity, UserProfileDto.class);
    }
}
