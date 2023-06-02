package com.changhong.sei.basic.service;

import com.changhong.sei.basic.dao.*;
import com.changhong.sei.basic.dto.SupplierUserVo;
import com.changhong.sei.basic.entity.FeatureRole;
import com.changhong.sei.basic.entity.SupplierUser;
import com.changhong.sei.basic.entity.User;
import com.changhong.sei.basic.entity.UserProfile;
import com.changhong.sei.basic.service.client.AccountManager;
import com.changhong.sei.basic.service.client.dto.CreateAccountRequest;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.entity.ICodeUnique;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.enums.UserAuthorityPolicy;
import com.changhong.sei.enums.UserType;
import com.changhong.sei.util.IdGenerator;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author 马超(Vision.Mac)
 * @version 1.0.1 2018/3/6 20:26
 */
@Service
public class SupplierUserService extends BaseEntityService<SupplierUser> {
    @Autowired
    private SupplierUserDao supplierUserDao;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private UserProfileDao userProfileDao;
    @Autowired
    private FeatureRoleService featureRoleService;
    @Autowired
    private UserFeatureRoleService userFeatureRoleService;
    @Autowired
    private UserFeatureRoleDao userFeatureRoleDao;
    @Autowired
    private UserDataRoleDao userDataRoleDao;
    @Autowired
    private AccountManager accountManager;

    @Override
    protected BaseEntityDao<SupplierUser> getDao() {
        return supplierUserDao;
    }

    /**
     * 保存供应商
     *
     * @param supplierUserVo 实体
     * @return 返回操作对象
     */
    @Transactional
    public OperateResultWithData<SupplierUserVo> saveSupplierUserVo(SupplierUserVo supplierUserVo) {
        //密码默认为：123456
        String password = DigestUtils.md5Hex("123456");
        supplierUserVo.setPassword(DigestUtils.md5Hex(password));  //加密
        return saveSupplierUser(supplierUserVo);
    }

    /**
     * 保存供应商(外部提供加密后的密码)
     *
     * @param supplierUserVo 实体
     * @return 返回操作对象
     */
    @Transactional
    public OperateResultWithData<SupplierUserVo> saveSupplierUserVoWithPassword(SupplierUserVo supplierUserVo) {
        return saveSupplierUser(supplierUserVo);
    }

