package com.ruoyi.bussines.dto;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

@Data
public class RecordTypeDTO {
    /**
     * 标识编码
     */
    @Parameter(description = "标识编码")
    private Long code;

    /**
     * 标识key
     */
    @Parameter(description = "标识key")
    private String value;

    /**
     * 字典排序
     */
    @Parameter(description = "排序")
    private Long sort;

    /**
     * 标签
     */
    @Parameter(description = "标签")
    private String name;
}
