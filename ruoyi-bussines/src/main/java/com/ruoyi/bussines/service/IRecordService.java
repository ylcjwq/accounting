package com.ruoyi.bussines.service;

import com.ruoyi.bussines.dto.BudgetDTO;
import com.ruoyi.bussines.dto.RecordDTO;

import java.util.List;
import java.util.Map;

public interface IRecordService {
    /**
     * 记录收入支出
     *
     * @param recordDTO 收入/支出参数
     */
    Integer add(RecordDTO recordDTO);

    /**
     * 设置预算
     *
     * @param budgetDTO 预算参数
     */
    void setBudget(BudgetDTO budgetDTO);

    /**
     * 获取预算信息/是否设置预算
     *
     * @param userId 当前登录用户
     * @return setBudget false 当前用户没有设置预算
     * @return setBudget true  当前用户设置了预算
     * @return enabled false 不启用 / true 启用
     * @return budget   预算数据
     */
    Map<String, Object> getBudget(Long userId);

    /**
     * 开启/关闭预算状态
     *
     * @param userId 用户id
     * @param status 设置的状态
     */
    void setBudgetStatus(Long userId, Boolean status);

    /**
     * 查询收入/支出信息
     *
     * @param dialogType 支出：spand，收入：revenue
     * @param region     支出/收入方式
     * @param mouth      月份
     * @return
     */
    List<RecordDTO> info(String dialogType, Integer region, Integer mouth);
}
