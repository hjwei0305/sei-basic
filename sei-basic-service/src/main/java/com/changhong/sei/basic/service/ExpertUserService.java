package com.changhong.sei.basic.service;

import com.changhong.sei.basic.dao.*;
import com.changhong.sei.basic.dto.ExpertUserVo;
import com.changhong.sei.basic.entity.ExpertUser;
import com.changhong.sei.basic.entity.ProfessionalDomain;
import com.changhong.sei.basic.entity.User;
import com.changhong.sei.basic.entity.UserProfile;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.enums.UserAuthorityPolicy;
import com.changhong.sei.enums.UserType;
import com.changhong.sei.util.IdGenerator;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author 马超(Vision.Mac)
 * @version 1.0.1 2018/3/6 20:26
 */
@Service
public class ExpertUserService extends BaseEntityService<ExpertUser> {
    @Autowired
    private ExpertUserDao expertUserDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserProfileDao userProfileDao;
    @Autowired
    private UserFeatureRoleDao userFeatureRoleDao;
    @Autowired
    private UserDataRoleDao userDataRoleDao;
    @Autowired
    private ExpertUserProfessionalDomainService expertUserProfessionalDomainService;

    @Override
    protected BaseEntityDao<ExpertUser> getDao() {
        return expertUserDao;
    }

    /**
     * 保存专家用户
     *
     * @param expertUserVo 专家用户
     * @return 操作结果
     */
    @Transactional
    public OperateResult save(ExpertUserVo expertUserVo) {
        ExpertUser expertUserFromExpertId = expertUserDao.findByExpertId(expertUserVo.getExpertId());
        if(!Objects.isNull(expertUserFromExpertId)){
            expertUserVo.setId(expertUserFromExpertId.getId());
        }
        String expertUserId = expertUserVo.getId();
        boolean isNew = StringUtils.isBlank(expertUserId);
        //检查该租户下专家用户编号不能重复
        boolean isCodeExists = expertUserDao.isCodeExists(ContextUtil.getTenantCode(), expertUserVo.getCode(), StringUtils.isBlank(expertUserId) ? IdGenerator.uuid() : expertUserId);
        if (isCodeExists) {
            //00058 = 专家用户编号【{0}】已存在，请重新输入！
            return OperateResult.operationFailure("00058", expertUserVo.getCode());
        }
        User user;
        ExpertUser expertUser;
        if (isNew) {
            //新增用户
            user = new User();
            user.setUserName(expertUserVo.getName());
            user.setUserType(UserType.Expert);
            user.setTenantCode(ContextUtil.getTenantCode());
            user.setUserAuthorityPolicy(UserAuthorityPolicy.NormalUser);
            user.setFrozen(expertUserVo.getFrozen());
            userDao.save(user);

            //新增用户配置
            UserProfile userProfile = new UserProfile();
            userProfile.setUser(user);
            userProfile.setEmail(expertUserVo.getEmail());
            userProfile.setGender(expertUserVo.getGender());
            userProfile.setIdCard(expertUserVo.getIdCard());
            userProfile.setMobile(expertUserVo.getMobile());
            userProfileDao.save(userProfile);

            //新增专家用户
            expertUser = new ExpertUser();
            expertUser.setId(user.getId());
            expertUser.setCode(expertUserVo.getCode());
            expertUser.setName(expertUserVo.getName());
            expertUser.setExpireDate(expertUserVo.getExpireDate());
            expertUser.setExpertId(expertUserVo.getExpertId());
            expertUser.setUser(user);
            expertUser = expertUserDao.save(expertUser, true);

            //提交事务，否则无法分配用户角色
            TransactionAspectSupport.currentTransactionStatus().flush();
            //为新增的专家用户分配领域
            expertUserProfessionalDomainService.saveRelations(expertUser.getId(), expertUserVo.getProfessionalDomainIds());

            //返回
            return OperateResult.operationSuccess("core_00001");
        } else {
            //修改用户
            user = userDao.getById(expertUserId);
            user.setUserName(expertUserVo.getName());
            user.setFrozen(expertUserVo.getFrozen());
            userDao.save(user);

            //修改用户配置
            UserProfile userProfile = userProfileDao.findByUserId(expertUserId);
            userProfile.setUser(user);
            userProfile.setEmail(expertUserVo.getEmail());
            userProfile.setGender(expertUserVo.getGender());
            userProfile.setIdCard(expertUserVo.getIdCard());
            userProfile.setMobile(expertUserVo.getMobile());
            userProfileDao.save(userProfile);

            //修改用户账户,设置可以修改的属性
            ExpertUser expertUserFromDb = expertUserDao.findOne(expertUserId);
            //修改专家用户
            expertUserFromDb.setName(expertUserVo.getName());
            expertUserFromDb.setExpireDate(expertUserVo.getExpireDate());
            expertUserFromDb.setUser(user);
            expertUserDao.save(expertUserFromDb, false);

            //提交事务，否则无法分配用户角色
            TransactionAspectSupport.currentTransactionStatus().flush();
            //修改专家的领域
            expertUserProfessionalDomainService.saveRelations(expertUserFromDb.getId(), expertUserVo.getProfessionalDomainIds());
            //返回
            return OperateResult.operationSuccess("core_00002");
        }

    }

