package com.changhong.sei.basic.service;

import com.changhong.sei.basic.api.ProfessionalDomainService;
import com.changhong.sei.basic.dto.ProfessionalDomainDto;
import com.changhong.sei.basic.entity.ProfessionalDomain;
import com.changhong.sei.basic.manager.ProfessionalDomainManager;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.manager.BaseTreeManager;
import com.changhong.sei.core.service.DefaultTreeService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 实现功能: 专业领域API服务
 *
 * @author 王锦光 wangjg
 * @version 2020-01-29 22:39
 */
@Service
@Api(value = "ProfessionalDomainService", tags = "专业领域API服务")
public class ProfessionalDomainServiceImpl implements DefaultTreeService<ProfessionalDomain, ProfessionalDomainDto>,
        ProfessionalDomainService {
    @Autowired
    private ProfessionalDomainManager manager;
    @Autowired
    private ModelMapper modelMapper;
    /**
     * 获取整个领域树
     *
     * @return 领域树形对象集合
     */
    @Override
    public ResultData<List<ProfessionalDomainDto>> getDomainTree() {
        return ResultData.success(convertToDtos(manager.getDomainTree()));
    }

    @Override
    public BaseTreeManager<ProfessionalDomain> getManager() {
        return manager;
    }

    @Override
    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    /**
     * 获取数据实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<ProfessionalDomain> getEntityClass() {
        return ProfessionalDomain.class;
    }

    /**
     * 获取传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<ProfessionalDomainDto> getDtoClass() {
        return ProfessionalDomainDto.class;
    }
}
