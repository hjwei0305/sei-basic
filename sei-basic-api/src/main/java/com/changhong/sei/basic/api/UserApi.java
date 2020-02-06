package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.Executor;
import com.changhong.sei.basic.dto.MenuDto;
import com.changhong.sei.basic.dto.UserDto;
import com.changhong.sei.basic.dto.UserInformation;
import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindByPageApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.auth.AuthEntityData;
import com.changhong.sei.core.dto.auth.AuthTreeEntityData;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 实现功能: 用户API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-30 10:23
 */
@FeignClient(name = "sei-basic", path = "user")
@RequestMapping(path = "user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface UserApi extends BaseEntityApi<UserDto>,
        FindByPageApi<UserDto> {
    /**
     * 根据用户id查询用户
     *
     * @param id 用户id
     * @return 用户
     */
    @GetMapping(path = "findByUserId")
    @ApiOperation(notes = "根据用户id查询用户", value = "根据用户id查询一个用户")
    ResultData<UserDto> findById(@RequestParam("id")String id);

    /**
     * 获取用户有权限的操作菜单树(VO)
     * @param userId 用户Id
     * @return 操作菜单树
     */
    @GetMapping(path = "getUserAuthorizedMenus")
    @ApiOperation(value = "获取用户有权限的操作菜单树",notes = "获取用户有权限的功能项对应的菜单树")
    ResultData<List<MenuDto>> getUserAuthorizedMenus(@RequestParam("userId") String userId);

    /**
     * 获取用户前端权限检查的功能项键值
     * @param userId 用户Id
     * @return 功能项键值
     */
    @GetMapping(path = "getUserAuthorizedFeatureMaps")
    @ApiOperation(value = "获取用户前端权限检查的功能项键值",notes = "获取用户前端权限检查的功能项键值(以应用模块代码分组)")
    ResultData<Map<String, Map<String,String>>> getUserAuthorizedFeatureMaps(@RequestParam("userId") String userId);

    /***
     * 判断用户是否有指定功能项的权限
     * @param userId 用户Id
     * @param featureCode 功能项代码
     * @return 有无权限
     */
    @GetMapping(path = "hasFeatureAuthority")
    @ApiOperation(value = "判断用户是否有指定功能项的权限", notes = "判断用户是否有指定功能项的权限")
    ResultData<Boolean> hasFeatureAuthority(@RequestParam("userId") String userId, @RequestParam("featureCode") String featureCode);

    /**
     * 获取用户可以分配的数据权限业务实体清单
     * @param dataAuthTypeId 数据权限类型Id
     * @param userId 用户Id
     * @return 数据权限业务实体清单
     */
    @GetMapping(path = "getUserCanAssignAuthDataList")
    @ApiOperation(value = "获取用户可以分配的数据权限业务实体清单",notes = "通过数据权限类型获取用户可以分配的数据权限业务实体清单")
    ResultData<List<AuthEntityData>> getUserCanAssignAuthDataList(@RequestParam("dataAuthTypeId") String dataAuthTypeId, @RequestParam("userId") String userId);

    /**
     * 获取一般用户有权限的业务实体Id清单
     * @param entityClassName 权限对象类名
     * @param featureCode 功能项代码
     * @param userId 用户Id
     * @return 业务实体Id清单
     */
    @GetMapping(path = "getNormalUserAuthorizedEntities")
    @ApiOperation(value = "获取一般用户有权限的业务实体Id清单",notes = "通过权限对象类名和功能项代码获取一般用户有权限的业务实体Id清单")
    ResultData<List<String>> getNormalUserAuthorizedEntities(@RequestParam("entityClassName") String entityClassName,@RequestParam("featureCode") String featureCode,@RequestParam("userId") String userId);

    /**
     * 获取用户可以分配的数据权限树形业务实体清单
     * @param dataAuthTypeId 数据权限类型Id
     * @param userId 用户Id
     * @return 数据权限树形业务实体清单
     */
    @GetMapping(path = "getUserCanAssignAuthTreeDataList")
    @ApiOperation(value = "获取用户可以分配的数据权限树形业务实体清单",notes = "通过数据权限类型获取用户可以分配的数据权限树形业务实体清单")
    ResultData<List<AuthTreeEntityData>> getUserCanAssignAuthTreeDataList(@RequestParam("dataAuthTypeId") String dataAuthTypeId, @RequestParam("userId") String userId);

    /**
     * 根据用户的id列表获取执行人（如果有员工信息，另赋值组织机构和岗位信息）
     *
     * @param userIds 用户的id列表
     * @return 执行人清单
     */
    @GetMapping(path = "getExecutorsByUserIds")
    @ApiOperation(value = "根据用户的id列表获取执行人", notes = "根据用户的id列表获取执行人")
    ResultData<List<Executor>> getExecutorsByUserIds(@RequestParam("userIds") List<String> userIds);

    /**
     * 根据公司IDS与岗位分类IDS获取执行人
     *
     * @param corpIds    公司IDS
     * @param postCatIds 岗位分类IDS
     * @return
     */
    @GetMapping(path = "getExecutorsByPostCatAndCorp")
    @ApiOperation(value = "根据公司IDS与岗位分类IDS获取执行人", notes = "根据公司IDS与岗位分类IDS获取执行人")
    ResultData<List<Executor>> getExecutorsByPostCatAndCorp(@RequestParam("corpIds") List<String> corpIds, @RequestParam("postCatIds") List<String> postCatIds);

    /**
     * 获取用户是否有该页面的权限
     *
     * @param userId        用户Id
     * @param pageGroupCode 功能项页面分组代码(react页面路由)
     * @return 有权限则data返回有权限的功能项集合
     */
    @GetMapping(path = "getUserAuthorizedFeature")
    @ApiOperation(value = "获取用户是否有该页面的权限", notes = "获取用户是否有该页面的权限(有权限则data返回true，无则返回data为false)")
    ResultData<Map<String, String>> getUserAuthorizedFeature(@RequestParam("userId") String userId, @RequestParam("url") String pageGroupCode);

    /**
     * 通过用户userId查询用户信息
     *
     * @param userId 用户id
     * @return 用户信息
     */
    @GetMapping(path = "getUserInformation")
    @ApiOperation(notes = "根据用户id查询用户", value = "根据用户id查询一个用户")
    ResultData<UserInformation> getUserInformation(@RequestParam("userId")String userId);
}
