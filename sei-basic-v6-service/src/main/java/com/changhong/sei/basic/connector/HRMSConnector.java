package com.changhong.sei.basic.connector;

import com.alibaba.fastjson.JSONObject;
import com.changhong.sei.basic.constant.HRMSConstant;
import com.changhong.sei.basic.dto.HrmsEmployeeDto;
import com.changhong.sei.basic.dto.OrgDTO;
import com.changhong.sei.basic.dto.TransferDto;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Carol
 * @date 2022/8/10 14:50
 * @description
 */
@Component
public class HRMSConnector {
    private static final RestTemplate REST_TEMPLATE = new RestTemplate();
    private static ResponseEntity<String> result;

    /**
     * 错误提示
     */
    public static final String ERROR = "HRMS服务器响应超时!";
    public static String url = null;

    /**
     * 人员信息
     *
     * @param
     * @return
     */
    public static List<HrmsEmployeeDto.DataDTO> getEmp() {
        REST_TEMPLATE.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        url = HRMSConstant.HRMSURL + HRMSConstant.GET_EMP;
        try {
            result = REST_TEMPLATE.getForEntity(url, String.class);
        } catch (Exception e) {
            throw (e);
        }
        List<HrmsEmployeeDto> empList = JSONObject.parseArray("[" + result.getBody() + "]", HrmsEmployeeDto.class);
        return empList.get(0).getData();
    }

    /**
     * 组织信息
     *
     * @param
     * @return
     */
    public static List<OrgDTO.DataDTO> getOrg() {
        REST_TEMPLATE.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        url = HRMSConstant.HRMSURL + HRMSConstant.GET_ORG;
        try {
            result = REST_TEMPLATE.getForEntity(url, String.class);
        } catch (Exception e) {
            throw (e);
        }
        List<OrgDTO> orgList = JSONObject.parseArray("[" + result.getBody() + "]", OrgDTO.class);
        return orgList.get(0).getData();
    }

    /**
     * 调动信息
     *
     * @param start 开始时间 end 结束时间
     * @return
     */
    public static List<TransferDto.DataDTO> getTransfer(String start, String end) {
        REST_TEMPLATE.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        url = HRMSConstant.HRMSURL + HRMSConstant.GET_TRANSFER+"?starttime="+start+"&endtime="+end;
        try {
            result = REST_TEMPLATE.getForEntity(url, String.class);
        } catch (Exception e) {
            throw (e);
        }
        List<TransferDto> transferList = JSONObject.parseArray("[" + result.getBody() + "]", TransferDto.class);
        return transferList.get(0).getData();
    }

}
