package com.changhong.sei.basic.service.client;

import com.changhong.sei.apitemplate.ApiTemplate;
import com.changhong.sei.basic.service.CorporationService;
import com.changhong.sei.basic.service.OrganizationService;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.auth.AuthEntityData;
import com.changhong.sei.core.dto.auth.AuthTreeEntityData;
import com.changhong.sei.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现功能: 数据权限服务方法的管理
 *
 * @author 王锦光 wangjg
 * @version 2020-05-10 21:21
 */
@Component
public class DataAuthManager {
    private static final String GET_AUTH_ENTITY_DATA_METHOD = "getAuthEntityDataByIds";
    private static final String GET_AUTH_TREE_ENTITY_DATA_METHOD = "getAuthTreeEntityDataByIds";
    private static final String FIND_ALL_AUTH_ENTITY_DATA_METHOD = "findAllAuthEntityData";
    private static final String FIND_ALL_AUTH_TREE_ENTITY_DATA_METHOD = "findAllAuthTreeEntityData";
    private static final String API_ORGANIZATION = "organization";
    private static final String API_CORPORATION = "corporation";
    @Autowired
    private ApiTemplate apiTemplate;
    @Autowired
    private CorporationService corporationService;
    @Autowired
    private OrganizationService organizationService;

    /**
     * 通过业务实体Id清单获取数据权限值清单
     * @param appModuleCode 应用模块代码
     * @param apiPath API接口路径（业务实体类名）
     * @param entityIds 业务实体Id清单
     * @return 数据权限值清单
     */
    public List<AuthEntityData> getAuthEntityDataByIds(String appModuleCode, String apiPath, List<String> entityIds){
        // 判断是公司,直接执行公司业务逻辑
        if (apiPath.equals(API_CORPORATION)) {
            return corporationService.getAuthEntityDataByIds(entityIds);
        }
        //调用API服务，获取业务实体
        String path = String.format("%s/%s", apiPath, GET_AUTH_ENTITY_DATA_METHOD);
        ParameterizedTypeReference<ResultData<List<AuthEntityData>>> typeReference = new ParameterizedTypeReference<ResultData<List<AuthEntityData>>>() {};
        ResultData<List<AuthEntityData>> resultData = apiTemplate.postByAppModuleCode(appModuleCode, path, typeReference, entityIds);
        if (resultData.failed()){
            throw new ServiceException("通过业务实体Id清单获取数据权限值清单失败！" + resultData.getMessage());
        }
        return resultData.getData();
    }

    /**
     * 获取业务实体所有未冻结的数据权限值清单
     * @param appModuleCode 应用模块代码
     * @param apiPath API接口路径（业务实体类名）
     * @return 数据权限值清单
     */
    public List<AuthEntityData> findAllAuthEntityData(String appModuleCode, String apiPath){
        // 判断是公司,直接执行公司业务逻辑
        if (apiPath.equals(API_CORPORATION)) {
            return corporationService.findAllAuthEntityData();
        }
        //调用API服务，获取业务实体
        String path = String.format("%s/%s", apiPath, FIND_ALL_AUTH_ENTITY_DATA_METHOD);
        ParameterizedTypeReference<ResultData<List<AuthEntityData>>> typeReference = new ParameterizedTypeReference<ResultData<List<AuthEntityData>>>() {};
        ResultData<List<AuthEntityData>> resultData = apiTemplate.getByAppModuleCode(appModuleCode, path, typeReference);
        if (resultData.failed()){
            throw new ServiceException("获取业务实体所有未冻结的数据权限值清单失败！" + resultData.getMessage());
        }
        return resultData.getData();
    }

    /**
     * 通过树形实体Id清单获取数据权限值清单
     * @param appModuleCode 应用模块代码
     * @param apiPath API接口路径（业务实体类名）
     * @param entityIds 业务实体Id清单
     * @return 数据权限值清单
     */
    public List<AuthTreeEntityData> getAuthTreeEntityDataByIds(String appModuleCode, String apiPath, List<String> entityIds){
        // 判断是组织机构,直接执行组织机构业务逻辑
        if (apiPath.equals(API_ORGANIZATION)) {
            return organizationService.getAuthTreeEntityDataByIds(entityIds);
        }
        //调用API服务，获取业务实体
        String path = String.format("%s/%s", apiPath, GET_AUTH_TREE_ENTITY_DATA_METHOD);
        ParameterizedTypeReference<ResultData<List<AuthTreeEntityData>>> typeReference = new ParameterizedTypeReference<ResultData<List<AuthTreeEntityData>>>() {};
        ResultData<List<AuthTreeEntityData>> resultData = apiTemplate.postByAppModuleCode(appModuleCode, path, typeReference, entityIds);
        if (resultData.failed()){
            throw new ServiceException("通过树形实体Id清单获取数据权限值清单失败！" + resultData.getMessage());
        }
        return resultData.getData();
    }

    /**
     * 获取树形实体所有未冻结的数据权限值清单
     * @param appModuleCode 应用模块代码
     * @param apiPath API接口路径（业务实体类名）
     * @return 数据权限值清单
     */
    public List<AuthTreeEntityData> findAllAuthTreeEntityData(String appModuleCode, String apiPath){
        // 判断是组织机构,直接执行组织机构业务逻辑
        if (apiPath.equals(API_ORGANIZATION)) {
            return organizationService.findAllAuthTreeEntityData();
        }
        //调用API服务，获取业务实体
        String path = String.format("%s/%s", apiPath, FIND_ALL_AUTH_TREE_ENTITY_DATA_METHOD);
        ParameterizedTypeReference<ResultData<List<AuthTreeEntityData>>> typeReference = new ParameterizedTypeReference<ResultData<List<AuthTreeEntityData>>>() {};
        ResultData<List<AuthTreeEntityData>> resultData = apiTemplate.getByAppModuleCode(appModuleCode, path, typeReference);
        if (resultData.failed()){
            throw new ServiceException("获取树形实体所有未冻结的数据权限值清单失败！" + resultData.getMessage());
        }
        return resultData.getData();
    }
}
