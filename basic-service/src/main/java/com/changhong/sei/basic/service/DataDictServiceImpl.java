package com.changhong.sei.basic.service;

import com.changhong.sei.basic.api.DataDictService;
import com.changhong.sei.basic.dto.DataDictDto;
import com.changhong.sei.basic.dto.DataDictItemDto;
import com.changhong.sei.basic.entity.DataDict;
import com.changhong.sei.basic.entity.DataDictItem;
import com.changhong.sei.basic.manager.DataDictManager;
import com.changhong.sei.core.dto.IDataDict;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.manager.BaseEntityManager;
import com.changhong.sei.core.manager.bo.OperateResult;
import com.changhong.sei.core.manager.bo.OperateResultWithData;
import com.changhong.sei.core.manager.bo.ResponseData;
import com.changhong.sei.core.service.DefaultBaseEntityService;
import com.changhong.sei.core.service.DefaultFindByPageService;
import com.changhong.sei.core.utils.ResultDataUtil;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 实现功能: 数据字典API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-27 8:39
 */
@Service
@Api(value = "DataDictService", tags = "数据字典API服务实现")
public class DataDictServiceImpl implements DefaultBaseEntityService<DataDict, DataDictDto>,
        DefaultFindByPageService<DataDict, DataDictDto>,
        DataDictService {
    @Autowired
    private DataDictManager manager;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * 将DTO转换为数据实体
     *
     * @param dto 数据字典行项目DTO
     * @return 数据字典行项目
     */
    private DataDictItem convertToEntity(DataDictItemDto dto) {
        return modelMapper.map(dto, DataDictItem.class);
    }

    /**
     * 将数据实体转换为DTO
     *
     * @param entity 数据实体
     * @return 数据字典行项目DTO
     */
    private DataDictItemDto convertToDto(DataDictItem entity) {
        return modelMapper.map(entity, DataDictItemDto.class);
    }

    /**
     * 保存字典项目
     *
     * @param dictItemDto 字典项目
     * @return 返回保存字典项目的结果
     */
    @Override
    public ResultData<DataDictItemDto> saveDictItem(@Valid DataDictItemDto dictItemDto) {
        // 转换行项目DTO为entity
        DataDictItem item = convertToEntity(dictItemDto);
        OperateResultWithData<DataDictItem> saveResult = manager.saveDictItem(item);
        return ResultDataUtil.convertFromOperateResult(saveResult, convertToDto(saveResult.getData()));
    }

    /**
     * 删除字典项目
     *
     * @param id 字典项目id
     * @return 返回保存字典项目的结果
     */
    @Override
    public ResultData deleteDictItem(String id) {
        OperateResult result = manager.deleteDictItem(id);
        return ResultDataUtil.convertFromOperateResult(result);
    }

    /**
     * 根据字段类别代码，获取字典项目
     *
     * @param categoryCode 字典类别代码
     * @param isAll
     * @return 返回当前类别下的字典项目
     */
    @Override
    public ResultData<List<DataDictItemDto>> getDataDictItems(String categoryCode, Boolean isAll) {
        List<DataDictItem> items = manager.getDataDictItems(categoryCode, isAll);
        List<DataDictItemDto> dtos = items.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    /**
     * 根据字段类别代码，获取未冻结的字典项目
     *
     * @param categoryCode 字典类别代码
     * @return 返回当前类别下未冻结的字典项目
     */
    @Override
    public ResultData<List<IDataDict>> getDataDictItemsUnFrozen(String categoryCode) {
        List<IDataDict> dataDicts = manager.getDataDictItemsUnFrozen(categoryCode);
        return ResultData.success(dataDicts);
    }

    /**
     * 根据字段类别代码，获取字典项目
     *
     * @param categoryCode 字典类别代码
     * @return 返回当前类别下的字典项目
     */
    @Override
    public ResultData<List<IDataDict>> getDataDicts(String categoryCode) {
        ResponseData<List<IDataDict>> responseData = manager.getDataDicts(categoryCode);
        return ResultDataUtil.convertFromResponseData(responseData, responseData.getData());
    }

    /**
     * 按字典类别代码和字典项目值查询字典项目
     *
     * @param categoryCode 字典类别代码
     * @param value        字典项目值
     * @return 返回符合条件的字典项目
     */
    @Override
    public ResultData<IDataDict> getDataDictByValue(String categoryCode, String value) {
        ResponseData<IDataDict> responseData = manager.getDataDictByValue(categoryCode, value);
        return ResultDataUtil.convertFromResponseData(responseData, responseData.getData());
    }

    /**
     * 按字典类别代码和字典项目值查询字典项目
     *
     * @param categoryCode 字典类别代码
     * @param valueName    字典项目名称
     * @return 返回符合条件的字典项目
     */
    @Override
    public ResultData<IDataDict> getDataDictByValueName(String categoryCode, String valueName) {
        ResponseData<IDataDict> responseData = manager.getDataDictByValueName(categoryCode, valueName);
        return ResultDataUtil.convertFromResponseData(responseData, responseData.getData());
    }

    @Override
    public BaseEntityManager<DataDict> getManager() {
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
    public Class<DataDict> getEntityClass() {
        return DataDict.class;
    }

    /**
     * 获取传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<DataDictDto> getDtoClass() {
        return DataDictDto.class;
    }
}
