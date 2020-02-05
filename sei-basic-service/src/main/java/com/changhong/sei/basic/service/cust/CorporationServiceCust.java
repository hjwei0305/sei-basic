package com.changhong.sei.basic.service.cust;

import com.changhong.sei.basic.entity.Corporation;

/**
 * 实现功能: 公司业务逻辑扩展接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-26 12:20
 */
public interface CorporationServiceCust {
    /**
     * 根据公司代码查询公司后，对公司的数据处理（无事务控制）
     * @param corporation 查询结果
     * @return  扩展处理结果
     */
    Corporation afterFindByCode(Corporation corporation);
}