    /**
     * 保存供应商(外部调用使用{ SupplierUserService.saveSupplierUserVo})
     *
     * @param supplierUserVo 实体
     * @return 返回操作对象
     */
    @Transactional
    protected OperateResultWithData<SupplierUserVo> saveSupplierUser(SupplierUserVo supplierUserVo) {
        String supplierUserId = supplierUserVo.getId();
        boolean isNew = StringUtils.isBlank(supplierUserId);
        //检查该租户下供应商用户编号不能重复
        boolean isCodeExists = supplierUserDao.isCodeExists(ContextUtil.getTenantCode(), supplierUserVo.getCode(), StringUtils.isBlank(supplierUserId) ? IdGenerator.uuid() : supplierUserId);
        if (isCodeExists) {
            //00054 = 供应商用户编号【{0}】已存在，请重新输入！
            return OperateResultWithData.operationFailure("00054", supplierUserVo.getCode());
        }
        User user;
        SupplierUser supplierUser;
        if (isNew) {
            //新增用户
            user = new User();
            user.setUserName(supplierUserVo.getName());
            user.setUserType(UserType.Supplier);
            user.setTenantCode(ContextUtil.getTenantCode());
            user.setUserAuthorityPolicy(UserAuthorityPolicy.NormalUser);
            user.setFrozen(supplierUserVo.getFrozen());
            userDao.save(user);

            //新增用户配置
            UserProfile userProfile = new UserProfile();
            userProfile.setUser(user);
            userProfile.setEmail(supplierUserVo.getEmail());
            userProfile.setMobile(supplierUserVo.getMobile());
            userProfile.setUserId(user.getId());
            userProfileDao.save(userProfile);

            //新增供应商用户
            supplierUser = new SupplierUser();
            supplierUser.setId(user.getId());
            supplierUser.setCode(supplierUserVo.getCode());
            supplierUser.setName(supplierUserVo.getName());
            //申请注册供应商用户需要保存
            supplierUser.setSupplierApplyId(supplierUserVo.getSupplierApplyId());
            supplierUser.setUser(user);
            //如果不是创建供应商管理员
           /* if (supplierUserVo.isCreateSupplierManager()) {
                supplierUser.setSupplierId(supplierUserVo.getSupplierId());
                supplierUser.setSupplierCode(supplierUserVo.getSupplierCode());
                supplierUser.setSupplierName(supplierUserVo.getSupplierName());
            } else {
                SupplierUser supplierUserFromUserId = this.getSupplierUser(ContextUtil.getUserId());
                if (Objects.nonNull(supplierUserFromUserId)) {
                    supplierUser.setSupplierId(supplierUserFromUserId.getSupplierId());
                    supplierUser.setSupplierCode(supplierUserFromUserId.getSupplierCode());
                    supplierUser.setSupplierName(supplierUserFromUserId.getSupplierName());
                } else {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    //当前用户类型不是供应商！
                    return OperateResultWithData.operationFailure("00060", supplierUserVo.getCode());
                }
            }*/
            supplierUser = supplierUserDao.save(supplierUser, true);
            //返回
            SupplierUserVo vo = new SupplierUserVo();
            try {
                PropertyUtils.copyProperties(vo, supplierUser);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException("数据处理错误");
            }


            // 创建用户账户
            CreateAccountRequest accountRequest = new CreateAccountRequest();
            // 员工编号作为账号
            accountRequest.setTenantCode(supplierUser.getTenantCode());
            accountRequest.setAccount(supplierUser.getCode());
            accountRequest.setUserId(supplierUser.getId());
            accountRequest.setName(supplierUser.getName());
            accountRequest.setAccountType(UserType.Supplier.name());
            accountRequest.setAuthorityPolicy(UserAuthorityPolicy.NormalUser.name());
            accountRequest.setMobile(userProfile.getMobile());
            accountRequest.setEmail(userProfile.getEmail());
            accountRequest.setGender(userProfile.getGender());
            accountRequest.setIdCard(userProfile.getIdCard());
            accountRequest.setLanguageCode(userProfile.getLanguageCode());
            accountRequest.setFrozen(supplierUserVo.isFrozen());

            accountManager.create(accountRequest);

            return OperateResultWithData.operationSuccessWithData(vo, "core_service_00026");
        } else {
            //修改用户
            user = userDao.getById(supplierUserId);
            user.setUserName(supplierUserVo.getName());
            user.setFrozen(supplierUserVo.getFrozen());
            userDao.save(user);

            //修改用户配置
            UserProfile userProfile = userProfileDao.findByUserId(supplierUserId);
            userProfile.setEmail(supplierUserVo.getEmail());
            userProfile.setGender(supplierUserVo.getGender());
            userProfile.setMobile(supplierUserVo.getMobile());
            userProfile.setIdCard(supplierUserVo.getIdNumber());
            userProfile.setUserId(user.getId());
            userProfile.setUser(user);

            userProfileDao.save(userProfile);
            //修改用户账户,设置可以修改的属性
            SupplierUser supplierUserFromDb = supplierUserDao.findOne(supplierUserId);
            //修改专家用户
//            supplierUserFromDb.setCode(supplierUserVo.getCode());
            supplierUserFromDb.setName(supplierUserVo.getName());
            supplierUserFromDb.setSupplierCode(supplierUserVo.getSupplierCode());
            supplierUserFromDb.setUser(user);
            supplierUser = supplierUserDao.save(supplierUserFromDb, false);
            //返回
            SupplierUserVo vo = new SupplierUserVo();
            try {
                PropertyUtils.copyProperties(vo, supplierUser);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException("数据处理错误");
            }
            return OperateResultWithData.operationSuccessWithData(vo, "core_service_00027");
        }
    }

