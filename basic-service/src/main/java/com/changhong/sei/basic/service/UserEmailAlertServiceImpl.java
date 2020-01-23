package com.changhong.sei.basic.service;

import com.changhong.sei.basic.api.UserEmailAlertService;
import com.changhong.sei.basic.dto.UserEmailAlertDto;
import com.changhong.sei.basic.entity.UserEmailAlert;
import com.changhong.sei.basic.manager.UserEmailAlertManager;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.manager.BaseEntityManager;
import com.changhong.sei.core.manager.bo.OperateResult;
import com.changhong.sei.core.service.DefaultBaseEntityService;
import com.changhong.sei.core.utils.ResultDataUtil;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 实现功能: 用户邮件提醒API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-22 10:28
 */
@Service
@Api(value = "UserEmailAlertService", tags = "用户邮件提醒API服务")
public class UserEmailAlertServiceImpl implements DefaultBaseEntityService<UserEmailAlert, UserEmailAlertDto>,
        UserEmailAlertService {
    @Autowired
    private UserEmailAlertManager manager;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public BaseEntityManager<UserEmailAlert> getManager() {
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
    public Class<UserEmailAlert> getEntityClass() {
        return UserEmailAlert.class;
    }

    /**
     * 获取传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<UserEmailAlertDto> getDtoClass() {
        return UserEmailAlertDto.class;
    }

    /**
     * 通过用户ID列表获取用户邮件通知列表
     *
     * @param userIdS 用户ID列表
     * @return 操作结果
     */
    @Override
    public ResultData<List<UserEmailAlertDto>> findByUserIds(List<String> userIdS) {
        List<UserEmailAlert> alerts = manager.findByUserIds(userIdS);
        List<UserEmailAlertDto> dtos = alerts.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    /**
     * 通过用户ID列表更新最新提醒时间
     *
     * @param userIds 用户ID列表
     * @return 操作结果
     */
    @Override
    public ResultData updateLastTimes(List<String> userIds) {
        OperateResult result = manager.updateLastTimes(userIds);
        return ResultDataUtil.convertFromOperateResult(result);
    }

    /**
     * 获取当前用户邮件通知列表
     *
     * @return 操作结果
     */
    @Override
    public ResultData<List<UserEmailAlertDto>> findByUserIds() {
        List<UserEmailAlert> alerts = manager.findByUserIds();
        List<UserEmailAlertDto> dtos = alerts.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResultData.success(dtos);
    }
}
