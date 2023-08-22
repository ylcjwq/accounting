package com.ruoyi.bussines.manager;

import com.ruoyi.bussines.model.Budget;

public interface RecordManager {
    Budget selectBudgetByUserId(Long userId);

    int insertBudget(Budget budget);

    int updateBudget(Budget budget);

    int updateBudgetById(Budget budget);
}
