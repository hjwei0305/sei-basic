package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.SysUserApi;
import com.changhong.sei.basic.dto.SysUserDto;
import com.changhong.sei.basic.entity.SysUser;
import com.changhong.sei.basic.service.SysUserService;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.utils.ResultDataUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;


/**
 * 系统用户(SysUser)控制类
 *
 * @author sei
 * @since 2022-12-02 14:34:52
 */
@RestController
@Api(value = "SysUserApi", tags = "系统用户服务")
@RequestMapping(path = SysUserApi.PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class SysUserController extends BaseEntityController<SysUser, SysUserDto> implements SysUserApi {
    /**
     * 系统用户服务对象
     */
    @Autowired
    private SysUserService service;

    @Override
    public BaseEntityService<SysUser> getService() {
        return service;
    }

    @Override
    public ResultData updateEmpTask(Map<String, String> params) {
        LogUtil.bizLog("后台任务由【{}】执行完成！", ContextUtil.getSessionUser());
        service.updateEmpTask();
        return ResultDataUtil.success("执行成功");
    }

    @Override
    public ResultData findByEmployeeCode(String employeeCode) {
        Optional<SysUser> emp = service.findByEmployeeCode(employeeCode);
        if (emp.isPresent()){
            return ResultData.success(emp.get());
        }else {
            return ResultData.success("没有工号" + employeeCode + "员工");
        }
    }


}
