package com.ruoyi.bussines.controller;

import com.ruoyi.bussines.service.IPicParseService;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.framework.config.ServerConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;

@RestController
@RequestMapping("/baiduAIParse")
@Api(tags = "调用百度ia扫描图片，识别文字")
public class PicParseController {
    @Resource
    private IPicParseService picParseService;
    @Autowired
    private ServerConfig serverConfig;

    /**
     * 需要解析的文件上传（单个）
     */
    @PostMapping("/upload")
    public AjaxResult uploadFile(MultipartFile file) throws Exception {
        try {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", url);
            ajax.put("fileName", fileName);
            ajax.put("newFileName", FileUtils.getName(fileName));
            ajax.put("originalFilename", file.getOriginalFilename());
            return ajax;
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }


    @ApiOperation("调用百度ia扫描图片，识别文字")
    @GetMapping("/baiduAIParsePic")
    public AjaxResult baiduAIParsePic(@RequestParam("filePath") String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.getAbsolutePath().endsWith(".jpg") && file.getAbsolutePath().endsWith(".png") && file.getAbsolutePath().endsWith(".jpeg") && file.getAbsolutePath().endsWith(".bmp")) {
            return AjaxResult.error("文件不存在或者文件格式不正确");
        }
        return AjaxResult.success(picParseService.parsePic(filePath));
    }
}