    /**
     * 根据当前登录人（供应商管理员）获取他的供应商
     *
     * @param userId 当前登录人（供应商管理员）
     * @return 供应商
     */
    private SupplierUser getSupplierUser(String userId) {
        return supplierUserDao.findOne(userId);
    }

    /**
     * 删除供应商用户
     *
     * @param s 供应商用户ID
     * @return 操作结果
     */
    @Override
    @Transactional
    public OperateResult delete(String s) {
        SupplierUser supplier = supplierUserDao.findOne(s);
        if (Objects.isNull(supplier)) {
            //00055 = 您操作的数据不存在，操作失败！
            return OperateResult.operationFailure("00055");
        }
        //移除角色
        removeRoles(s);
        //删除供应商用户
        super.delete(s);
        //删除用户配置
        userProfileService.delete(userProfileService.findByUserId(s).getId());
        //删除用户
        userService.delete(s);
        return OperateResult.operationSuccess("core_00003");
    }

    /**
     * 移除角色
     *
     * @param s 专家用户ID
     */

    private void removeRoles(String s) {
        //移除功能角色
        List<String> featureRoleIds = userFeatureRoleDao.getRelationIdsByParentId(s);
        userFeatureRoleDao.delete(featureRoleIds);

        //移除数据角色
        List<String> dataRoleIds = userDataRoleDao.getRelationIdsByParentId(s);
        userDataRoleDao.delete(dataRoleIds);
    }

    /**
     * 分页查询
     *
     * @param searchConfig 查询参数
     * @return 分页数据
     */
    public PageResult<SupplierUserVo> findVoByPage(Search searchConfig) {
        PageResult<SupplierUser> pageResult = super.findByPage(searchConfig);
        PageResult<SupplierUserVo> page;
        List<SupplierUserVo> voList = new ArrayList<>();
        ArrayList<SupplierUser> resultRows = pageResult.getRows();
        if (resultRows.isEmpty()) {
            page = new PageResult<>();
            page.setRows(voList);
            return page;
        }
        for (SupplierUser supplier : resultRows) {
            SupplierUserVo supplierUserVo = new SupplierUserVo();
            try {
                PropertyUtils.copyProperties(supplierUserVo, supplier);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException("数据处理错误");
            }
            supplierUserVo.setFrozen(supplier.getUser().getFrozen());
            voList.add(supplierUserVo);
        }
        page = new PageResult<>(pageResult);
        page.setRows(voList);
        return page;
    }

    /**
     * 保存供应商管理员
     *
     * @param supplierUserVo 供应商信息
     * @param roleCode       角色代码的KEY
     * @return 操作结果
     */
    @Transactional
    public OperateResult saveSupplierManager(SupplierUserVo supplierUserVo, String roleCode) {
        OperateResultWithData<String> result = this.saveSupplierManagerBackId(supplierUserVo, roleCode);
        if (result.successful()) {
            return OperateResult.operationSuccess(result.getMessage());
        } else {
            return OperateResult.operationFailure(result.getMessage());
        }
    }

