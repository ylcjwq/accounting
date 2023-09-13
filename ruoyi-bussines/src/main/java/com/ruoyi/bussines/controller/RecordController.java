package com.ruoyi.bussines.controller;

import com.ruoyi.bussines.dto.BudgetDTO;
import com.ruoyi.bussines.dto.RecordDTO;
import com.ruoyi.bussines.service.IRecordService;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/record")
@Api(tags = "记录收入支出")
public class RecordController {
    @Resource
    private IRecordService recordService;

    @ApiOperation("新增收入支出记录")
    @PostMapping("/add")
    public AjaxResult add(@RequestBody RecordDTO recordDTO) {
        Integer i = recordService.add(recordDTO);
        return i <= 0 ? AjaxResult.error() : AjaxResult.success();
    }

    @ApiOperation("查询收入/支出详情")
    @GetMapping("/info")
    public R<List<RecordDTO>> info(
            @Parameter(description = "spand:收入/revenue:支出 ") @RequestParam(value = "dialogType", required = false) String dialogType,
            @Parameter(description = "方式 = 1：微信钱包，2：微信零钱通，3：支付宝余额，4：支付宝余额宝，5：银行卡，6：基金，7：其他 ") @RequestParam(value = "region", required = false) Integer region
    ) {
        List<RecordDTO> record = recordService.info(dialogType, region);
        return R.ok(record);
    }

    @ApiOperation("查询年/年月/年月日/全部收入/支出详情")
    @GetMapping("/queryInfoByDate")
    public R<Object> queryInfoByDate(
            @Parameter(description = "spand:收入/revenue:支出 ") @RequestParam(value = "dialogType", required = false) String dialogType,
            @Parameter(description = "方式 = 1：微信钱包，2：微信零钱通，3：支付宝余额，4：支付宝余额宝，5：银行卡，6：基金，7：其他 ") @RequestParam(value = "region", required = false) Integer region,
            @Parameter(description = "年") @RequestParam(value = "year", required = false) String year,
            @Parameter(description = "年月") @RequestParam(value = "month", required = false) String month,
            @Parameter(description = "天") @RequestParam(value = "day", required = false) String day
    ) {
        return R.ok(recordService.queryInfoByDate(dialogType, region, year, month, day));
    }

    @ApiOperation("设置预算")
    @PutMapping("/setBudget")
    public AjaxResult setBudget(@RequestBody BudgetDTO budgetDTO) {
        recordService.setBudget(budgetDTO);
        return AjaxResult.success();
    }

    @ApiOperation("查询用户预算")
    @GetMapping("/getBudget/{userId}")
    public R getBudget(@PathVariable Long userId) {
        return R.ok(recordService.getBudget(userId));
    }

    @ApiOperation("设置预算状态开关")
    @PutMapping("/setBudgetStatus/{userId}")
    public AjaxResult setBudgetStatus(@PathVariable("userId") Long userId, @RequestParam("status") Boolean status) {
        recordService.setBudgetStatus(userId, status);
        return AjaxResult.success();
    }

}
