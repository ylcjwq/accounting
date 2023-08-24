package com.ruoyi.bussines.service.Impl;

import com.ruoyi.bussines.dto.BudgetDTO;
import com.ruoyi.bussines.dto.RecordDTO;
import com.ruoyi.bussines.manager.RecordManager;
import com.ruoyi.bussines.mapper.RecordMapper;
import com.ruoyi.bussines.model.Budget;
import com.ruoyi.bussines.model.Record;
import com.ruoyi.bussines.service.IRecordService;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.system.mapper.SysUserMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RecordServiceImpl implements IRecordService {
    @Resource
    private RecordMapper recordMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private RecordManager recordManager;

    @Override
    public List<RecordDTO> info(String dialogType, Integer region, Integer mouth) {
        List<RecordDTO> records = recordManager.queryAll(dialogType, region, mouth);
        return records;
    }

    @Override
    public void setBudgetStatus(Long userId, Boolean status) {
        Budget budget = recordManager.selectBudgetByUserId(userId);
        if (budget != null) {
            budget.setEnabled(status);
            recordManager.updateBudget(budget);
        } else {
            throw new ServiceException("未查询到用户的预算信息");
        }
    }

    @Override
    public Map<String, Object> getBudget(Long userId) {
        Map<String, Object> map = new ConcurrentHashMap<>();
        // 查询当前用户状态
        Assert.state(ObjectUtils.isNotEmpty(sysUserMapper.selectUserById(userId)), "非法用户！");
        // 查询该用户预算
        Budget budget = recordManager.selectBudgetByUserId(userId);
        if (budget != null) {
            map.put("userId", budget.getUserId());
            map.put("budget", budget.getBudget());
            map.put("enabled", budget.getEnabled());
            map.put("setBudget", true);
        } else {
            map.put("setBudget", false);
        }
        return map;
    }

    @Override
    public void setBudget(BudgetDTO budgetDTO) {
        // 查询当前用户状态
        Long userId = budgetDTO.getUserId();
        Assert.state(ObjectUtils.isNotEmpty(sysUserMapper.selectUserById(userId)), "非法用户！");
        // 查询是否设置过预算
        Budget budget = recordManager.selectBudgetByUserId(userId);
        if (budget == null) {
            budget = new Budget();
            BeanUtils.copyProperties(budgetDTO, budget);
            budget.setEnabled(true);
            recordManager.insertBudget(budget);
        } else if (budget.getEnabled()) {
            budget.setBudget(budgetDTO.getBudget());
            recordManager.updateBudget(budget);
        }
    }

    @Override
    public Integer add(RecordDTO recordDTO) {
        Record record = new Record();
        BeanUtils.copyProperties(recordDTO, record);
        record.setTime(new Date());
        int i = recordMapper.insert(record);
        return i;
    }
}
