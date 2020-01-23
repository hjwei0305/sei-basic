package com.changhong.sei.basic.manager;

import com.changhong.sei.basic.dao.EmployeeDao;
import com.changhong.sei.basic.dao.UserProfileDao;
import com.changhong.sei.basic.entity.UserProfile;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.entity.IDataValue;
import com.changhong.sei.core.manager.BaseEntityManager;
import com.changhong.sei.notify.dto.UserNotifyInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

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
@Component
public class UserProfileManager extends BaseEntityManager<UserProfile> {
    @Autowired
    private UserProfileDao userProfileDao;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    protected BaseEntityDao<UserProfile> getDao() {
        return userProfileDao;
    }

    /**
     * 获取用户配置的默认语言
     * @return 对象Id/Name/value
     */
    public ResultData<List<IDataValue>> getLanguages() {
        List<IDataValue> languages = new ArrayList<>();
        languages.add(new IDataValue() {
            @Override
            public Object getId() {
                return null;
            }

            @Override
            public String getName() {
                return "中文 (中国)";
            }

            @Override
            public Object getValue() {
                return "zh_CN";
            }
        });

        languages.add(new IDataValue() {
            @Override
            public Object getId() {
                return null;
            }

            @Override
            public String getName() {
                return "English";
            }

            @Override
            public Object getValue() {
                return "en_US";
            }
        });

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
            userNotifyInfo.setEmail(r.getEmail());
            userNotifyInfo.setMobile(r.getMobile());
            userNotifyInfos.add(userNotifyInfo);
        });
        return userNotifyInfos;
    }

    /**
     * 查询一个用户配置
     *
     * @param userId 用户id
     * @return 用户配置
     */
    public UserProfile findByUserId(String userId) {
        return userProfileDao.findByUserId(userId);
    }

    /**
     * 获取当前用户的记账用户
     *
     * @return 记账用户
     */
    public String findAccountor() {
        UserProfile userProfile = findByUserId(ContextUtil.getUserId());
        if (Objects.nonNull(userProfile)) {
            return userProfile.getAccountor();
        }
        return null;
    }

    /**
     * 获取8位数验证码
     * @return 验证码
     */
    private String getVerifyCode () {
        final char[] CHAR_SET = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
                'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
                'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        for (int i = 1; i < 9; i++) {
            buffer.append(CHAR_SET[random.nextInt(CHAR_SET.length)]);
        }
        return buffer.toString();
    }
}

