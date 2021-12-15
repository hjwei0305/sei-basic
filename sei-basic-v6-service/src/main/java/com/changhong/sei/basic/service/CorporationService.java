package com.changhong.sei.basic.service;

import com.changhong.sei.basic.dao.CorporationDao;
import com.changhong.sei.basic.entity.Corporation;
import com.changhong.sei.basic.entity.Organization;
import com.changhong.sei.basic.service.cust.CorporationServiceCust;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.DataAuthEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * *************************************************************************************************
 * <br>
 * 实现功能：公司业务逻辑实现
 * <br>
 * ------------------------------------------------------------------------------------------------
 * <br>
 * 版本          变更时间             变更人                     变更原因
 * <br>
 * ------------------------------------------------------------------------------------------------
 * <br>
 * 1.0.00      2017/6/2 17:26    余思豆(yusidou)                 新建
 * <br>
 * *************************************************************************************************<br>
 */
@Service
public class CorporationService extends BaseEntityService<Corporation> implements DataAuthEntityService {

    @Autowired
    private CorporationDao corporationDao;
    @Autowired
    private UserService userService;
    @Autowired(required = false)
    private OrganizationService organizationService;
    @Autowired
    private DataRoleAuthTypeValueService dataRoleAuthTypeValueService;

    // 注入扩展业务逻辑
    @Autowired
    private CorporationServiceCust serviceCust;

    @Override
    protected BaseEntityDao<Corporation> getDao() {
        return corporationDao;
    }

    /**
     * 创建数据保存数据之前额外操作回调方法 默认为空逻辑，子类根据需要覆写添加逻辑即可
     *
     * @param entity 待创建数据对象
     */
    @Override
    protected OperateResultWithData<Corporation> preInsert(Corporation entity) {
        // 检查税号是否已关联其他公司
        String taxNo = entity.getTaxNo();
        if (StringUtils.isNotBlank(taxNo)) {
            Corporation corp = this.findByTaxNo(taxNo);
            if  (Objects.nonNull(corp)) {
                // 税号[{0}]已关联[{1}]
                return OperateResultWithData.operationFailure("00111", taxNo, corp.getName());
            }
        }
        return super.preInsert(entity);
    }

    /**
     * 更新数据保存数据之前额外操作回调方法 默认为空逻辑，子类根据需要覆写添加逻辑即可
     *
     * @param entity 待更新数据对象
     */
    @Override
    protected OperateResultWithData<Corporation> preUpdate(Corporation entity) {
        // 检查税号是否已关联其他公司
        String taxNo = entity.getTaxNo();
        if (StringUtils.isNotBlank(taxNo)) {
            Corporation corp = this.findByTaxNo(taxNo);
            if  (Objects.nonNull(corp) && !Objects.equals(corp.getId(), entity.getId())) {
                // 税号[{0}]已关联[{1}]
                return OperateResultWithData.operationFailure("00111", taxNo, corp.getName());
            }
        }
        return super.preUpdate(entity);
    }

    /**
     * 根据公司代码查询公司
     *
     * @param code 公司代码
     * @return 公司
     */
    public Corporation findByCode(String code) {
        Corporation corporation = corporationDao.findByCodeAndTenantCode(code, ContextUtil.getTenantCode());
        // 执行扩展业务逻辑
        return serviceCust.afterFindByCode(corporation);
    }

    /**
     * 根据ERP公司代码查询公司
     *
     * @param erpCode ERP公司代码
     * @return 公司
     */
    public List<Corporation> findByErpCode(String erpCode) {
        return corporationDao.findByErpCodeAndTenantCode(erpCode, ContextUtil.getTenantCode());
    }

    /**
     * 根据纳税人识别号查询公司
     *
     * @param taxNo 纳税人识别号(税号)
     * @return 公司
     */
    public Corporation findByTaxNo(String taxNo) {
        return corporationDao.findFirstByProperty(Corporation.FIELD_TAX_NO, taxNo);
    }

    /**
     * 从平台基础应用获取一般用户有权限的数据实体Id清单
     * 对于数据权限对象的业务实体，需要override，使用BASIC提供的通用工具来获取
     *
     * @param entityClassName 权限对象实体类型
     * @param featureCode     功能项代码
     * @param userId          用户Id
     * @return 数据实体Id清单
     */
    @Override
    public List<String> getNormalUserAuthorizedEntitiesFromBasic(String entityClassName, String featureCode, String userId) {
        return userService.getNormalUserAuthorizedEntities(entityClassName, featureCode, userId);
    }


    /**
     * 根据组织机构Id查询公司
     *
     * @param organizationId 组织机构Id
     * @return 公司
     */
    public Corporation findByOrganizationId(String organizationId) {
        List<Corporation> corporationList = findAllUnfrozen();
        if (CollectionUtils.isNotEmpty(corporationList)) {
            return findByOrgTree(organizationId, corporationList);
        }
        return null;
    }


    /**
     * 根据组织机构树往上查找公司
     *
     * @param organizationId  组织机构树开始节点
     * @param corporationList 所有公司
     * @return
     */
    private Corporation findByOrgTree(String organizationId, List<Corporation> corporationList) {
        Corporation corporation = corporationList.parallelStream().filter(i -> Objects.equals(i.getOrganizationId(), organizationId)).findFirst().orElse(null);
        if (Objects.isNull(corporation)) {
            Organization organization = organizationService.findOne(organizationId);
            if (Objects.nonNull(organization) && StringUtils.isNoneBlank(organization.getParentId())) {
                corporation = findByOrgTree(organization.getParentId(), corporationList);
            }
        }
        return corporation;
    }

    /**
     * 删除数据保存数据之前额外操作回调方法 子类根据需要覆写添加逻辑即可
     *
     * @param id 公司Id标识
     */
    @Override
    protected OperateResult preDelete(String id) {
        // 检查数据权限值是否已经使用
        if (dataRoleAuthTypeValueService.isExistsByProperty("entityId", id)) {
            // 公司已经使用，禁止删除！
            return OperateResult.operationFailure("00123");
        }
        return super.preDelete(id);
    }
}
