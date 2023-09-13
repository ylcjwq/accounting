package com.ruoyi.bussines.manager.impl;

import com.ruoyi.bussines.dto.EmailsDTO;
import com.ruoyi.bussines.manager.EmailsManager;
import com.ruoyi.bussines.mapper.EmailsMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@AllArgsConstructor
@Component
public class EmailsManagerImpl implements EmailsManager {
    @Resource
    private EmailsMapper emailsMapper;

    @Override
    public List<EmailsDTO> getList(Long userId) {
        return emailsMapper.getList(userId);
    }
}
