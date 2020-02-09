package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.DataDictDto;
import com.changhong.sei.basic.dto.DataDictItemDto;
import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindByPageApi;
import com.changhong.sei.core.dto.IDataDict;
import com.changhong.sei.core.dto.ResultData;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 实现功能: 数据字典API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-26 19:55
 */
@FeignClient(name = "sei-basic", path = "dataDict")
public interface DataDictApi extends BaseEntityApi<DataDictDto>,
        FindByPageApi<DataDictDto> {
    /**
     * 保存字典项目
     *
     * @param dictItemDto 字典项目
     * @return 返回保存字典项目的结果
     */
    @PostMapping(path = "saveDictItem")
    @ApiOperation(value = "保存字典项目", notes = "保存字典项目")
    ResultData<DataDictItemDto> saveDictItem(@RequestBody @Valid DataDictItemDto dictItemDto);

    /**
     * 删除字典项目
     *
     * @param id 字典项目id
     * @return 返回保存字典项目的结果
     */
    @DeleteMapping(path = "deleteDictItem")
    @ApiOperation(value = "删除字典项目", notes = "删除字典项目")
    ResultData deleteDictItem(@RequestParam("id") String id);

    /**
     * 根据字段类别代码，获取字典项目
     *
     * @param categoryCode 字典类别代码
     * @return 返回当前类别下的字典项目
     */
    @GetMapping(path = "getDataDictItems")
    @ApiOperation(value = "根据字段类别代码，获取字典项目", notes = "根据字段类别代码，获取字典项目")
    ResultData<List<DataDictItemDto>> getDataDictItems(@RequestParam("categoryCode") String categoryCode, @RequestParam("isAll")Boolean isAll);

    /**
     * 根据字段类别代码，获取未冻结的字典项目
     *
     * @param categoryCode 字典类别代码
     * @return 返回当前类别下未冻结的字典项目
     */
    @GetMapping(path = "getDataDictItemsUnFrozen")
    @ApiOperation(value = "根据字段类别代码，获取未冻结的字典项目", notes = "根据字段类别代码，获取未冻结的字典项目")
    ResultData<List<IDataDict>> getDataDictItemsUnFrozen(@RequestParam("categoryCode") String categoryCode);

    /**
     * 根据字段类别代码，获取字典项目
     *
     * @param categoryCode 字典类别代码
     * @return 返回当前类别下的字典项目
     */
    @GetMapping(path = "getDataDicts")
    @ApiOperation(value = "根据字段类别代码，获取字典项目", notes = "根据字段类别代码，获取字典项目")
    ResultData<List<IDataDict>> getDataDicts(@RequestParam("categoryCode") String categoryCode);

    /**
     * 按字典类别代码和字典项目值查询字典项目
     *
     * @param categoryCode 字典类别代码
     * @param value        字典项目值
     * @return 返回符合条件的字典项目
     */
    @GetMapping(path = "getDataDictByValue")
    @ApiOperation(value = "按字典类别代码和字典项目值查询字典项目", notes = "按字典类别代码和字典项目值查询字典项目")
    ResultData<IDataDict> getDataDictByValue(@RequestParam("categoryCode") String categoryCode, @RequestParam("value") String value);

    /**
     * 按字典类别代码和字典项目值查询字典项目
     *
     * @param categoryCode 字典类别代码
     * @param valueName    字典项目名称
     * @return 返回符合条件的字典项目
     */
    @GetMapping(path = "getDataDictByValueName")
    @ApiOperation(value = "按字典类别代码和字典项目值查询字典项目", notes = "按字典类别代码和字典项目值查询字典项目")
    ResultData<IDataDict> getDataDictByValueName(@RequestParam("categoryCode") String categoryCode, @RequestParam("valueName") String valueName);
}
