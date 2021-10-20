package com.changhong.sei.basic.service;

import com.changhong.sei.basic.dao.UserProfileDao;
import com.changhong.sei.basic.dto.LanguageValue;
import com.changhong.sei.basic.dto.UserPreferenceEnum;
import com.changhong.sei.basic.entity.User;
import com.changhong.sei.basic.entity.UserProfile;
import com.changhong.sei.basic.service.client.AccountManager;
import com.changhong.sei.basic.service.client.dto.AccountInfoDto;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.Validation;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.notify.dto.UserNotifyInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：实现用户配置的业务逻辑服务
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间                  变更人                 变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017/4/14 15:45            高银军                  新建
 * <p/>
 * *************************************************************************************************
 */
@Service
public class UserProfileService extends BaseEntityService<UserProfile> {
    @Autowired
    private UserProfileDao userProfileDao;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountManager accountManager;

    @Override
    protected BaseEntityDao<UserProfile> getDao() {
        return userProfileDao;
    }

    /**
     * 获取用户配置的默认语言
     *
     * @return 对象Id/Name/value
     */
    public ResultData<List<LanguageValue>> getLanguages() {
        List<LanguageValue> languages = new LinkedList<>();
        languages.add(new LanguageValue("中文 (中国)", "zh_CN"));
        languages.add(new LanguageValue("English", "en_US"));
        return ResultData.success(languages);
    }

    /**
     * 根据用户id列表获取通知信息
     *
     * @param userIds 用户id集合
     */
    public List<UserNotifyInfo> findNotifyInfoByUserIds(List<String> userIds) {
        List<UserProfile> userProfiles = userProfileDao.findNotifyInfoByUserIds(userIds);
        List<UserNotifyInfo> userNotifyInfos = new ArrayList<>();
        userProfiles.forEach((r) -> {
            UserNotifyInfo userNotifyInfo = new UserNotifyInfo();
            userNotifyInfo.setUserId(r.getUser().getId());
            userNotifyInfo.setUserName(r.getUser().getUserName());
            userNotifyInfo.setUserAccount(r.getUser().getAccount());
            userNotifyInfo.setEmail(r.getEmail());
            userNotifyInfo.setMobile(r.getMobile());
            //TODO 获取用户的微信用户和小程序用户openid
//            List<UserAccount> userAccounts = userAccountDao.findByUserId(r.getUser().getId());
//            Set<String> weChatOpenId = userAccounts.stream().map(UserAccount::getWechatOpenId).filter(StringUtils::isNotEmpty).collect(Collectors.toSet());
//            userNotifyInfo.setWeChatOpenId(weChatOpenId);
//            Set<String> miniProgramOpenId = userAccounts.stream().map(UserAccount::getMiniProgramOpenId).filter(StringUtils::isNotEmpty).collect(Collectors.toSet());
//            userNotifyInfo.setMiniProgramOpenId(miniProgramOpenId);
            userNotifyInfos.add(userNotifyInfo);
        });
        return userNotifyInfos;
    }

