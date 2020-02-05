package com.changhong.sei.basic.dao;

import com.changhong.sei.basic.entity.Position;
import com.changhong.sei.core.dao.BaseEntityDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：岗位的数据访问接口
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间                  变更人                 变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017/4/17 23:02            高银军                  新建
 * <p/>
 * *************************************************************************************************
 */
@Repository
public interface PositionDao extends BaseEntityDao<Position>, PositionExtDao {
    /**
     * 根据岗位类别的id来查询岗位
     *
     * @param categoryId 岗位类别Id
     * @return 岗位清单
     */
    List<Position> findByPositionCategoryId(String categoryId);

    /**
     * 根据组织机构的id获取岗位
     *
     * @param organizationId 组织机构的id
     * @return 岗位清单
     */
    List<Position> findByOrganizationId(String organizationId);

    /**
     * 根据组织获取所有的岗位 根据岗位code排序
     * @param organizationId 组织Id
     * @return 岗位列表
     */
    List<Position> findAllByOrganizationIdOrderByCode(String organizationId);

    /**
     * 根据组织机构的id和名称获取岗位
     *
     * @param organizationId 组织机构的id
     * @param name 岗位名称
     * @return 岗位清单
     */
    List<Position> findByOrganizationIdAndName(String organizationId, String name);
}
