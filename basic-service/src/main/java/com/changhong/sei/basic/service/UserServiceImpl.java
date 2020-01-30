package com.changhong.sei.basic.service;

import com.changhong.sei.basic.api.UserService;
import com.changhong.sei.basic.dto.Executor;
import com.changhong.sei.basic.dto.MenuDto;
import com.changhong.sei.basic.dto.UserDto;
import com.changhong.sei.basic.entity.Menu;
import com.changhong.sei.basic.entity.User;
import com.changhong.sei.basic.manager.UserManager;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.auth.AuthEntityData;
import com.changhong.sei.core.dto.auth.AuthTreeEntityData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.manager.BaseEntityManager;
import com.changhong.sei.core.service.DefaultBaseEntityService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 实现功能: 用户API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-30 10:32
 */
@Service
@Api(value = "UserService", tags = "用户API服务实现")
public class UserServiceImpl implements DefaultBaseEntityService<User, UserDto>,
        UserService {
    @Autowired
    private UserManager manager;
    @Autowired
    private ModelMapper modelMapper;
    /**
     * 根据用户id查询用户
     *
     * @param id 用户id
     * @return 用户
     */
    @Override
    public ResultData<UserDto> findById(String id) {
        return ResultData.success(convertToDto(manager.findById(id)));
    }

    /**
     * 获取用户有权限的操作菜单树(VO)
     *
     * @param userId 用户Id
     * @return 操作菜单树
     */
    @Override
    public ResultData<List<MenuDto>> getUserAuthorizedMenus(String userId) {
        List<Menu> menus = manager.getUserAuthorizedMenus(userId);
        List<MenuDto> menuDtos = menus.stream().map(MenuServiceImpl::custConvertToDto).collect(Collectors.toList());
        return ResultData.success(menuDtos);
    }

    /**
     * 获取用户前端权限检查的功能项键值
     *
     * @param userId 用户Id
     * @return 功能项键值
     */
    @Override
    public ResultData<Map<String, Map<String, String>>> getUserAuthorizedFeatureMaps(String userId) {
        return ResultData.success(manager.getUserAuthorizedFeatureMaps(userId));
    }

    /***
     * 判断用户是否有指定功能项的权限
     * @param userId 用户Id
     * @param featureCode 功能项代码
     * @return 有无权限
     */
    @Override
    public ResultData<Boolean> hasFeatureAuthority(String userId, String featureCode) {
        return ResultData.success(manager.hasFeatureAuthority(userId, featureCode));
    }

    /**
     * 获取用户可以分配的数据权限业务实体清单
     *
     * @param dataAuthTypeId 数据权限类型Id
     * @param userId         用户Id
     * @return 数据权限业务实体清单
     */
    @Override
    public ResultData<List<AuthEntityData>> getUserCanAssignAuthDataList(String dataAuthTypeId, String userId) {
        return ResultData.success(manager.getUserCanAssignAuthDataList(dataAuthTypeId, userId));
    }

    /**
     * 获取一般用户有权限的业务实体Id清单
     *
     * @param entityClassName 权限对象类名
     * @param featureCode     功能项代码
     * @param userId          用户Id
     * @return 业务实体Id清单
     */
    @Override
    public ResultData<List<String>> getNormalUserAuthorizedEntities(String entityClassName, String featureCode, String userId) {
        return ResultData.success(manager.getNormalUserAuthorizedEntities(entityClassName, featureCode, userId));
    }

    /**
     * 获取用户可以分配的数据权限树形业务实体清单
     *
     * @param dataAuthTypeId 数据权限类型Id
     * @param userId         用户Id
     * @return 数据权限树形业务实体清单
     */
    @Override
    public ResultData<List<AuthTreeEntityData>> getUserCanAssignAuthTreeDataList(String dataAuthTypeId, String userId) {
        return ResultData.success(manager.getUserCanAssignAuthTreeDataList(dataAuthTypeId, userId));
    }

    /**
     * 根据用户的id列表获取执行人（如果有员工信息，另赋值组织机构和岗位信息）
     *
     * @param userIds 用户的id列表
     * @return 执行人清单
     */
    @Override
    public ResultData<List<Executor>> getExecutorsByUserIds(List<String> userIds) {
        return ResultData.success(manager.getExecutorsByUserIds(userIds));
    }

    /**
     * 根据公司IDS与岗位分类IDS获取执行人
     *
     * @param corpIds    公司IDS
     * @param postCatIds 岗位分类IDS
     * @return
     */
    @Override
    public ResultData<List<Executor>> getExecutorsByPostCatAndCorp(List<String> corpIds, List<String> postCatIds) {
        return ResultData.success(manager.getExecutorsByPostCatAndCorp(corpIds, postCatIds));
    }

    /**
     * 获取用户是否有该页面的权限
     *
     * @param userId        用户Id
     * @param pageGroupCode 功能项页面分组代码(react页面路由)
     * @return 有权限则data返回有权限的功能项集合
     */
    @Override
    public ResultData<Map<String, String>> getUserAuthorizedFeature(String userId, String pageGroupCode) {
        return manager.getUserAuthorizedFeature(userId, pageGroupCode);
    }

    /**
     * 分页查询业务实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @Override
    public ResultData<PageResult<UserDto>> findByPage(Search search) {
        return convertToDtoPageResult(manager.findByPage(search));
    }

    @Override
    public BaseEntityManager<User> getManager() {
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
    public Class<User> getEntityClass() {
        return User.class;
    }

    /**
     * 获取传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<UserDto> getDtoClass() {
        return UserDto.class;
    }
}
