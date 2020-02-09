package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.ProfessionalDomainDto;
import com.changhong.sei.core.api.BaseTreeApi;
import com.changhong.sei.core.dto.ResultData;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 实现功能: 专业领域API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-29 22:37
 */
@FeignClient(name = "sei-basic", path = "professionalDomain")
public interface ProfessionalDomainApi extends BaseTreeApi<ProfessionalDomainDto> {
    /**
     * 获取整个领域树
     *
     * @return 领域树形对象集合
     */
    @GetMapping(path = "getDomainTree")
    @ApiOperation(notes = "查询所有的领域树", value = "查询所有的领域树")
    ResultData<List<ProfessionalDomainDto>> getDomainTree();
}
