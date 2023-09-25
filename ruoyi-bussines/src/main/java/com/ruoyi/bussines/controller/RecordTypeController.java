package com.ruoyi.bussines.controller;

import com.ruoyi.bussines.dto.RecordDTO;
import com.ruoyi.bussines.dto.RecordTypeDTO;
import com.ruoyi.bussines.service.IRecordService;
import com.ruoyi.common.core.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/recordType")
@Api(tags = "记录收入支出类型")
public class RecordTypeController {
    @Resource
    private IRecordService recordService;

    @ApiOperation("查询收入/支出类型")
    @GetMapping(value = "/queryType")
    public AjaxResult queryType() {
        return AjaxResult.success(recordService.queryType());
    }

    @ApiOperation("新增收入/支出类型")
    @PutMapping(value = "/addType")
    public AjaxResult addType(@Parameter(description = "需要新增的类型名称") @RequestParam(name = "name", required = true) @NotBlank String name) {
        recordService.addType(name);
        return AjaxResult.success();
    }

    @ApiOperation("删除收入/支出类型")
    @DeleteMapping(value = "/deleteType")
    public AjaxResult deleteType(@Parameter(description = "被删除类型的数字标识") @RequestParam(name = "code", required = true) @NotBlank Long code) {
        recordService.deleteType(code);
        return AjaxResult.success();
    }

    @ApiOperation("调整顺序")
    @PostMapping(value = "/updateSort")
    public AjaxResult updateSort(@RequestBody List<RecordTypeDTO> dtoList) {
        recordService.updateSort(dtoList);
        return AjaxResult.success();
    }
}
