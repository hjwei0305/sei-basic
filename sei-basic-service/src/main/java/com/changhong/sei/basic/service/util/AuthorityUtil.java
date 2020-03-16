package com.changhong.sei.basic.service.util;

import com.changhong.sei.basic.entity.*;
import com.changhong.sei.basic.service.*;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.log.LogUtil;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * 实现功能：权限工具类
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2019-11-28 23:16
 */
@SuppressWarnings("unchecked")
public class AuthorityUtil {

    /**
     * 按功能角色清除用户权限缓存
     *
     * @param featureRoleId 功能角色id
     */

    public static void cleanAuthorizedCachesByFeatureRoleId(String featureRoleId) {
        LogUtil.debug("按功能角色清除用户权限缓存 {}", featureRoleId);
        // step 1 检查当前角色是否是公共角色,公共角色清除所有用户
        FeatureRole featureRole = ContextUtil.getBean(FeatureRoleService.class).findOne(featureRoleId);
        if (Objects.nonNull(featureRole)
                && (Objects.nonNull(featureRole.getPublicUserType()) || Objects.nonNull(featureRole.getPublicOrg()))) {
            cleanAllUserAuthorizedCaches();
        }

        // step 2 获取功能角色对应的用户,并按用户清除
        List<User> users = ContextUtil.getBean(UserFeatureRoleService.class).getParentsFromChildId(featureRoleId);
        if (CollectionUtils.isNotEmpty(users)) {
            users.forEach(user -> {
                cleanUserAuthorizedCaches(user.getId());
            });
        }

        // step 3 获取功能角色对应的岗位,并按岗位清除
        List<Position> positions = ContextUtil.getBean(PositionFeatureRoleService.class).getParentsFromChildId(featureRoleId);
        if (CollectionUtils.isNotEmpty(positions)) {
            positions.forEach(position -> {
                cleanAuthorizedCachesByPositionId(position.getId());
            });
        }
    }

    /**
     * 按数据角色清除用户权限缓存
     *
     * @param dataRoleId 数据角色id
     */
    public static void cleanAuthorizedCachesByDataRoleId(String dataRoleId) {
        LogUtil.debug("按数据角色清除用户权限缓存 {}", dataRoleId);
        // step 1 检查当前角色是否是公共角色,公共角色清除所有用户
        DataRole dataRole = ContextUtil.getBean(DataRoleService.class).findOne(dataRoleId);
        if (Objects.nonNull(dataRole)
                && (Objects.nonNull(dataRole.getPublicUserType()) || Objects.nonNull(dataRole.getPublicOrg()))) {
            cleanAllUserAuthorizedCaches();
        }
        // step 2 获取数据角色对应的用户,并按用户清除
        List<User> users = ContextUtil.getBean(UserDataRoleService.class).getParentsFromChildId(dataRoleId);
        if (CollectionUtils.isNotEmpty(users)) {
            users.forEach(user -> {
                cleanUserAuthorizedCaches(user.getId());
            });
        }

        // step 3 获取数据角色对应的岗位,并按岗位清除
        List<Position> positions = ContextUtil.getBean(PositionDataRoleService.class).getParentsFromChildId(dataRoleId);
        if (CollectionUtils.isNotEmpty(positions)) {
            positions.forEach(position -> {
                cleanAuthorizedCachesByPositionId(position.getId());
            });
        }
    }

    /**
     * 按岗位清除用户权限缓存
     *
     * @param positionId 岗位id
     */
    public static void cleanAuthorizedCachesByPositionId(String positionId) {
        LogUtil.debug("按岗位清除用户权限缓存 {}", positionId);
        List<Employee> employees = ContextUtil.getBean(EmployeePositionService.class).getParentsFromChildId(positionId);
        if (CollectionUtils.isNotEmpty(employees)) {
            employees.forEach(employee -> {
                cleanUserAuthorizedCaches(employee.getUser().getId());
            });
        }
    }

    /**
     * 通过用户id异步清理权限缓存
     *
     * @param userId 用户id
     */
    public static void cleanUserAuthorizedCaches(String userId) {
        if (StringUtils.isBlank(userId)) {
            return;
        }
        RedisTemplate<String, Object> redisTemplate = ContextUtil.getBean("redisTemplate");
        CompletableFuture.runAsync(() -> {
            Set<String> keys = redisTemplate.keys("*" + userId);
            LogUtil.debug("通过用户id[{}]异步清理权限缓存 {}", userId, keys.size());

            if (CollectionUtils.isNotEmpty(keys)) {
                redisTemplate.delete(keys);
            }
        });
    }

    /**
     * 清除所有用户权限缓存
     */
    public static void cleanAllUserAuthorizedCaches() {
        LogUtil.debug("清除所有用户权限缓存");

        RedisTemplate redisTemplate = ContextUtil.getBean("redisTemplate");
        CompletableFuture.runAsync(() -> {
            Set<String> keys = Sets.newHashSet();
            keys.addAll(redisTemplate.keys("UserAuthorizedFeaturesCache*"));
            keys.addAll(redisTemplate.keys("UserAuthorizedMenusCache*"));
            keys.addAll(redisTemplate.keys("UserCanAssignFeaturesCache*"));
            keys.addAll(redisTemplate.keys("UserAuthorizedFeatureMapsCache*"));
            keys.addAll(redisTemplate.keys("UserCanAssignAuthDataList*"));
            keys.addAll(redisTemplate.keys("getNormalUserAuthorizedEntities*"));
            keys.addAll(redisTemplate.keys("UserCanAssignAuthTreeDataList*"));
            keys.addAll(redisTemplate.keys("UserAuthorizedFeature*"));
            LogUtil.debug("清除所有用户权限缓存 {}", keys.size());

            if (CollectionUtils.isNotEmpty(keys)) {
                redisTemplate.delete(keys);
            }
        });
    }

}
