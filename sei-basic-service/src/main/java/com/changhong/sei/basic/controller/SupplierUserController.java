package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.SupplierUserApi;
import com.changhong.sei.basic.dto.SupplierUserDto;
import com.changhong.sei.basic.dto.SupplierUserVo;
import com.changhong.sei.basic.entity.SupplierUser;
import com.changhong.sei.basic.service.SupplierUserService;
import com.changhong.sei.core.controller.DefaultBaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.core.utils.ResultDataUtil;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 实现功能: 供应商用户API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-29 23:37
 */
@RestController
@Api(value = "SupplierUserService", tags = "供应商用户API服务实现")
public class SupplierUserController implements DefaultBaseEntityController<SupplierUser, SupplierUserDto>,
        SupplierUserApi {
    @Autowired
    private SupplierUserService service;
    @Autowired
    private ModelMapper modelMapper;
    /**
     * 分页查询业务实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @Override
    public ResultData<PageResult<SupplierUserVo>> findVoByPage(Search search) {
        return ResultData.success(service.findVoByPage(search));
    }

    /**
     * 保存供应商
     *
     * @param supplierUserVo 供应商信息
     * @return 操作结果
     */
    @Override
    public ResultData<SupplierUserVo> saveSupplierUserVo(SupplierUserVo supplierUserVo) {
        OperateResultWithData<SupplierUserVo> result = service.saveSupplierUserVo(supplierUserVo);
        return ResultDataUtil.convertFromOperateResult(result, result.getData());
    }

    /**
     * 保存供应商管理员
     *
     * @param supplierUserVo 供应商信息
     * @param roleCode       角色代码的KEY
     * @return 操作结果
     */
    @Override
    public ResultData saveSupplierManager(SupplierUserVo supplierUserVo, String roleCode) {
        return ResultDataUtil.convertFromOperateResult(service.saveSupplierManager(supplierUserVo, roleCode));
    }

    /**
     * 保存供应商管理员返回供应商用户ID
     *
     * @param supplierUserVo 供应商信息
     * @param roleCode       角色代码的KEY
     * @return 操作结果
     */
    @Override
    public ResultData<String> saveSupplierManagerBackId(SupplierUserVo supplierUserVo, String roleCode) {
        OperateResultWithData<String> result = service.saveSupplierManagerBackId(supplierUserVo, roleCode);
        return ResultDataUtil.convertFromOperateResult(result, result.getData());
    }

    /**
     * 增加主数据供应商字段
     *
     * @param supplierUserVo 供应商用户VO  需要申请注册供应商ID，主数据供应商ID
     * @return 操作结果
     */
    @Override
    public ResultData addSupplierIdToSupUser(SupplierUserVo supplierUserVo) {
        return ResultDataUtil.convertFromOperateResult(service.addSupplierIdToSupUser(supplierUserVo));
    }

    /**
     * 根据供应商的ID查询供应商用户
     *
     * @param supplierId 供应商ID
     * @return 供应商用户
     */
    @Override
    public ResultData<List<SupplierUserDto>> findBySupplierId(String supplierId) {
        return ResultData.success(convertToDtos(service.findBySupplierId(supplierId)));
    }

    /**
     * 保存供应商(外部提供加密后的密码)
     *
     * @param supplierUserVo 实体
     * @return 返回操作对象
     */
    @Override
    public ResultData<SupplierUserVo> saveSupplierUserVoWithPassword(SupplierUserVo supplierUserVo) {
        OperateResultWithData<SupplierUserVo> result = service.saveSupplierUserVoWithPassword(supplierUserVo);
        return ResultDataUtil.convertFromOperateResult(result, result.getData());
    }

    @Override
    public BaseEntityService<SupplierUser> getService() {
        return service;
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
    public Class<SupplierUser> getEntityClass() {
        return SupplierUser.class;
    }

    /**
     * 获取传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<SupplierUserDto> getDtoClass() {
        return SupplierUserDto.class;
    }
}
