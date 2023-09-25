package com.ruoyi.bussines.service.Impl;

import com.ruoyi.bussines.dto.BudgetDTO;
import com.ruoyi.bussines.dto.RecordDTO;
import com.ruoyi.bussines.manager.RecordManager;
import com.ruoyi.bussines.mapper.RecordMapper;
import com.ruoyi.bussines.model.Budget;
import com.ruoyi.bussines.model.Record;
import com.ruoyi.bussines.service.IRecordService;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.system.service.ISysDictTypeService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class RecordServiceImpl implements IRecordService {
    @Resource
    private RecordMapper recordMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private RecordManager recordManager;
    @Autowired
    private ISysDictTypeService dictTypeService;
    @Autowired
    private ISysDictDataService dictDataService;

    @Override
    public List<RecordDTO> info(String dialogType, Integer region) {
        List<RecordDTO> records = recordManager.queryAll(dialogType, region);
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

    @Override
    public Object queryInfoByDate(String dialogType, Integer region, String year, String month, String day) {
        return recordManager.queryInfoByDate(dialogType, region, year, month, day);
    }

    @Override
    public Map<String, Object> queryType() {
        // 查询收入/支出类型
        String dictType = "expense_or_income_type";
        List<SysDictData> dictDatas = dictTypeService.selectDictDataByType(dictType);
        // 以dict_label为key，以dict_valuer为value
        return dictDatas.stream().collect(Collectors.toMap(SysDictData::getDictValue, SysDictData::getDictLabel));
    }

    @Override
    public void addType(String type) {
        type = type.replaceAll("\"", "");
        List<SysDictData> dictDatas = dictTypeService.selectDictDataByType("expense_or_income_type");
        // step1 检查是否已存在该类型
        String finalType = type;
        dictDatas.forEach(dictData -> {
            if (dictData.getDictLabel().equals(finalType)) {
                throw new ServiceException("已存在该类型");
            }
        });
        // step2 获取当前最大的排序
        Long maxSort = dictDatas.stream().map(SysDictData::getDictSort).max(Long::compareTo).get();
        // step3 新增
        SysDictData dictData = new SysDictData();
        dictData.setDictType("expense_or_income_type");
        dictData.setDictLabel(type);
        dictData.setDictValue(maxSort + "");
        dictData.setDictSort(maxSort + 1);
        dictData.setCreateBy("admin");
        dictData.setCreateTime(new Date());
        dictDataService.insertDictData(dictData);
    }

    @Override
    public void deleteType(String code) {
        List<SysDictData> dictDatas = dictTypeService.selectDictDataByType("expense_or_income_type");
        // step1 检查是否存在该类型
        SysDictData eqDictData = getEqDictData(code, dictDatas);
        if (CollectionUtils.isEmpty(dictDatas)) {
            throw new ServiceException("不存在该类型！");
        }
        if (dictDatas.size() > 0 && eqDictData == null) {
            throw new ServiceException("不存在该类型！");
        }
        // step2 删除
        dictDataService.deleteDictDataById(eqDictData.getDictCode());
    }

    private static SysDictData getEqDictData(String code, List<SysDictData> dictDatas) {
        for (SysDictData dictData : dictDatas) {
            if (dictData.getDictValue().equals(code)) {
                return dictData;
            }
        }
        return null;
    }
}
