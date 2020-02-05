package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.DataRoleGroupDto;
import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindAllApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 实现功能: 数据角色组API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-20 15:03
 */
@FeignClient(name = "sei-basic", path = "dataRoleGroup")
@RequestMapping(path = "dataRoleGroup", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface DataRoleGroupApi extends BaseEntityApi<DataRoleGroupDto>,
        FindAllApi<DataRoleGroupDto> {
}
