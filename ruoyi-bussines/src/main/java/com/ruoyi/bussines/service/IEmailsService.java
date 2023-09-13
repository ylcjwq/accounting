package com.ruoyi.bussines.service;

import java.util.Map;

public interface IEmailsService {
    /**
     * 查询是否超出预算
     *
     * @return true 超出多少预算 false 未超出预算
     */
    Map<String, Object> info();
}
