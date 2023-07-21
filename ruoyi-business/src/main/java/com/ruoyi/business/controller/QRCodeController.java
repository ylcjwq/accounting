package com.ruoyi.business.controller;

import com.ruoyi.business.util.QRCodeUtil;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.uuid.IdUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/business/qrCode")
public class QRCodeController {

    @GetMapping("/create1")
    public AjaxResult createQR() {
        // 随机生成一个uuid
        String uuid = IdUtils.simpleUUID();
        // 生成二维码
        try {
            QRCodeUtil.createImagePng(uuid, 300, "e:\\test.png");
            return AjaxResult.success();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/create2")
    public AjaxResult createLogoQR() {
        // 随机生成一个uuid
        String uuid = IdUtils.simpleUUID();
        // 生成二维码
        try {
            QRCodeUtil.createQRCodeInnerLogoPng(uuid, 300, "C:\\Users\\admin\\Desktop\\logo.jpg", 50, "e:\\test.png");
            return AjaxResult.success();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
