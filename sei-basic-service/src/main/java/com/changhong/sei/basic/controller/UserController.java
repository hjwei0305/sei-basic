package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.UserApi;
import com.changhong.sei.basic.dto.*;
import com.changhong.sei.basic.dto.search.UserQuickQueryParam;
import com.changhong.sei.basic.entity.*;
import com.changhong.sei.basic.service.*;
import com.changhong.sei.basic.service.client.AccountManager;
import com.changhong.sei.basic.service.client.dto.SessionUserResponse;
import com.changhong.sei.commondata.sdk.annotation.MultilingualEnable;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.controller.DefaultBaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.auth.AuthEntityData;
import com.changhong.sei.core.dto.auth.AuthTreeEntityData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.utils.ResultDataUtil;
import com.changhong.sei.enums.UserAuthorityPolicy;
import com.changhong.sei.utils.AsyncRunUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 实现功能: 用户API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-30 10:32
 */
@RestController
@Api(value = "UserApi", tags = "用户API服务实现")
@RequestMapping(path = "user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@SuppressWarnings("unchecked")
public class UserController implements DefaultBaseEntityController<User, UserDto>,
        UserApi {
    @Autowired
    private UserService service;
    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private AccountManager accountManager;
    @Autowired
    private DataRoleService dataRoleService;
    @Autowired
    private AsyncRunUtil asyncRunUtil;
    @Autowired
    private UserMenuService userMenuService;
    /**
     * 根据用户id查询用户
     *
     * @param id 用户id
     * @return 用户
     */
    @Override
    public ResultData<UserDto> findById(String id) {
        return ResultData.success(convertToDto(service.findById(id)));
    }

    /**
     * 获取用户有权限的操作菜单树(DTO)
     *
     * @param userId 用户Id
     * @return 操作菜单树
     */
    @Override
    @MultilingualEnable
    public ResultData<List<MenuDto>> getUserAuthorizedMenus(String userId) {
        // 获取用户有权限的菜单项清单
        List<Menu> menus = service.getUserAuthorizedMenus(userId);
        // 设置是否用户已经收藏
        userMenuService.fetchFavoriteMenus(menus, userId);
        // 构造菜单树
        List<Menu> menusTrees = MenuService.buildTree(menus);
        List<MenuDto> menuDtos = menusTrees.stream().map(MenuController::custConvertToDto).collect(Collectors.toList());
        return ResultData.success(menuDtos);
    }

    /**
     * 获取用户前端权限检查的功能项键值
     *
     * @param userId 用户Id
     * @return 功能项键值
     */
    @Override
    public ResultData<Map<String, Set<String>>> getUserAuthorizedFeatureMaps(String userId) {
        return ResultData.success(service.getUserAuthorizedFeatureMaps(userId));
    }

    /***
     * 判断用户是否有指定功能项的权限
     * @param userId 用户Id
     * @param featureCode 功能项代码
     * @return 有无权限
     */
    @Override
    public ResultData<Boolean> hasFeatureAuthority(String userId, String featureCode) {
        return ResultData.success(service.hasFeatureAuthority(userId, featureCode));
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
        return ResultData.success(service.getUserCanAssignAuthDataList(dataAuthTypeId, userId));
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
        return ResultData.success(service.getNormalUserAuthorizedEntities(entityClassName, featureCode, userId));
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
        return ResultData.success(service.getUserCanAssignAuthTreeDataList(dataAuthTypeId, userId));
    }

    /**
     * 根据用户的id列表获取执行人（如果有员工信息，另赋值组织机构和岗位信息）
     *
     * @param userIds 用户的id列表
     * @return 执行人清单
     */
    @Override
    public ResultData<List<Executor>> getExecutorsByUserIds(List<String> userIds) {
        return ResultData.success(service.getExecutorsByUserIds(userIds));
    }

    /**
     * 根据公司IDS与岗位分类IDS获取执行人
     *
     * @param queryParam    执行人查询参数
     * @return 执行人清单
     */
    @Override
    public ResultData<List<Executor>> getExecutorsByPostCatAndCorp(ExecutorQueryParam queryParam) {
        return ResultData.success(service.getExecutorsByPostCatAndCorp(queryParam.getCorpIds(), queryParam.getPostCatIds()));
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
        return service.getUserAuthorizedFeature(userId, pageGroupCode);
    }

    /**
     * 通过用户userId查询用户信息
     *
     * @param userId 用户id
     * @return 用户信息
     */
    @Override
    public ResultData<UserInformation> getUserInformation(String userId) {
        UserInformation information = new UserInformation();
        information.setUserId(userId);
        // 获取用户基本信息
        User user = service.findById(userId);
        if (Objects.isNull(user)){
            // 用户【{0}】不存在！
            return ResultData.fail(ContextUtil.getMessage("00092", userId));
        }
        information.setUserType(user.getUserType());
        information.setUserAuthorityPolicy(user.getUserAuthorityPolicy());
        // 获取用户配置信息
        UserProfile profile = userProfileService.findByUserId(userId);
        if (Objects.nonNull(profile)){
            information.setLanguageCode(profile.getLanguageCode());
        }
        return ResultData.success(information);
    }

    /**
     * 清除用户权限相关的所有缓存
     *
     * @param userId 用户Id
     */
    @Override
    public void clearUserAuthorizedCaches(String userId) {
        // 清除用户权限相关的缓存
        service.clearUserAuthorizedCaches(userId);
        // 异步加载用户的应用菜单
        asyncRunUtil.runAsync(()-> service.getUserAuthorizedMenus(userId));
    }

    /**
     * 通过用户账户获取用户的数据角色
     *
     * @param account 用户账户
     * @return 数据角色清单
     */
    @Override
    public ResultData<List<DataRoleDto>> getDataRolesByAccount(String account) {
        // 获取用户信息
        ResultData<User> userResultData = getNormalUserByAccount(account);
        if (userResultData.failed()) {
            return ResultData.fail(userResultData.getMessage());
        }
        Set<DataRole> roles = service.getNormalUserDataRoles(userResultData.getData());
        List<DataRoleDto> roleDtos = roles.stream().map(DataRoleController::custConvertToDto).collect(Collectors.toList());
        return ResultData.success(roleDtos);
    }

    /**
     * 通过用户账号获取平台的一般用户
     * @param account 用户账号
     * @return 操作结果
     */
    private ResultData<User> getNormalUserByAccount(String account) {
        // 获取用户信息
        String tenantCode = ContextUtil.getTenantCode();
        SessionUserResponse userResponse = accountManager.getSessionUser(tenantCode, account);
        if (Objects.isNull(userResponse)) {
            // 用户账号【{0}】不存在！
            return ResultDataUtil.fail("00108", account);
        }
        UserAuthorityPolicy authorityPolicy = userResponse.getAuthorityPolicy();
        if (authorityPolicy == UserAuthorityPolicy.GlobalAdmin) {
            // 用户账号【{0}】是全局管理员！
            return ResultDataUtil.fail("00109", account);
        }
        if (authorityPolicy == UserAuthorityPolicy.TenantAdmin) {
            // 用户账号【{0}】是租户【{1}】的系统管理员！
            return ResultDataUtil.fail("00110", account, tenantCode);
        }
        User user = service.findOne(userResponse.getUserId());
        if (Objects.isNull(user)) {
            // 用户账号【【{0}】不存在对应的系统用户！
            return ResultDataUtil.fail("00098", account);
        }
        return ResultData.success(user);
    }

    /**
     * 通过用户账户获取用户的功能角色
     *
     * @param account 用户账户
     * @return 功能角色清单
     */
    @Override
    public ResultData<List<FeatureRoleDto>> getFeatureRolesByAccount(String account) {
        // 获取用户信息
        ResultData<User> userResultData = getNormalUserByAccount(account);
        if (userResultData.failed()) {
            return ResultData.fail(userResultData.getMessage());
        }
        Set<FeatureRole> roles = service.getNormalUserFeatureRoles(userResultData.getData());
        List<FeatureRoleDto> roleDtos = roles.stream().map(FeatureRoleController::custConvertToDto).collect(Collectors.toList());
        return ResultData.success(roleDtos);
    }

    /**
     * 分页查询平台用户
     *
     * @param queryParam 查询参数
     * @return 用户DTO
     */
    @Override
    public ResultData<PageResult<UserDto>> queryUsers(UserQuickQueryParam queryParam) {
        return convertToDtoPageResult(service.queryUsers(queryParam, ContextUtil.getTenantCode()));
    }

    /**
     * 分页查询业务实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @Override
    public ResultData<PageResult<UserDto>> findByPage(Search search) {
        return convertToDtoPageResult(service.findByPage(search));
    }

    @Override
    public BaseEntityService<User> getService() {
        return service;
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
