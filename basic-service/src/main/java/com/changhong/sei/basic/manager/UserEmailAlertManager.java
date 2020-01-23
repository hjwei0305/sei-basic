package com.changhong.sei.basic.manager;

import com.changhong.sei.basic.dao.UserEmailAlertDao;
import com.changhong.sei.basic.entity.UserEmailAlert;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.manager.BaseEntityManager;
import com.changhong.sei.core.manager.bo.OperateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by WangShuFa on 2018/7/11.
 */
@Component
public class UserEmailAlertManager extends BaseEntityManager<UserEmailAlert> {
    @Autowired
    private UserEmailAlertDao userEmailAlertDao;

    @Override
    protected BaseEntityDao<UserEmailAlert> getDao() {
        return userEmailAlertDao;
    }

    public List<UserEmailAlert> findByUserIds(List<String> userIdS) {
        Search search=new Search();
        search.addFilter(new SearchFilter("userId", userIdS, SearchFilter.Operator.IN));
        List<UserEmailAlert> userEmailAlerts = userEmailAlertDao.findByFilters(search);
        if(Objects.isNull(userEmailAlerts) || userEmailAlerts.isEmpty()){
            return Collections.emptyList();
        }
        List<UserEmailAlert> userEmailAlerts1 = userEmailAlerts.stream().filter(r->r.getHours()>0 || r.getToDoAmount()>0).collect(Collectors.toList());
        return userEmailAlerts1;
    }

    public OperateResult updateLastTimes(List<String> userIds) {
        List<UserEmailAlert> userEmailAlertList=findByUserIds(userIds);
        for(UserEmailAlert userEmailAlert: userEmailAlertList){
            userEmailAlert.setLastTime(new Date());
            super.preUpdate(userEmailAlert);
        }
        userEmailAlertDao.saveAll(userEmailAlertList);
        return OperateResult.operationSuccess();
    }

    /**
     * 保存
     *
     * @param entities
     */
    @Override
    public void save(Collection<UserEmailAlert> entities) {
        String userId = ContextUtil.getUserId();
        if (!StringUtils.isEmpty(userId)) {
            //userid为空则填充为当前用户id
            for (UserEmailAlert entity : entities) {
                if (StringUtils.isEmpty(entity.getUserId())) {
                    entity.setUserId(userId);
                }
            }
        }
        super.save(entities);
    }

    /**
     * 获取当前用户邮件通知列表
     *
     * @return
     */
    public List<UserEmailAlert> findByUserIds() {
        String userId = ContextUtil.getUserId();
        if (StringUtils.isEmpty(userId)) {
            return new ArrayList<>();
        }
        return this.findByUserIds(Arrays.asList(userId));
    }
}
