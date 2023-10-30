package com.ruoyi.bussines.controller;

import com.github.pagehelper.PageInfo;
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

    @ApiOperation("修改收入支出记录")
    @PostMapping("/update")
    public AjaxResult update(@RequestBody RecordDTO recordDTO) {
        Integer i = recordService.update(recordDTO);
        return i <= 0 ? AjaxResult.error() : AjaxResult.success();
    }

    @ApiOperation("查询收入/支出详情")
    @GetMapping("/list")
    public R<PageInfo<RecordDTO>> list(
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize,
            @Parameter(description = "spand:收入/revenue:支出 ") @RequestParam(value = "dialogType", required = false) String dialogType,
            @Parameter(description = "方式 = 0：微信钱包，1：微信零钱通，2：支付宝余额，3：银行卡，4：基金，5：支付宝余额宝 ") @RequestParam(value = "region", required = false) Integer region
    ) {
        PageInfo<RecordDTO> record = recordService.list(dialogType, region, pageNum, pageSize);
        return R.ok(record);
    }

    @ApiOperation("查询年/年月/年月日/全部收入/支出详情")
    @GetMapping("/queryInfoByDate")
    public R<Object> queryInfoByDate(
            @Parameter(description = "spend:收入/revenue:支出 ") @RequestParam(value = "dialogType", required = false) String dialogType,
            @Parameter(description = "方式 = 1：微信钱包，2：微信零钱通，3：支付宝余额，4：支付宝余额宝，5：银行卡，6：基金，7：其他 ") @RequestParam(value = "region", required = false) Integer region,
            @Parameter(description = "年") @RequestParam(value = "year", required = false) String year,
            @Parameter(description = "月") @RequestParam(value = "month", required = false) String month,
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
