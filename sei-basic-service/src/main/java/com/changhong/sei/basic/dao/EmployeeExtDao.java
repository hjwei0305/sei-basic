package com.changhong.sei.basic.dao;

import com.changhong.sei.basic.dto.EmployeeBriefInfo;
import com.changhong.sei.basic.dto.EmployeeQueryParam;
import com.changhong.sei.basic.dto.search.EmployeeBriefInfoQueryParam;
import com.changhong.sei.basic.dto.search.EmployeeQuickQueryParam;
import com.changhong.sei.basic.entity.Employee;
import com.changhong.sei.basic.entity.Organization;
import com.changhong.sei.core.dto.serach.PageResult;

import java.util.List;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：企业员工扩展接口
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017/5/26 13:59      秦有宝                     新建
 * <p/>
 * *************************************************************************************************
 */
public interface EmployeeExtDao {

    /**
     * 保存企业员工用户
     *
     * @param entity 企业员工用户实体
     * @param isNew 是否是创建
     * @return 保存结果
     */
    Employee save(Employee entity, boolean isNew);

    /**
     * 根据查询参数获取企业员工(分页)
     *
     * @param employeeQueryParam 查询参数
     * @return 企业员工
     */
    PageResult<Employee> findByEmployeeParam(EmployeeQueryParam employeeQueryParam);

    /**
     * 检查员工编号是否存在
     * @param code 员工编号
     * @param id 实体id
     * @return 是否存在
     */
    Boolean isCodeExist(String code, String id);

    /**
     * 分页查询企业用户
     *
     * @param queryParam 查询参数
     * @param organization 查询的组织节点
     * @param excludeEmployeeIds 需要排数的用户Id清单
     * @return 企业用户
     */
    PageResult<Employee> queryEmployees(EmployeeQuickQueryParam queryParam,
                                        Organization organization,
                                        List<String> excludeEmployeeIds);

    /**
     * 分页查询企业用户简要信息
     * @param queryParam 查询参数
     * @param tenantCode 租户代码
     * @return 企业用户简要信息
     */
    PageResult<EmployeeBriefInfo> queryEmployeeBriefInfos(EmployeeBriefInfoQueryParam queryParam, String tenantCode);
}
