package com.ruoyi.bussines.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ApiModel("添加收入/支出参数")
public class RecordDTO {
    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("spand：支出，revenue：收入")
    @NotBlank(message = "记录类型不能为空")
    private String dialogType;

    @ApiModelProperty("支出收入方式=1：微信钱包，2：微信零钱通，3：支付宝余额，4：支付宝余额宝，5：银行卡，6：基金，7：其他，8：其他，9：其他")
    @NotBlank(message = "收入/支出方式不能为空")
    private Integer region;

    @ApiModelProperty("金额")
    @NotBlank(message = "金额不能为空")
    private Integer number;

    @ApiModelProperty("备注")
    private String remark;
}
