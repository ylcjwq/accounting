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
     * 查询收入/记录
     *
     * @param dialogType 收入/支出
     * @param region     方式
     * @return 返回查询结果
     */
    List<RecordDTO> info(String dialogType, Integer region);

    /**
     * 查询收入/记录-按照年/年月/年月日
     *
     * @param dialogType 收入/支出
     * @param region     方式
     * @param year       年
     * @param month      月
     * @param day        日
     * @return 返回查询结果
     */
    Object queryInfoByDate(String dialogType, Integer region, String year, String month, String day);

    /**
     * 查询收入/支出方式
     *
     * @return 返回查询结果 0=微信钱包，1=微信零钱通，2=支付宝余额，3=银行卡，4=基金，5=支付宝余额宝
     */
    Map<String, Object> queryType();

    /**
     * 添加收入/支出方式
     *
     * @param type 收入/支出方式
     */
    void addType(String type);

    /**
     * 删除收入/支出方式
     *
     * @param code 需要删除的标识code，0=微信钱包，1=微信零钱通，2=支付宝余额，3=银行卡，4=基金，5=支付宝余额宝
     */
    void deleteType(String code);
}
