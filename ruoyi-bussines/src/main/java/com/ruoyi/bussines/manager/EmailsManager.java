package com.ruoyi.bussines.manager;

import com.ruoyi.bussines.dto.EmailsDTO;

import java.util.List;

public interface EmailsManager {

    List<EmailsDTO> getList(Long userId);
}
