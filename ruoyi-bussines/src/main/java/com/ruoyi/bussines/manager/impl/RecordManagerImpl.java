package com.ruoyi.bussines.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ruoyi.bussines.dto.RecordDTO;
import com.ruoyi.bussines.manager.RecordManager;
import com.ruoyi.bussines.mapper.BudgetMapper;
import com.ruoyi.bussines.mapper.RecordMapper;
import com.ruoyi.bussines.model.*;
import com.ruoyi.bussines.tracker.ExpenseTracker;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
@SuppressWarnings("all")
public class RecordManagerImpl implements RecordManager {
    private final BudgetMapper budgetMapper;
    private final RecordMapper recordMapper;

    @Override
    public int insertBudget(Budget budget) {
        return budgetMapper.insert(budget);
    }

    @Override
    public List<RecordDTO> queryAll(String dialogType, Integer region) {
        Long userId = SecurityUtils.getUserId();
        if (userId == null) {
            return null;
        }
        List<RecordDTO> recordDTOS = recordMapper.selectAll(dialogType, region, SecurityUtils.getUserId());
        return recordDTOS;
    }

    @Override
    public Object queryInfoByDate(String dialogType, Integer region, String year, String month, String day) {
        Long userId = SecurityUtils.getUserId();
        if (userId == null) {
            return null;
        }
        Integer type = getType(year, month, day);
        List<RecordDTO> recordDTOS = recordMapper.queryInfoByDate(dialogType, region, type, year, month, day, userId);

        return strResult(recordDTOS, year, type);
    }

    @Override
    public int updateBudget(Budget budget) {
        return budgetMapper.update(budget, new LambdaUpdateWrapper<Budget>()
                .eq(Budget::getUserId, budget.getUserId())
                .set(budget.getBudget() != null, Budget::getBudget, budget.getBudget())
                .set(budget.getEnabled() != null, Budget::getEnabled, budget.getEnabled()));
    }

    @Override
    public Budget selectBudgetByUserId(Long userId) {
        return budgetMapper.selectOne(new LambdaQueryWrapper<Budget>()
                .eq(Budget::getUserId, userId));
    }

    public int updateBudgetById(Budget budget) {
        return budgetMapper.updateById(budget);
    }

    /**
     * 获取查询类型
     *
     * @return 年月日=1，年月=2，年=3，其他=0
     */
    private Integer getType(String year, String month, String day) {
        if (StringUtils.isNotBlank(year) && StringUtils.isNotBlank(month) && StringUtils.isNotBlank(day)) {
            return 3;
        } else if (StringUtils.isNotBlank(year) && StringUtils.isNotBlank(month)) {
            return 2;
        } else if (StringUtils.isNotBlank(year)) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 拼接返回的数据
     *
     * @return 年月日=直接返回list，年月=返回根据日分组的map，年=返回根据月分组的map
     */
    private Object strResult(List<RecordDTO> recordDTOS, String year, Integer type) {
        Map map = new ConcurrentHashMap<String, Object>();
        if (type == 0) {
            map.put("year",getResultGroupByYear(recordDTOS));
            return map;
        }
        YearModel yearModel = new YearModel();
        List<MonthModel> monthModel = getResultGroupByMonth(recordDTOS);
        yearModel.setNumber(year);
        yearModel.setMonth(monthModel);
        map.put("year", yearModel);
        return map;
    }

    private List<YearModel> getResultGroupByYear(List<RecordDTO> dtos) {
        // 先根据年份分组
        List<YearModel> yearModels = new ArrayList<>();
        Map<Integer, List<RecordDTO>> yearMap = dtos.stream().collect(Collectors.groupingBy(RecordDTO::getYear, TreeMap::new, Collectors.toList()));
        for (Map.Entry<Integer, List<RecordDTO>> entry : yearMap.entrySet()) {
            YearModel yearModel = new YearModel();
            yearModel.setNumber(entry.getKey().toString());
            yearModel.setMonth(getResultGroupByMonth(entry.getValue()));
            yearModels.add(yearModel);
        }
        return yearModels;
    }

    // 先根据月分组，后根据日分组
    private List<MonthModel> getResultGroupByMonth(List<RecordDTO> dtos) {
        // 先根据月份分组
        Map<Integer, List<RecordDTO>> monthMap = dtos.stream().collect(Collectors.groupingBy(RecordDTO::getMonth, TreeMap::new, Collectors.toList()));
        Map<Integer, Map<Integer, List<RecordDTO>>> resultMap = monthMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> {
            // 根据月份分组后，再根据天分组
            TreeMap<Integer, List<RecordDTO>> dayMap = entry.getValue().stream().collect(Collectors.groupingBy(RecordDTO::getDay, TreeMap::new, Collectors.toList()));
            return dayMap;
        }));

        List<MonthModel> monthModels = new ArrayList<>();
        resultMap.entrySet().stream().forEach(entry -> {
            Integer month = entry.getKey();
            Map<Integer, List<RecordDTO>> values = entry.getValue();
            // 处理月下的日
            List<DayModel> dayModels = new ArrayList<>();
            MonthModel monthModel = new MonthModel();
            values.entrySet().stream().forEach(value -> {
                Integer day = value.getKey();
                List<RecordDTO> recordDTOS = value.getValue();
                DayModel dayModel = new DayModel();
                dayModel.setNumber(day.toString());
                dayModel.setPay(recordDTOS);
                dayModels.add(dayModel);
            });
            monthModel.setNumber(month.toString());
            monthModel.setDay(dayModels);
            monthModels.add(monthModel);
        });
        return monthModels;
    }

    @Override
    public Boolean asyncBudgetRemind() {
        Long userId = SecurityUtils.getUserId();
        if (userId == null) {
            throw new ServiceException("非法用户！");
        }
        // 查询当月预算
        Budget budget = selectBudgetByUserId(userId);
        if (ObjectUtils.isEmpty(budget) || !budget.getEnabled()) {
            // 若预算为空，且预算不可用，则不进行超出预算提醒
            return false;
        }
        ExpenseTracker tracker = new ExpenseTracker(Double.valueOf(budget.getBudget()));
        // 查询当月的支出，是否触发预算提醒
        Double sum = recordMapper.sumByMonth(userId);
        Expense expense = new Expense(sum);
        tracker.addExpense(expense);
        return true;
    }
}
