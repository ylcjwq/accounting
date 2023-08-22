package com.ruoyi.bussines.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("设置预算参数")
public class BudgetDTO {
    @ApiModelProperty("用户id")
    private Long userId;
    @ApiModelProperty("预算金额")
    private String budget;
}
