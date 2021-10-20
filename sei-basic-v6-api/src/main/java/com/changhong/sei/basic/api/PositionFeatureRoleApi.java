package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.FeatureRoleDto;
import com.changhong.sei.basic.dto.PositionDto;
import com.changhong.sei.basic.dto.PositionFeatureRoleDto;
import com.changhong.sei.core.api.BaseRelationApi;
import com.changhong.sei.core.dto.ParentRelationParam;
import com.changhong.sei.core.dto.ResultData;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 实现功能: 岗位分配的功能角色API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-29 10:09
 */
@FeignClient(name = "sei-basic", path = "positionFeatureRole")
public interface PositionFeatureRoleApi extends BaseRelationApi<PositionFeatureRoleDto, PositionDto, FeatureRoleDto> {
    /**
     * 通过父实体清单保存分配关系
     *
     * @param relationParam   父实体Id清单的分配参数
     * @return 操作结果
     */
    @PostMapping(path = "saveRelationsByParents")
    @ApiOperation(value = "通过父实体清单保存分配关系", notes = "通过父实体清单保存分配关系，将会覆盖现有的分配关系")
    ResultData<?> saveRelationsByParents(@RequestBody ParentRelationParam relationParam);
}
