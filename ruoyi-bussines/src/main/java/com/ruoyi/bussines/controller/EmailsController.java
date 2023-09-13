package com.ruoyi.bussines.controller;

import com.ruoyi.bussines.service.IEmailsService;
import com.ruoyi.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/emails")
@Api(tags = "警示消息管理")
public class EmailsController {
    @Resource
    private IEmailsService emailsService;

    @GetMapping("/info")
    @Operation(summary = "查询是否超出预算")
    public R<Map<String, Object>> info() {
        return R.ok(emailsService.info());
    }
}
