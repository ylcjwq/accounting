package com.ruoyi.bussines.manager;

import com.ruoyi.bussines.dto.RecordDTO;
import com.ruoyi.bussines.model.Budget;

import java.util.List;

public interface RecordManager {
    Budget selectBudgetByUserId(Long userId);

    int insertBudget(Budget budget);

    int updateBudget(Budget budget);

    int updateBudgetById(Budget budget);

    List<RecordDTO> queryAll(String dialogType, Integer region);

    Object queryInfoByDate(String dialogType, Integer region, String year, String month, String day);

    Boolean asyncBudgetRemind();

    Integer updateRecordById(RecordDTO recordDTO);
}
