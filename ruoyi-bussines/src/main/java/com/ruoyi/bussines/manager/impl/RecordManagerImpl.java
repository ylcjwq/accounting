package com.ruoyi.bussines.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ruoyi.bussines.manager.RecordManager;
import com.ruoyi.bussines.mapper.BudgetMapper;
import com.ruoyi.bussines.model.Budget;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RecordManagerImpl implements RecordManager {
    @Resource
    private BudgetMapper budgetMapper;

    @Override
    public int insertBudget(Budget budget) {
        return budgetMapper.insert(budget);
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
