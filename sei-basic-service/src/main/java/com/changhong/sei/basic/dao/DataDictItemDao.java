package com.changhong.sei.basic.dao;

import com.changhong.sei.basic.entity.DataDictItem;
import com.changhong.sei.core.dao.BaseEntityDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataDictItemDao extends BaseEntityDao<DataDictItem> {

    /**
     * 根据字段类别代码，获取字典项目
     *
     * @param id 字典项目id
     * @return 返回当前类别下的字典项目
     */
    @Query("select t from DataDictItem t where t.id = :id")
    DataDictItem findId(@Param("id") String id);

    /**
     * 根据字段类别代码，获取字典项目
     *
     * @param categoryCode 字典类别代码
     * @return 返回当前类别下的字典项目
     */
    List<DataDictItem> findByCategoryCodeOrderByRank(String categoryCode);

    /**
     * 根据字段类别代码，获取字典项目
     *
     * @param categoryCode 字典类别代码
     * @param tenant       租户代码
     * @return 返回当前类别下的字典项目
     */
    List<DataDictItem> findByCategoryCodeAndTenantCodeOrderByRank(String categoryCode, String tenant);

    /**
     * 根据字段类别代码，获取字典项目
     *
     * @param categoryCode 字典类别代码
     * @param tenant       租户代码
     * @return 返回当前类别下的字典项目
     */
    List<DataDictItem> findByCategoryCodeAndTenantCodeAndFrozenIsFalseOrderByRank(String categoryCode, String tenant);

    /**
     * 按字典类别代码和字典项目值查询字典项目
     *
     * @param categoryCode 字典类别代码
     * @param value        字典项目值
     * @return 返回符合条件的字典项目
     */
    DataDictItem findByCategoryCodeAndValue(String categoryCode, String value);

    /**
     * 按字典类别代码和字典项目值查询字典项目
     *
     * @param categoryCode 字典类别代码
     * @param value        字典项目值
     * @param tenant       租户代码
     * @return 返回符合条件的字典项目
     */
    DataDictItem findByCategoryCodeAndValueAndTenantCodeAndCode(String categoryCode, String value, String tenant, String code);

    DataDictItem findByCategoryCodeAndTenantCodeAndCode(String categoryCode, String tenant, String code);

    /**
     * 按字典类别代码和字典项目值查询字典项目
     *
     * @param categoryCode 字典类别代码
     * @param valueName    字典项目名称
     * @return 返回符合条件的字典项目
     */
    DataDictItem findByCategoryCodeAndValueName(String categoryCode, String valueName);

    /**
     * 按字典类别代码和字典项目值查询字典项目
     *
     * @param categoryCode 字典类别代码
     * @param valueName    字典项目名称
     * @param tenant       租户代码
     * @return 返回符合条件的字典项目
     */
    DataDictItem findByCategoryCodeAndValueNameAndTenantCodeAndCode(String categoryCode, String valueName, String tenant, String code);
}
