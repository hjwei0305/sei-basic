package com.changhong.sei.basic.service.client;

import com.changhong.sei.basic.service.client.dto.AccountResponse;
import com.changhong.sei.basic.service.client.dto.CreateAccountRequest;
import com.changhong.sei.basic.service.client.dto.SessionUserResponse;
import com.changhong.sei.basic.service.client.dto.UpdateAccountRequest;
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
            throw new ServiceException("租户【"+tenantCode+"】中获取账户【"+account+"】信息失败！"+resultData.getMessage());
        }
        return resultData.getData();
    }

    /**
     * 创建新账户
     * @param request 创建参数
     */
    public void create(CreateAccountRequest request) {
        try {
            ResultData resultData = client.create(request);
            if (resultData.failed()) {
                throw new ServiceException("创建新账户失败！"+resultData.getMessage());
            }
        } catch (IllegalAccessException e) {
            throw new ServiceException("创建新账户异常！"+e.getMessage());
        }
    }

    /**
     * 更改账户
     * @param request 更改参数
     */
    public void update(UpdateAccountRequest request) {
        try {
            ResultData resultData = client.update(request);
            if (resultData.failed()) {
                throw new ServiceException("更改账户失败！"+resultData.getMessage());
            }
        } catch (IllegalAccessException e) {
            throw new ServiceException("更改账户异常！"+e.getMessage());
        }
    }
}
