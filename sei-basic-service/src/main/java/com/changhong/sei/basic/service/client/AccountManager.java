package com.changhong.sei.basic.service.client;

import com.changhong.sei.basic.service.client.dto.SessionUserResponse;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 实现功能: 平台AUTH用户账户服务
 *
 * @author 王锦光 wangjg
 * @version 2020-02-26 21:50
 */
@Component
public class AccountManager {
    @Autowired
    private AccountClient client;

    /**
     * 获取用户信息
     * @param tenantCode 租户代码
     * @param account 用户账号
     * @return 用户信息
     */
    public SessionUserResponse getSessionUser(String tenantCode, String account) {
        ResultData<SessionUserResponse> resultData = client.getByTenantAccount(tenantCode, account);
        if (resultData.failed()) {
            throw new ServiceException("通过租户和账号获取已有账户异常！"+resultData.getMessage());
        }
        return resultData.getData();
    }
}
