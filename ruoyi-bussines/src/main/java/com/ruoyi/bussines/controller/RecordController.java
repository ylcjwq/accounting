package com.ruoyi.bussines.controller;

import com.ruoyi.bussines.dto.BudgetDTO;
import com.ruoyi.bussines.dto.RecordDTO;
import com.ruoyi.bussines.service.IRecordService;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/record")
@Api(tags = "记录收入支出")
public class RecordController {
    @Resource
    private IRecordService recordService;

    @ApiOperation("新增收入支出记录")
    @PostMapping("/add")
    public AjaxResult info(@RequestBody RecordDTO recordDTO) {
        Integer i = recordService.add(recordDTO);
        return i <= 0 ? AjaxResult.error() : AjaxResult.success();
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
    public AjaxResult setBudgetStatus(@PathVariable("userId") Long userId, @PathParam("status") Boolean status) {
        recordService.setBudgetStatus(userId, status);
        return AjaxResult.success();
    }

}
