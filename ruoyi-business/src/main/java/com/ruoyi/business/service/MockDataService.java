package com.ruoyi.business.service;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.ruoyi.business.mapper.MockMapper;
import com.ruoyi.business.mock.MockData;
import com.ruoyi.business.model.QrCode;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class MockDataService {
    @Resource
    private MockMapper mockMapper;
    @Resource
    private TokenService tokenService;
    @Resource
    private ISysUserService userService;

    public void save(MockData data) {
        String id = IdWorker.getIdStr();
        QrCode qrCode = new QrCode();
        // 存入数据库中QrCode
        BeanUtils.copyProperties(data, qrCode);
        mockMapper.insert(qrCode);
        log.info("当前id为：{}", id);
    }

    public QrCode get(String id) {
        return mockMapper.selectById(id);
    }

    public QrCode updateScanCount(String id){
        QrCode code = mockMapper.selectById(id);
        code.setCount(code.getCount() == null ? 1 : code.getCount() + 1);
        mockMapper.updateById(code);
        return code;
    }

    public SysUser scan(String id, String token) {
        updateScanCount(id);
        String username = tokenService.getUsernameFromToken(token);
        SysUser user = userService.selectUserByUserName(username);
        return user;
    }
}