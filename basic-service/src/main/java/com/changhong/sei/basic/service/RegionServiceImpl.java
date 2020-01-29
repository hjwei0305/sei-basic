package com.changhong.sei.basic.service;

import com.changhong.sei.basic.api.RegionService;
import com.changhong.sei.basic.dto.RegionDto;
import com.changhong.sei.basic.entity.Region;
import com.changhong.sei.basic.manager.RegionManager;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.manager.BaseTreeManager;
import com.changhong.sei.core.service.DefaultTreeService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 实现功能: 行政区域的API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-29 23:02
 */
@Service
@Api(value = "RegionService", tags = "行政区域的API服务实现")
public class RegionServiceImpl implements DefaultTreeService<Region, RegionDto>,
        RegionService {
    @Autowired
    private RegionManager manager;
    @Autowired
    private ModelMapper modelMapper;
    /**
     * 获取所有行政区域树
     *
     * @return 行政区域树形对象集合
     */
    @Override
    public ResultData<List<RegionDto>> getRegionTree() {
        return ResultData.success(convertToDtos(manager.getRegionTree()));
    }

    /**
     * 通过国家id查询行政区域树
     *
     * @param countryId 国家id
     * @return 行政区域树清单
     */
    @Override
    public ResultData<List<RegionDto>> getRegionTreeByCountry(String countryId) {
        return ResultData.success(convertToDtos(manager.getRegionTreeByCountry(countryId)));
    }

    /**
     * 通过国家id查询省
     *
     * @param countryId
     */
    @Override
    public ResultData<List<RegionDto>> getProvinceByCountry(String countryId) {
        return ResultData.success(convertToDtos(manager.getProvinceByCountry(countryId)));
    }

    /**
     * 通过省id查询市
     *
     * @param provinceId
     */
    @Override
    public ResultData<List<RegionDto>> getCityByProvince(String provinceId) {
        return ResultData.success(convertToDtos(manager.getCityByProvince(provinceId)));
    }

    @Override
    public BaseTreeManager<Region> getManager() {
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
    public Class<Region> getEntityClass() {
        return Region.class;
    }

    /**
     * 获取传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<RegionDto> getDtoClass() {
        return RegionDto.class;
    }

    /**
     * 分页查询业务实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @Override
    public ResultData<PageResult<RegionDto>> findByPage(Search search) {
        return convertToDtoPageResult(manager.findByPage(search));
    }

    /**
     * 将数据实体转换成DTO
     *
     * @param entity 业务实体
     * @return DTO
     */
    @Override
    public RegionDto convertToDto(Region entity) {
        return RegionServiceImpl.custConvertToDto(entity);
    }

    /**
     * 自定义将数据实体转换成DTO
     *
     * @param entity 业务实体
     * @return DTO
     */
    static RegionDto custConvertToDto(Region entity){
        if (Objects.isNull(entity)){
            return null;
        }
        ModelMapper custMapper = new ModelMapper();
        // 创建自定义映射规则
        PropertyMap<Region, RegionDto> propertyMap = new PropertyMap<Region, RegionDto>() {
            @Override
            protected void configure() {
                // 自定义转换规则
                map().setCountryId(source.getCountryId());
            }
        };
        // 添加映射器
        custMapper.addMappings(propertyMap);
        // 转换
        return custMapper.map(entity, RegionDto.class);
    }
}
