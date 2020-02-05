package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.SupplierUserDto;
import com.changhong.sei.basic.dto.SupplierUserVo;
import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 实现功能: 供应商用户API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-29 23:29
 */
@FeignClient(name = "sei-basic", path = "supplierUser")
@RequestMapping(path = "supplierUser", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface SupplierUserApi extends BaseEntityApi<SupplierUserDto> {
    /**
     * 分页查询业务实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @PostMapping(path = "findVoByPage", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "分页查询业务实体", notes = "分页查询业务实体")
    ResultData<PageResult<SupplierUserVo>> findVoByPage(@RequestBody Search search);

    /**
     * 保存供应商
     *
     * @param supplierUserVo 供应商信息
     * @return 操作结果
     */
    @PostMapping(path = "saveSupplierUserVo", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "保存供应商", notes = "保存供应商")
    ResultData<SupplierUserVo> saveSupplierUserVo(@RequestBody SupplierUserVo supplierUserVo);

    /**
     * 保存供应商管理员
     *
     * @param supplierUserVo 供应商信息
     * @param roleCode       角色代码的KEY
     * @return 操作结果
     */
    @PostMapping(path = "saveSupplierManager", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "保存供应商管理员", notes = "保存供应商管理员")
    ResultData saveSupplierManager(@RequestBody SupplierUserVo supplierUserVo, @RequestParam("roleCodeKey") String roleCode);

    /**
     * 保存供应商管理员返回供应商用户ID
     *
     * @param supplierUserVo 供应商信息
     * @param roleCode       角色代码的KEY
     * @return 操作结果
     */
    @PostMapping(path = "saveSupplierManagerBackId", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "保存供应商管理员返回供应商用户ID", notes = "保存供应商管理员返回供应商用户ID")
    ResultData<String> saveSupplierManagerBackId(@RequestBody SupplierUserVo supplierUserVo, @RequestParam("roleCodeKey") String roleCode);

    /**
     * 增加主数据供应商字段
     *
     * @param supplierUserVo 供应商用户VO  需要申请注册供应商ID，主数据供应商ID
     * @return 操作结果
     */
    @PostMapping(path = "addSupplierIdToSupUser", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "增加主数据供应商ID", notes = "增加主数据供应商ID")
    ResultData addSupplierIdToSupUser(@RequestBody SupplierUserVo supplierUserVo);

    /**
     * 根据供应商的ID查询供应商用户
     *
     * @param supplierId 供应商ID
     * @return 供应商用户
     */
    @GetMapping(path = "findBySupplierId")
    @ApiOperation(value = "根据供应商的ID查询供应商用户", notes = "根据供应商的ID查询供应商用户")
    ResultData<List<SupplierUserDto>> findBySupplierId(@RequestParam("supplierId") String supplierId);

    /**
     * 保存供应商(外部提供加密后的密码)
     *
     * @param supplierUserVo 实体
     * @return 返回操作对象
     */
    @PostMapping(path = "saveSupplierUserVoWithPassword")
    @ApiOperation(value = "保存供应商(外部提供加密后的密码)", notes = "保存供应商(外部提供加密后的密码)")
    ResultData<SupplierUserVo> saveSupplierUserVoWithPassword(@RequestBody SupplierUserVo supplierUserVo);
}
