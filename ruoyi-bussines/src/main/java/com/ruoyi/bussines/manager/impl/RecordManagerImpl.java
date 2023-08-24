package com.ruoyi.bussines.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ruoyi.bussines.dto.RecordDTO;
import com.ruoyi.bussines.manager.RecordManager;
import com.ruoyi.bussines.mapper.BudgetMapper;
import com.ruoyi.bussines.mapper.RecordMapper;
import com.ruoyi.bussines.model.Budget;
import com.ruoyi.common.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class RecordManagerImpl implements RecordManager {
    private final BudgetMapper budgetMapper;
    private final RecordMapper recordMapper;

    @Override
    public int insertBudget(Budget budget) {
        return budgetMapper.insert(budget);
    }

    @Override
    public List<RecordDTO> queryAll(String dialogType, Integer region, Integer mouth) {
        Long userId = SecurityUtils.getUserId();
        if (userId == null) {
            return null;
        }
        List<RecordDTO> recordDTOS = recordMapper.selectAll(dialogType, region, mouth, userId);
        return recordDTOS;
    }

    @Override
    public int updateBudget(Budget budget) {
        return budgetMapper.update(budget, new LambdaUpdateWrapper<Budget>()
                .eq(Budget::getUserId, budget.getUserId())
                .set(budget.getBudget() != null, Budget::getBudget, budget.getBudget())
                .set(budget.getEnabled() != null, Budget::getEnabled, budget.getEnabled()));
    }

    public int updateBudgetById(Budget budget) {
        return budgetMapper.updateById(budget);
    }

    @Override
    public Budget selectBudgetByUserId(Long userId) {
        return budgetMapper.selectOne(new LambdaQueryWrapper<Budget>()
                .eq(Budget::getUserId, userId));
    }
}
