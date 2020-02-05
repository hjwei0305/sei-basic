package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.MenuApi;
import com.changhong.sei.basic.dto.MenuDto;
import com.changhong.sei.basic.entity.Menu;
import com.changhong.sei.basic.service.MenuService;
import com.changhong.sei.core.controller.DefaultTreeController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseTreeService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 实现功能: 系统菜单API服务
 *
 * @author 王锦光 wangjg
 * @version 2020-01-19 22:09
 */
@RestController
@Api(value = "MenuService", tags = "系统菜单API服务")
public class MenuController implements DefaultTreeController<Menu, MenuDto>,
        MenuApi {
    @Autowired
    private MenuService menuService;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * 获取整个菜单树
     *
     * @return 菜单树形对象集合
     */
    @Override
    public ResultData<List<MenuDto>> getMenuTree() {
        List<Menu> menus = menuService.getMenuTree();
        List<MenuDto> dtos = menus.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    /**
     * 根据名称模糊查询
     *
     * @param name 名称
     * @return 返回的列表
     */
    @Override
    public ResultData<List<MenuDto>> findByNameLike(String name) {
        List<Menu> menus = menuService.findByNameLike(name);
        List<MenuDto> dtos = menus.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    @Override
    public BaseTreeService<Menu> getService() {
        return menuService;
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
    public Class<Menu> getEntityClass() {
        return Menu.class;
    }

    /**
     * 获取传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<MenuDto> getDtoClass() {
        return MenuDto.class;
    }

    /**
     * 将数据实体转换成DTO
     *
     * @param entity 业务实体
     * @return DTO
     */
    @Override
    public MenuDto convertToDto(Menu entity) {
        return MenuController.custConvertToDto(entity);
    }

    /**
     * 自定义将数据实体转换成DTO
     *
     * @param entity 业务实体
     * @return DTO
     */
    static MenuDto custConvertToDto(Menu entity) {
        if (Objects.isNull(entity)){
            return null;
        }
        ModelMapper custMapper = new ModelMapper();
        // 创建自定义映射规则
        PropertyMap<Menu, MenuDto> propertyMap = new PropertyMap<Menu, MenuDto>() {
            @Override
            protected void configure() {
                // 使用自定义转换规则确定FeatureId
                map().setFeatureId(source.getFeatureId());
                map().setMenuUrl(source.getFeature().getGroupCode());
            }
        };
        // 添加映射器
        custMapper.addMappings(propertyMap);
        // 转换
        return custMapper.map(entity, MenuDto.class);
    }
}
