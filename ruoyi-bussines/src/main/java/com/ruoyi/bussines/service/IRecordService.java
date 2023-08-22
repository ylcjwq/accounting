package com.ruoyi.bussines.service;

import com.ruoyi.bussines.dto.BudgetDTO;
import com.ruoyi.bussines.dto.RecordDTO;

import java.util.Map;

public interface IRecordService {
    /**
     * 记录记录收入支出接口
     *
     * @param recordDTO
     */
    Integer add(RecordDTO recordDTO);

    /**
     * 设置预算
     * @param budgetDTO
     */
    void setBudget(BudgetDTO budgetDTO);

    Map<String, Object> getBudget(Long userId);

    void setBudgetStatus(Long userId, Boolean status);
}
