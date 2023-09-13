package com.ruoyi.bussines.tracker;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.ruoyi.bussines.dto.EmailsDTO;
import com.ruoyi.bussines.mapper.EmailsMapper;
import com.ruoyi.bussines.model.Emails;
import com.ruoyi.bussines.model.Expense;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Getter
@Setter
@Slf4j
public class ExpenseTracker {
    private double monthlyThreshold;
    private double currentMonthExpense;

    public ExpenseTracker(double monthlyThreshold) {
        this.monthlyThreshold = monthlyThreshold;
    }

    public void addExpense(Expense expense) {
        if (isExceedingThreshold(expense)) {
            sendReminder();
        } else {
            // 处理如果有超支预警的话，需要删除预警
            deleteReminder();
        }
    }

    private boolean isExceedingThreshold(Expense expense) {
        this.currentMonthExpense = expense.getAmount();
        return this.currentMonthExpense > this.monthlyThreshold;
    }

    private void sendReminder() {
        Long userId = SecurityUtils.getUserId();
        if (userId == null) {
            throw new ServiceException("非法用户！");
        }
        EmailsMapper emailsMapper = SpringUtils.getBean(EmailsMapper.class);
        Emails emails = new Emails();
        emails.setId(IdWorker.getId());
        emails.setTopic("超支提醒");
        emails.setContent(StrUtil.format("您本月的支出已经超出预算啦！您本月的支出为：{}，预算为：{}", this.currentMonthExpense, this.monthlyThreshold));
        emails.setUserId(userId);
        emails.setSendTime(new Date());
        emails.setIsRead(false);
        EmailsDTO dto = emailsMapper.selectExpenseByTopic("超支提醒", userId);
        if (dto != null) {
            emails.setId(dto.getId());
            emailsMapper.updateById(emails);
        } else {
            emailsMapper.insert(emails);
        }
    }

    private void deleteReminder() {
        Long userId = SecurityUtils.getUserId();
        if (userId == null) {
            throw new ServiceException("非法用户！");
        }
        EmailsMapper emailsMapper = SpringUtils.getBean(EmailsMapper.class);
        EmailsDTO dto = emailsMapper.selectExpenseByTopic("超支提醒", userId);
        if (dto != null) {
            emailsMapper.deleteById(dto.getId());
        }
    }
}

