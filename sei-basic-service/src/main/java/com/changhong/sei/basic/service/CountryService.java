package com.changhong.sei.basic.service;

import com.changhong.sei.basic.dao.CountryDao;
import com.changhong.sei.basic.dao.RegionDao;
import com.changhong.sei.basic.entity.Country;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p/>
 * 实现功能：国家的业务逻辑
 * <p/>
 *
 * @author 豆
 * @version 1.0.00
 */
@Service
public class CountryService extends BaseEntityService<Country> {

    @Autowired
    private CountryDao dao;
    @Autowired
    private RegionDao regionDao;

    @Override
    protected BaseEntityDao<Country> getDao() {
        return dao;
    }

    /**
     * 删除前检查是否能删除
     *
     * @param s 待删除的国家id
     * @return 操作结果
     */
    @Override
    protected OperateResult preDelete(String s) {
        OperateResult result = null;
        if (regionDao.isExistsByProperty("countryId", s)) {
            //该国家下存在行政区域，禁止删除！
            result = OperateResult.operationFailure("00049");
        }
        return result;
    }


    /**
     * 根据代码查询国家
     * 
     * @param code 代码
     * @return 国家信息
     */
	public Country findByCode(String code) {
		if(StringUtils.isBlank(code)) {
			return null;
		}
		return super.findByProperty(Country.CODE_FIELD, code);
	}
}
