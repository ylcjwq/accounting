package com.ruoyi.bussines.service.Impl;

import com.ruoyi.bussines.dto.EmailsDTO;
import com.ruoyi.bussines.manager.RecordManager;
import com.ruoyi.bussines.mapper.EmailsMapper;
import com.ruoyi.bussines.service.IEmailsService;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EmailsServiceImpl implements IEmailsService {
    @Resource
    private EmailsMapper emailsMapper;
    @Resource
    private RecordManager recordManager;

    @Override
    public Map<String, Object> info() {
        Long userId = SecurityUtils.getUserId();
        if (userId == null) {
            throw new ServiceException("非法用户！");
        }
        Boolean flag = recordManager.asyncBudgetRemind();
        Map<String, Object> map = new ConcurrentHashMap<>();
        if (!flag) {
            map.put("isExceedingThreshold", false);
            map.put("content", "您本月的支出未超出预算！");
        } else {
            // 查询emails表中是否有当月topic为超支提醒的邮件
            EmailsDTO emailsDTO = emailsMapper.selectExpenseByTopic("超支提醒", userId);
            if (ObjectUtils.isEmpty(emailsDTO)) {
                map.put("isExceedingThreshold", false);
                map.put("content", "您本月的支出未超出预算！");
            } else {
                map.put("isExceedingThreshold", true);
                map.put("content", emailsDTO.getContent());
            }
        }
        return map;
    }
}
