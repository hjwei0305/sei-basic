package com.changhong.sei.basic.service;

import com.changhong.sei.basic.dao.DataAuthorizeTypeDao;
import com.changhong.sei.basic.dto.DataAuthorizeTypeVo;
import com.changhong.sei.basic.entity.AppModule;
import com.changhong.sei.basic.entity.DataAuthorizeType;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：数据权限类型业务逻辑实现
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017-05-04 14:04      王锦光(wangj)                新建
 * <p/>
 * *************************************************************************************************
 */
@Service
public class DataAuthorizeTypeService extends BaseEntityService<DataAuthorizeType> {
    @Autowired
    private DataAuthorizeTypeDao dao;
    @Autowired
    private TenantAppModuleService tenantAppModuleService;
    @Autowired
    private DataRoleAuthTypeValueService dataRoleAuthTypeValueService;
    @Override
    protected BaseEntityDao<DataAuthorizeType> getDao() {
        return dao;
    }

    /**
     * 删除数据保存数据之前额外操作回调方法 子类根据需要覆写添加逻辑即可
     *
     * @param s 待删除数据对象主键
     */
    @Override
    protected OperateResult preDelete(String s) {
        if (dataRoleAuthTypeValueService.isExistsByProperty("dataAuthorizeType.id", s)) {
            //数据权限类型存在数据角色分配权限类型的值，禁止删除！
            return OperateResult.operationFailure("00020");
        }
        return super.preDelete(s);
    }

    /**
     * 通过数据角色Id获取数据权限类型（VO）
     *
     * @param roleId 数据角色Id
     * @return 数据权限类型
     */
    public List<DataAuthorizeTypeVo> getByDataRole(String roleId) {
        List<String> appModuleIds = tenantAppModuleService.getTenantAppModuleIds();
        List<DataAuthorizeType> authorizeTypes = dao.findByAuthorizeEntityTypeAppModuleIdIn(appModuleIds);
        return constructVo(authorizeTypes, roleId);
    }

    /**
     * 通过数据角色Id获取数据权限类型（VO）
     *
     * @param appModuleId 应用模块Id
     * @param roleId      数据角色Id
     * @return 数据权限类型
     */
    public List<DataAuthorizeTypeVo> getByAppModuleAndDataRole(String appModuleId, String roleId) {
        List<DataAuthorizeType> authorizeTypes = dao.findByAuthorizeEntityTypeAppModuleId(appModuleId);
        return constructVo(authorizeTypes, roleId);
    }

    /**
     * 构造一个数据权限类型的VO
     * @param authorizeTypes 数据权限类型
     * @param roleId 数据角色Id
     * @return 数据权限类型的VO
     */
    private List<DataAuthorizeTypeVo> constructVo(List<DataAuthorizeType> authorizeTypes, String roleId){
        List<DataAuthorizeTypeVo> vos = new ArrayList<>();
        if (CollectionUtils.isEmpty(authorizeTypes)) {
            return vos;
        }
        authorizeTypes.forEach(t -> {
            DataAuthorizeTypeVo vo = new DataAuthorizeTypeVo();
            vo.setId(t.getId());
            vo.setCode(t.getCode());
            vo.setName(t.getName());
            AppModule appModule = t.getAuthorizeEntityType().getAppModule();
            vo.setAppModuleId(appModule.getId());
            vo.setAppModuleName(appModule.getName());
            vo.setBeTree(t.getAuthorizeEntityType().getBeTree());
            vo.setAlreadyAssign(dataRoleAuthTypeValueService.isAlreadyAssign(roleId, t.getId()));
            vos.add(vo);
        });
        return vos;
    }

    /**
     * 通过实体类型名和功能项代码获取数据权限类型
     *
     * @param entityClassName 实体类型名
     * @param featureCode     功能项代码
     * @return 数据权限类型
     */
    public DataAuthorizeType getByEntityClassNameAndFeature(String entityClassName, String featureCode) {
        //判断功能项代码是否为空
        List<DataAuthorizeType> result;
        if (StringUtils.isNotBlank(featureCode) && !StringUtils.equalsIgnoreCase("null", featureCode)) {
            result = dao.findByClassNameAndFeatureCode(entityClassName, featureCode);
        } else {
            result = dao.findByClassName(entityClassName);
        }
        if (CollectionUtils.isEmpty(result)){
            return null;
        }
        return result.get(0);
    }

    /**
     * 创建数据保存数据之前额外操作回调方法 默认为空逻辑，子类根据需要覆写添加逻辑即可
     *
     * @param entity 待创建数据对象
     */
    @Override
    protected OperateResultWithData<DataAuthorizeType> preInsert(DataAuthorizeType entity) {

        // 检查重复
        DataAuthorizeType authorizeType = dao.findByAuthorizeEntityTypeIdAndFeatureId(entity.getAuthorizeEntityTypeId(), entity.getFeatureId());
        if (Objects.nonNull(authorizeType)) {
            // 存在相同权限对象和功能项的数据权限，禁止保存！
            return OperateResultWithData.operationFailure("00101");
        }
        return super.preInsert(entity);
    }

    /**
     * 更新数据保存数据之前额外操作回调方法 默认为空逻辑，子类根据需要覆写添加逻辑即可
     *
     * @param entity 待更新数据对象
     */
    @Override
    protected OperateResultWithData<DataAuthorizeType> preUpdate(DataAuthorizeType entity) {
        // 检查重复
        DataAuthorizeType authorizeType = dao.findByAuthorizeEntityTypeIdAndFeatureId(entity.getAuthorizeEntityTypeId(), entity.getFeatureId());
        if (Objects.nonNull(authorizeType) && !authorizeType.getId().equals(entity.getId())) {
            // 存在相同权限对象和功能项的数据权限，禁止保存！
            return OperateResultWithData.operationFailure("00101");
        }
        return super.preUpdate(entity);
    }
}