    /**
     * 数据保存操作
     */
    @Override
    @Transactional
    public OperateResultWithData<UserProfile> save(UserProfile entity) {
        Validation.notNull(entity, "持久化对象不能为空");

        String id = entity.getId();
        String userId = entity.getUserId();
        User user = userService.findOne(userId);
        if (Objects.isNull(user)) {
            // 用户【{0}】配置不存在.
            return OperateResultWithData.operationFailure("00092", userId);
        }

        try {
            // 偏好设置
            if (StringUtils.isNotBlank(id)) {
                UserProfile userProfile = this.findOne(id);
                if (Objects.isNull(userProfile)) {
                    // 用户【{0}】配置不存在.
                    return OperateResultWithData.operationFailure("00118", id);
                }

                Map<String, Object> preferenceMap;
                String preferences = userProfile.getPreferences();
                if (StringUtils.isNotBlank(preferences)) {
                    preferenceMap = JsonUtils.fromJson(preferences, Map.class);
                } else {
                    preferenceMap = new HashMap<>();
                    // 系统引导
                    preferenceMap.put(UserPreferenceEnum.guide.name(), Boolean.FALSE);
                }
                String portrait = entity.getPortrait();
                if (StringUtils.isNotBlank(portrait)) {
                    // 头像
                    preferenceMap.put(UserPreferenceEnum.portrait.name(), portrait);
                }
                entity.setPreferences(JsonUtils.toJson(preferenceMap));
            } else {
                Map<String, Object> preferenceMap = new HashMap<>();
                // 系统引导
                preferenceMap.put(UserPreferenceEnum.guide.name(), Boolean.FALSE);

                String portrait = entity.getPortrait();
                if (StringUtils.isNotBlank(portrait)) {
                    // 头像
                    preferenceMap.put(UserPreferenceEnum.portrait.name(), portrait);
                }
                entity.setPreferences(JsonUtils.toJson(preferenceMap));
            }
            AccountInfoDto accountInfo = new AccountInfoDto();
            accountInfo.setTenantCode(user.getTenantCode());
            accountInfo.setAccount(user.getAccount());
            accountInfo.setAccountType(user.getUserType().name());
            accountInfo.setAuthorityPolicy(user.getUserAuthorityPolicy().name());
            accountInfo.setEmail(entity.getEmail());
            accountInfo.setMobile(entity.getMobile());
            accountInfo.setGender(entity.getGender());
            accountInfo.setIdCard(entity.getIdCard());
            accountInfo.setLanguageCode(entity.getLanguageCode());
            accountManager.updateAccountInfo(accountInfo);
        } catch (Exception ignored) {
        }
        return super.save(entity);
    }

    /**
     * 查询一个用户配置
     *
     * @param userId 用户id
     * @return 用户配置
     */
    public UserProfile findByUserId(String userId) {
        UserProfile userProfile = userProfileDao.findByUserId(userId);
        if (Objects.nonNull(userProfile)) {
            // 设置头像
            String preferenceStr = userProfile.getPreferences();
            if (StringUtils.isNotBlank(preferenceStr)) {
                try {
                    Map<String, Object> preferenceMap = JsonUtils.fromJson(preferenceStr, Map.class);
                    userProfile.setPortrait((String) preferenceMap.get(UserPreferenceEnum.portrait.name()));
                } catch (Exception ignored) {
                }
            }
        }
        return userProfile;
    }

    /**
     * 设置用户偏好配置
     *
     * @param preference 偏好配置类型
     * @param userId     用户id
     * @param value      偏好配置
     * @return 返回操作结果
     */
    public ResultData<Void> putUserPreference(String userId, UserPreferenceEnum preference, Object value) {
        UserProfile userProfile = userProfileDao.findByUserId(userId);
        if (Objects.isNull(userProfile)) {
            // 用户【{0}】配置不存在.
            return ResultData.fail(ContextUtil.getMessage("00118", userId));
        }

        Map<String, Object> preferenceMap;
        String preferences = userProfile.getPreferences();
        if (StringUtils.isNotBlank(preferences)) {
            preferenceMap = JsonUtils.fromJson(preferences, Map.class);
        } else {
            preferenceMap = new HashMap<>();
        }

        preferenceMap.put(preference.name(), value);
        userProfile.setPreferences(JsonUtils.toJson(preferenceMap));
        OperateResultWithData<UserProfile> result = super.save(userProfile);
        if (result.successful()) {
            return ResultData.success();
        } else {
            return ResultData.fail(result.getMessage());
        }
    }

    /**
     * 获取当前用户的记账用户
     *
     * @return 记账用户
     */
    public String findAccountor() {
        UserProfile userProfile = userProfileDao.findByUserId(ContextUtil.getUserId());
        if (Objects.nonNull(userProfile)) {
            return userProfile.getAccountor();
        }
        return null;
    }

    /**
     * 获取用户的头像
     *
     * @return 头像文件数据
     */
    public String getPreferences(String userId) {
        UserProfile userProfile = userProfileDao.findByUserId(userId);
        if (Objects.isNull(userProfile)) {
            return null;
        }
        return userProfile.getPreferences();
    }
}

