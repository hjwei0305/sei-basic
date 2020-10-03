package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.ProfessionalDomainApi;
import com.changhong.sei.basic.dto.ProfessionalDomainDto;
import com.changhong.sei.basic.entity.ProfessionalDomain;
import com.changhong.sei.basic.service.ProfessionalDomainService;
import com.changhong.sei.core.controller.BaseTreeController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseTreeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 实现功能: 专业领域API服务
 *
 * @author 王锦光 wangjg
 * @version 2020-01-29 22:39
 */
@RestController
@Api(value = "ProfessionalDomainApi", tags = "专业领域API服务")
@RequestMapping(path = "professionalDomain", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ProfessionalDomainController extends BaseTreeController<ProfessionalDomain, ProfessionalDomainDto>
        implements ProfessionalDomainApi {
    @Autowired
    private ProfessionalDomainService service;
    /**
     * 获取整个领域树
     *
     * @return 领域树形对象集合
     */
    @Override
    public ResultData<List<ProfessionalDomainDto>> getDomainTree() {
        return ResultData.success(convertToDtos(service.getDomainTree()));
    }

    @Override
    public BaseTreeService<ProfessionalDomain> getService() {
        return service;
    }
}
