package com.changhong.sei.basic.dao;

import com.changhong.sei.basic.dto.search.PositionQuickQueryParam;
import com.changhong.sei.basic.dto.search.UserQuickQueryParam;
import com.changhong.sei.basic.entity.Organization;
import com.changhong.sei.basic.entity.Position;
import com.changhong.sei.basic.entity.User;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.QuickQueryParam;

import java.util.List;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：岗位数据访问扩展接口
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017/6/30 13:45      秦有宝                     新建
 * <p/>
 * *************************************************************************************************
 */
public interface PositionExtDao {
    /**
     * 检查同一部门下的岗位名称是否存在
     *
     * @param organizationId 组织机构的id
     * @param name 岗位名称
     * @param id 实体id
     * @return 是否存在
     */
    Boolean isOrgAndNameExist(String organizationId, String name, String id);

    /**
     * 分页查询岗位
     *
     * @param queryParam 查询参数
     * @param excludeIds 排除的岗位Id清单
     * @param tenantCode 租户代码
     * @param organization 组织机构
     * @return 岗位
     */
    PageResult<Position> queryPositions(PositionQuickQueryParam queryParam, List<String> excludeIds, String tenantCode, Organization organization);
}
