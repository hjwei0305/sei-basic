package com.changhong.sei.basic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author czq
 * @date 2023/7/4
 * @description 调动信息
 */
@NoArgsConstructor
@Data
public class TransferDto {

    @JsonProperty("code")
    private String code;
    @JsonProperty("data")
    private List<DataDTO> data;
    @JsonProperty("message")
    private String message;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("emptranfcode")
        private String emptranfcode;
        @JsonProperty("employee_code")
        private String employeeCode;
        @JsonProperty("tranfcmpdate")
        private String tranfcmpdate;
    }
}
