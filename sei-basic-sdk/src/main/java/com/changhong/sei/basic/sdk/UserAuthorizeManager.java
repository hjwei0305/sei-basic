package com.changhong.sei.basic.sdk;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.exception.ServiceException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 实现功能: 提供基础应用的用户权限管理API调用
 *
 * @author 王锦光 wangjg
 * @version 2020-03-20 10:38
 */
@Component
public class UserAuthorizeManager {
    private final BasicUserClient userClient;

    public UserAuthorizeManager(BasicUserClient userClient) {
        this.userClient = userClient;
    }

    /**
     * 从平台基础应用获取一般用户有权限的数据实体Id清单
     * 对于数据权限对象的业务实体，需要override，使用BASIC提供的通用工具来获取
     * @param entityClassName 权限对象实体类型
     * @param featureCode 功能项代码
     * @param userId 用户Id
     * @return 数据实体Id清单
     */
    public List<String> getNormalUserAuthorizedEntitiesFromBasic(String entityClassName, String featureCode, String userId) {
        ResultData<List<String>> resultData = userClient.getNormalUserAuthorizedEntities(entityClassName, featureCode, userId);
        if (resultData.failed()) {
            throw new ServiceException("从平台基础应用获取一般用户有权限的数据实体Id清单失败！"+resultData.getMessage());
        }
        return resultData.getData();
    }
}
