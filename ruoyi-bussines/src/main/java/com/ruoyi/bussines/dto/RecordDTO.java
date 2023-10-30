package com.ruoyi.bussines.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.service.ISysDictDataService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import javax.validation.constraints.NotBlank;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@ApiModel("收入/支出参数")
public class RecordDTO {
    @ApiModelProperty("记录主键编号")
    private Long id;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("spand：支出，revenue：收入")
    @NotBlank(message = "记录类型不能为空")
    private String dialogType;

    @ApiModelProperty("支出收入方式=0：微信钱包，1：微信零钱通，2：支付宝余额，3：银行卡，4：基金，5：支付宝余额宝")
    @NotBlank(message = "收入/支出方式不能为空")
    private Integer region;

    @ApiModelProperty("支出/收入方式说明")
    public String getRegionDesc() {
        if (ObjectUtils.isEmpty(this.region)) {
            return null;
        }
        return SpringUtils.getBean(ISysDictDataService.class).selectDictLabel("expense_or_income_type", this.region.toString());
    }

    @ApiModelProperty("金额")
    @NotBlank(message = "金额不能为空")
    private Double number;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("记录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    @ApiModelProperty("年份")
    private Integer year;

    @ApiModelProperty("月份")
    private Integer month;

    @ApiModelProperty("天")
    private Integer day;

    @ApiModelProperty("活动名称")
    private String projectName;

    @ApiModelProperty("日期")
    public String getTimeStr() {
        if (ObjectUtils.isEmpty(this.time)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        return sdf.format(this.time);
    }
}
