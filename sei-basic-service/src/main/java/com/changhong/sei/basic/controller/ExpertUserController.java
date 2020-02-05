package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.ExpertUserApi;
import com.changhong.sei.basic.dto.ExpertUserDto;
import com.changhong.sei.basic.dto.ExpertUserVo;
import com.changhong.sei.basic.entity.ExpertUser;
import com.changhong.sei.basic.service.ExpertUserService;
import com.changhong.sei.core.controller.DefaultBaseController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.utils.ResultDataUtil;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 实现功能: 专家用户API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-28 10:02
 */
@RestController
@Api(value = "ExpertUserService", tags = "专家用户API服务实现")
public class ExpertUserController implements DefaultBaseController<ExpertUser, ExpertUserDto>,
        ExpertUserApi {
    @Autowired
    private ExpertUserService service;
    @Autowired
    private ModelMapper modelMapper;
    /**
     * 分页查询业务实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @Override
    public ResultData<PageResult<ExpertUserVo>> findVoByPage(Search search) {
        return ResultData.success(service.findVoByPage(search));
    }

    /**
     * 通过ID将该实体冻结/解冻
     *
     * @param id     实体ID
     * @param frozen 是否冻结，是true,否false
     * @return 操作结果
     */
    @Override
    public ResultData freeze(String id, Boolean frozen) {
        return ResultDataUtil.convertFromOperateResult(service.freeze(id, frozen));
    }

    /**
     * 通过专家用户中专家的ID将该实体冻结/解冻
     *
     * @param expertId 实体ID
     * @param frozen   是否冻结，是true,否false
     * @return 操作结果
     */
    @Override
    public ResultData freezeByExpertId(String expertId, Boolean frozen) {
        return ResultDataUtil.convertFromOperateResult(service.freezeByExpertId(expertId, frozen));
    }

    /**
     * 保存专家用户
     *
     * @param expertUserVo 专家用户
     * @return 操作结果
     */
    @Override
    public ResultData save(ExpertUserVo expertUserVo) {
        return ResultDataUtil.convertFromOperateResult(service.save(expertUserVo));
    }

    /**
     * 根据专家用户中的专家的ID删除业务实体
     *
     * @param expertId 专家用户中的专家的ID
     * @return 操作结果
     */
    @Override
    public ResultData deleteByExpertId(String expertId) {
        return ResultDataUtil.convertFromOperateResult(service.deleteByExpertId(expertId));
    }

    /**
     * 通过Id获取一个业务实体
     *
     * @param id 业务实体Id
     * @return 业务实体
     */
    @Override
    public ResultData<ExpertUserDto> findOne(String id) {
        return ResultData.success(convertToDto(service.findOne(id)));
    }

    @Override
    public BaseEntityService<ExpertUser> getService() {
        return service;
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
    public Class<ExpertUser> getEntityClass() {
        return ExpertUser.class;
    }

    /**
     * 获取传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<ExpertUserDto> getDtoClass() {
        return ExpertUserDto.class;
    }
}
