package com.ruoyi.bussines.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ruoyi.bussines.dto.RecordDTO;
import com.ruoyi.bussines.manager.RecordManager;
import com.ruoyi.bussines.mapper.BudgetMapper;
import com.ruoyi.bussines.mapper.RecordMapper;
import com.ruoyi.bussines.model.Budget;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
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
     * @return 年月日=直接返回list，年月=返回根据日分组的map，年=返回根据月分组的map
     */
    private Object strResult(List<RecordDTO> recordDTOS, String year, Integer type) {
        Map map = new ConcurrentHashMap<String, Map<String, List<RecordDTO>>>();
        // 拼接返回的数据
        switch (type) {
            case 1:
                Map<Integer, Map<Integer, List<RecordDTO>>> monthDateResultMap = getResultGroupByMonth(recordDTOS);
                map.put(StringUtils.format("{}", year), monthDateResultMap);
                return map;
            case 2:
                Map<Integer, List<RecordDTO>> dateResultMap = getResultGroupByDay(recordDTOS);
                return dateResultMap;
            case 3:
                return recordDTOS;
            default:
                break;
        }
        return map;
    }


    // 先根据月分组，后根据日分组
    private Map<Integer, Map<Integer, List<RecordDTO>>> getResultGroupByMonth(List<RecordDTO> dtos) {
        // 先根据月份分组
        TreeMap<Integer, List<RecordDTO>> monthMap = dtos.stream().collect(Collectors.groupingBy(RecordDTO::getMonth, TreeMap::new, Collectors.toList()));
        Map<Integer, Map<Integer, List<RecordDTO>>> resultMap = monthMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> {
            // 根据月份分组后，再根据天分组
            TreeMap<Integer, List<RecordDTO>> dayMap = entry.getValue().stream().collect(Collectors.groupingBy(RecordDTO::getDay, TreeMap::new, Collectors.toList()));
            return dayMap;
        }));
        return resultMap;
    }

    // 根据日分组
    private Map<Integer, List<RecordDTO>> getResultGroupByDay(List<RecordDTO> dtos) {
        // 先根据月份分组
        Map<Integer, List<RecordDTO>> monthMap = dtos.stream().collect(Collectors.groupingBy(RecordDTO::getDay, TreeMap::new, Collectors.toList()));
        return monthMap;
    }
}
