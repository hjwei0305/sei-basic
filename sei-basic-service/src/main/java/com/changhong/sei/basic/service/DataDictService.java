package com.changhong.sei.basic.service;

import com.changhong.sei.basic.dao.DataDictDao;
import com.changhong.sei.basic.dao.DataDictItemDao;
import com.changhong.sei.basic.entity.DataDict;
import com.changhong.sei.basic.entity.DataDictItem;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.IDataDict;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.chonghong.sei.util.IdGenerator;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DataDictService extends BaseEntityService<DataDict> {

    @Autowired
    private DataDictDao dao;
    @Autowired
    private DataDictItemDao dictItemDao;

    @Override
    protected BaseEntityDao<DataDict> getDao() {
        return dao;
    }

    @Override
    public OperateResultWithData<DataDict> save(DataDict entity) {
        String id = entity.getId();
        id = StringUtils.isBlank(id) ? IdGenerator.uuid2() : id;
        if (dao.isCodeExists(entity.getCode(), id)) {
            //已存在字典类别代码【{0}】
            return OperateResultWithData.operationFailure("00076", entity.getCode());
        }
        //清除缓存
        removeDataDictCache(entity.getCode());
        return super.save(entity);
    }

    /**
     * 主键删除
     *
     * @param id 主键
     * @return 返回操作结果对象
     */
    @Override
    public OperateResult delete(final String id) {
        OperateResult operateResult = preDelete(id);
        if (Objects.isNull(operateResult) || operateResult.successful()) {
            DataDict entity = findOne(id);
            if (entity != null) {
                List<DataDictItem> dictItems = dictItemDao.findByCategoryCodeOrderByRank(entity.getCode());
                if (dictItems != null && dictItems.size() > 0) {
                    //当前字典类别【{0}】存在字典项目不允许删除！
                    return OperateResult.operationSuccess("00080", entity.getCode());
                }
                //清除缓存
                removeDataDictCache(entity.getCode());

                getDao().delete(entity);
                return OperateResult.operationSuccess("ecmp_service_00003");
            } else {
                return OperateResult.operationWarning("ecmp_service_00004");
            }
        }
        return operateResult;
    }

    /**
     * 保存字典项目
     *
     * @param dictItem 字典项目
     * @return 返回保存字典项目的结果
     */
    public OperateResultWithData<DataDictItem> saveDictItem(DataDictItem dictItem) {
        // 字典类型代码
        String categoryCode = dictItem.getCategoryCode();
        //字典值
        String value = dictItem.getValue();
        //字典代码
        String code = dictItem.getCode();
        //字典值名称
        String valueName = dictItem.getValueName();
        //租户代码
        String tenantCode = dictItem.getTenantCode();
        if (StringUtils.isBlank(tenantCode)) {
            //如果没有租户值，则为通用字典数据，将租户代码设置为 global
            tenantCode = DataDictItem.DEFAULT_TENANT;
        }
        if (StringUtils.isBlank(categoryCode)) {
            //字典类别代码不允许为空
            return OperateResultWithData.operationFailure("00077", dictItem.getCode());
        }
        if (StringUtils.isBlank(code)) {
            //字典代码不允许为空
            return OperateResultWithData.operationFailure("00082");
        }
        //检查当前字典类别是否存在相同的value
        DataDictItem item = dictItemDao.findByCategoryCodeAndTenantCodeAndCode(categoryCode, tenantCode, code);
        if (Objects.nonNull(item) && !StringUtils.equals(item.getId(), dictItem.getId())) {
            //当前字典类别【{0}】存在相同的代码【{1}】
            return OperateResultWithData.operationFailure("00083", categoryCode, dictItem.getCode());
        }

        item = dictItemDao.findByCategoryCodeAndValueAndTenantCodeAndCode(categoryCode, value, tenantCode, code);
        if (Objects.nonNull(item) && !StringUtils.equals(item.getId(), dictItem.getId())) {
            //当前字典类别【{0}】存在相同的值【{1}】
            return OperateResultWithData.operationFailure("00078", dictItem.getCode());
        }

        //检查当前字典类别是否存在相同的valueName
        item = dictItemDao.findByCategoryCodeAndValueNameAndTenantCodeAndCode(categoryCode, valueName, tenantCode, code);
        if (Objects.nonNull(item) && !StringUtils.equals(item.getId(), dictItem.getId())) {
            //当前字典类别【{0}】存在相同的值名称【{1}】
            return OperateResultWithData.operationFailure("00079", dictItem.getCode());
        }

        dictItemDao.save(dictItem);

        //清除缓存
        removeDataDictCache(categoryCode);
        return OperateResultWithData.operationSuccessWithData(dictItem);
    }

    /**
     * 保存字典项目
     *
     * @param id 字典项目id
     * @return 返回保存字典项目的结果
     */
    public OperateResult deleteDictItem(String id) {
        DataDictItem entity = dictItemDao.findId(id);
        if (entity != null) {
            //清除缓存
            removeDataDictCache(entity.getCategoryCode());
            //删除
            dictItemDao.delete(entity);
            return OperateResult.operationSuccess();
        } else {
            //未找到字典项目【{0}】，删除失败！
            return OperateResult.operationFailure("00081", id);
        }
    }

    /**
     * 根据字段类别代码，获取字典项目
     *
     * @param categoryCode 字典类别代码
     * @return 返回当前类别下的字典项目
     */
    public List<DataDictItem> getDataDictItems(String categoryCode, Boolean isAll) {
        List<DataDictItem> dataDictItems;
        if (isAll == null || isAll) {
            dataDictItems = dictItemDao.findByCategoryCodeOrderByRank(categoryCode);
        } else {
            String tenant = ContextUtil.getTenantCode();
            dataDictItems = dictItemDao.findByCategoryCodeAndTenantCodeAndFrozenIsFalseOrderByRank(categoryCode, tenant);
            if (dataDictItems == null || dataDictItems.isEmpty()) {
                tenant = DataDictItem.DEFAULT_TENANT;
                dataDictItems = dictItemDao.findByCategoryCodeAndTenantCodeAndFrozenIsFalseOrderByRank(categoryCode, tenant);
            }
        }
        return dataDictItems;
    }

    /**
     * 根据字段类别代码，获取未冻结的字典项目
     *
     * @param categoryCode 字典类别代码
     * @return 返回当前类别下未冻结的字典项目
     */
    public List<IDataDict> getDataDictItemsUnFrozen(String categoryCode) {
        List<IDataDict> dataDicts = null;
        List<DataDictItem> dataDictItems = getDataDictItems(categoryCode, Boolean.FALSE);
        if (Objects.nonNull(dataDictItems)) {
            //写入缓存
            dataDicts = Lists.newArrayList(dataDictItems);
            putDataDictCache(categoryCode, dataDicts);
        }
        return dataDicts;
    }
}