    /**
     * 保存供应商管理员
     *
     * @param supplierUserVo 供应商信息
     * @param roleCode       角色代码的KEY
     * @return 操作结果
     */
    @Transactional
    public OperateResultWithData<String> saveSupplierManagerBackId(SupplierUserVo supplierUserVo, String roleCode) {
        //修改供应商
        if (supplierUserVo.isEditSupplier()) {
            OperateResult result = this.editSupplierUser(supplierUserVo, roleCode);
            if (result.successful()) {
                return OperateResultWithData.operationSuccess(result.getMessage());
            } else {
                return OperateResultWithData.operationFailure(result.getMessage());
            }
        }
        //新增供应商用户
        OperateResultWithData<SupplierUserVo> resultWithData = this.saveSupplierUserVo(supplierUserVo);
        String supplierUserId;
        if (resultWithData.successful()) {
            SupplierUserVo savedSupplierVo = resultWithData.getData();
            supplierUserId = savedSupplierVo.getId();
            //如果角色有问题，未能成功分配角色，则需要管理员手动分配角色
            FeatureRole featureRole = featureRoleService.findByProperty(ICodeUnique.CODE_FIELD, roleCode);
            if (Objects.isNull(featureRole)) {
                //00056 = 供应商用户未配置功能角色，请联系管理员！
                return OperateResultWithData.operationFailure("00056");
//                throw new RuntimeException(ContextUtil.getMessage("00056"));
            }
            //提交事务，否则无法分配用户角色
            TransactionAspectSupport.currentTransactionStatus().flush();

            List<String> featureRoleIds = new ArrayList<>();
            featureRoleIds.add(featureRole.getId());
            OperateResult operateResult = userFeatureRoleService.insertRelations(supplierUserId, featureRoleIds);
            //保存不成功，返回提示消息
            if (operateResult.notSuccessful()) {
                //00061 = 保存供应商用户成功，但是分配角色失败，请联系管理员手动分配！
                return OperateResultWithData.operationFailure("00061");
            }
        } else {
            //00062 = 供应商用户创建失败！
            return OperateResultWithData.operationFailure("00062");
        }
        //00063 = 供应商用户创建成功！
        return OperateResultWithData.operationSuccessWithData(supplierUserId, "00063");

    }

    private OperateResult editSupplierUser(SupplierUserVo supplierUserVo, String roleCode) {
        String supplierId = supplierUserVo.getSupplierId();
        List<SupplierUser> supplierUserList = supplierUserDao.findListByProperty(SupplierUser.SUPPLIER_ID, supplierId);
        if (!supplierUserList.isEmpty()) {
            for (SupplierUser supplierUser : supplierUserList) {
                supplierUser.setSupplierName(supplierUserVo.getSupplierName());
            }
            supplierUserDao.save(supplierUserList);
            //提交事务，否则无法分配用户角色
            TransactionAspectSupport.currentTransactionStatus().flush();
            //编辑成功后为供应商管理员增加功能角色
            return addRole(supplierId, roleCode);
        }
        return OperateResult.operationSuccess();
    }

    public OperateResult addSupplierIdToSupUser(SupplierUserVo supplierUserVo) {
        List<SupplierUser> supplierUserList = supplierUserDao.findListByProperty(SupplierUser.SUPPLIER_APPLY_ID, supplierUserVo.getSupplierApplyId());
        if (!supplierUserList.isEmpty()) {
            supplierUserList.forEach(supplierUser -> supplierUser.setSupplierId(supplierUserVo.getSupplierId()));
            supplierUserDao.save(supplierUserList);
        }
        return OperateResult.operationSuccess();
    }

    /**
     * 根据供应商的ID查询供应商用户
     *
     * @param supplierId 供应商ID
     * @return 供应商用户
     */
    public List<SupplierUser> findBySupplierId(String supplierId) {
        if (StringUtils.isBlank(supplierId)) {
            return Collections.emptyList();
        }
        return findListByProperty(SupplierUser.SUPPLIER_ID, supplierId);
    }

    /**
     * @return
     */
    public OperateResult saveApplySupUser(SupplierUserVo supplierUserVo) {
        return OperateResult.operationSuccess();
    }

    public OperateResult addRole(String supplierId, String featureRoleCode) {
        if (StringUtils.isBlank(featureRoleCode)) {
            //00056 = 供应商用户未配置功能角色，请联系管理员！
            return OperateResult.operationFailure("00056");
        }
        FeatureRole featureRole = featureRoleService.findByProperty(ICodeUnique.CODE_FIELD, featureRoleCode);
        if (Objects.isNull(featureRole)) {
            //00056 = 供应商用户未配置功能角色，请联系管理员！
            return OperateResult.operationFailure("00056");
        }
        List<String> featureRoleIds = new ArrayList<>();
        featureRoleIds.add(featureRole.getId());
        OperateResult operateResult = userFeatureRoleService.insertRelations(supplierId, featureRoleIds);
        if (operateResult.notSuccessful()) {
            //00061 = 保存供应商用户成功，但是分配角色失败，请联系管理员手动分配！
            return OperateResult.operationFailure("00061");
        }
        return OperateResult.operationSuccess();
    }
}