    /**
     * 根据专家ID删除业务实体
     *
     * @param s 专家ID
     * @return 操作结果
     */
    @Override
    @Transactional
    public OperateResult delete(String s) {
        ExpertUser expertUser = expertUserDao.findOne(s);
        if (Objects.isNull(expertUser)) {
            //00055 = 您操作的数据不存在，操作失败！
            return OperateResult.operationFailure("00055");
        }
        //移除角色
        removeRoles(s);
        //删除供应商用户
        expertUserDao.delete(s);
        //删除用户配置
        userProfileDao.delete(userProfileDao.findByUserId(s).getId());
        //删除用户
        userDao.delete(s);
        return OperateResult.operationSuccess("core_00003");
    }

    /**
     * 根据专家用户中的专家的ID删除业务实体
     *
     * @param expertId 专家用户中的专家的ID
     * @return 操作结果
     */
    @Transactional
    public OperateResult deleteByExpertId(String expertId) {
        ExpertUser expertUser = expertUserDao.findByExpertId(expertId);
        if (Objects.isNull(expertUser)) {
            //00055 = 您操作的数据不存在，操作失败！
            return OperateResult.operationFailure("00055");
        }
        return this.delete(expertUser.getId());
    }

    /**
     * 重写findByPage方法，将冻结属性附值
     *
     * @param searchConfig 查询参数
     * @return 分页数据
     */
    public PageResult<ExpertUserVo> findVoByPage(Search searchConfig) {
        PageResult<ExpertUser> pageResult = super.findByPage(searchConfig);
        PageResult<ExpertUserVo> page ;
        List<ExpertUserVo> voList = new ArrayList<>();
        ArrayList<ExpertUser> resultRows = pageResult.getRows();
        if (resultRows.isEmpty()) {
            page = new PageResult<>();
            page.setRows(voList);
            return page;
        }
        for (ExpertUser expertUser : resultRows) {
            ExpertUserVo expertUserVo = new ExpertUserVo();
            try {
                PropertyUtils.copyProperties(expertUserVo, expertUser);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException("数据处理错误");
            }
            expertUserVo.setFrozen(expertUser.getUser().getFrozen());
            //设置领域
            List<ProfessionalDomain> professionalDomainList = expertUserProfessionalDomainService.getChildrenFromParentId(expertUser.getId());
            StringBuilder professionalDomainName = new StringBuilder();
            if (CollectionUtils.isNotEmpty(professionalDomainList)){
                for (ProfessionalDomain professionalDomain : professionalDomainList) {
                    professionalDomainName.append(professionalDomain.getName());
                    professionalDomainName.append("/");
                }
                expertUserVo.setProfessionalDomainName(professionalDomainName.substring(0, professionalDomainName.length() - 1));
            }
            voList.add(expertUserVo);
        }
        page = new PageResult<>(pageResult);
        page.setRows(voList);
        return page;
    }

    /**
     * 通过ID将该实体冻结/解冻
     *
     * @param id     实体ID
     * @param frozen 是否冻结，是true,否false
     * @return 操作结果
     */
    @Transactional
    public OperateResult freeze(String id, Boolean frozen) {
        User user = userDao.findOne(id);
        if (Objects.isNull(user)) {
            //00055 = 您操作的数据不存在，请仔细核对！
            return OperateResult.operationFailure("00055");
        }
        user.setFrozen(frozen);
        userDao.save(user);
        return OperateResult.operationSuccess();
    }

    /**
     * 通过专家用户中专家的ID将该实体冻结/解冻
     *
     * @param expertId 实体ID
     * @param frozen   是否冻结，是true,否false
     * @return 操作结果
     */
    @Transactional
    public OperateResult freezeByExpertId(String expertId, Boolean frozen) {
        ExpertUser expertUser = expertUserDao.findByExpertId(expertId);
        if (Objects.isNull(expertUser)) {
            //00055 = 您操作的数据不存在，请仔细核对！
            return OperateResult.operationFailure("00055");
        }
        return this.freeze(expertUser.getId(), frozen);
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
}
