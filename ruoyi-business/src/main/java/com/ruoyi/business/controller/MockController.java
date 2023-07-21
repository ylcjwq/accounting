package com.ruoyi.business.controller;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.ruoyi.business.annotation.UserInfo;
import com.ruoyi.business.mock.MockData;
import com.ruoyi.business.model.QrCode;
import com.ruoyi.business.service.MockDataService;
import com.ruoyi.business.util.QRCodeUtil;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author cube.li
 * @date 2021/3/28 15:02
 * @description
 */
@RestController
@RequestMapping("/data")
@Slf4j
public class MockController {

    @Resource
    private MockDataService dataService;

    @Resource
    private HttpServletResponse response;

    private static final String QR_CODE_SCAN_URL = "http://localhost:9999/data/scan?id=";

    /*@Value("${cube.qr-code-scan-url}")
    private String qrCodeScanUrl;*/

    @PostMapping("/save")
    public AjaxResult save(@RequestBody @Valid MockData data) {
        dataService.save(data);
        return AjaxResult.success();
    }

    @GetMapping("/qrCode")
    public void qrCode() throws Exception {
        MockData data = new MockData();
        String id = IdWorker.getIdStr();
        data.setId(id);
        data.setCount(0);
        data.setContent(QR_CODE_SCAN_URL + id);
        dataService.save(data);
        BufferedImage image = QRCodeUtil.createImage(QR_CODE_SCAN_URL + id, 300);
        ImageIO.write(image, "png", new File("E:\\test.png"));
    }

    @RequestMapping("scan")
    public AjaxResult qrScanCount(@RequestParam("id") String id,@RequestParam("token") String token) {
        QrCode data = dataService.get(id);
        Assert.notNull(data, "未查找到对应数据");
        log.info("活动id = {},活动二维码扫描次数 = {}", id, data.getCount());
        return AjaxResult.success(dataService.scan(id,token));
    }
}