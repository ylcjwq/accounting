package com.ruoyi.bussines.service.Impl;

import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.ruoyi.bussines.dto.RecordDTO;
import com.ruoyi.bussines.mapper.AccountPicMapper;
import com.ruoyi.bussines.mapper.RecordMapper;
import com.ruoyi.bussines.model.AccountPic;
import com.ruoyi.bussines.model.Record;
import com.ruoyi.bussines.service.IPicParseService;
import com.ruoyi.bussines.utils.BaiDuAiUtils;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;

import static com.ruoyi.bussines.utils.Base64Utils.getFileContentAsBase64;

@Service
public class PicParseServiceImpl implements IPicParseService {
    @Resource
    private AccountPicMapper accountPicMapper;
    @Value("${ruoyi.profile}")
    private String profile;

    @Override
    public RecordDTO parsePic(String filePath) {
        // 校验是否存在
        String contentAsBase64 = null;
        try {
            filePath = filePath.replace("http://43.138.195.96:9999/profile", profile);
            contentAsBase64 = getFileContentAsBase64(filePath, true);
            AccountPic pic = accountPicMapper.selectOne(new LambdaQueryWrapper<AccountPic>()
                    .eq(AccountPic::getPath, filePath)
                    .eq(AccountPic::getIsExisted, true));
            JSONArray jsonArray = null;
            Long id = null;
            if (ObjectUtils.isNull(pic)) {
                jsonArray = BaiDuAiUtils.baiduAiParsePic(contentAsBase64);
                AccountPic accountPic = new AccountPic();
                accountPic.setId(IdWorker.getId());
                accountPic.setParsedFormat(JSONArray.toJSONString(jsonArray));
                accountPic.setPath(filePath);
                accountPic.setIsExisted(true);
                accountPic.setCreateTime(new Date());
                accountPic.setCreateBy(SecurityUtils.getUserId());
                accountPic.setUpdateTime(new Date());
                accountPic.setUpdateBy(SecurityUtils.getUserId());
                accountPicMapper.insert(accountPic);
                id = accountPic.getId();
            } else {
                jsonArray = JSONArray.parseArray(pic.getParsedFormat());
                id = pic.getId();
            }
            if (jsonArray.isEmpty()) {
                throw new ServiceException("解析失败，请检查图片是否清晰或图片是否完整。");
            }
            // 添加支出记录，默认为银行卡支出
            // 前缀
            String[] prefixMoneyStr = new String[]{"付款金额", "实付：", "实收：", "总消费金额：", "消费合计：", "应收：", "应付：", "总消费：", "应收金额："};
            Double cost = getCost(jsonArray, prefixMoneyStr);
            if (ObjectUtils.isNull(cost) || cost.equals(00.00)) {
                accountPicMapper.deleteById(id);
                throw new ServiceException("当前账单小票格式并不支持解析，可请联系管理员更新。");
            }
            RecordDTO record = new RecordDTO();
            record.setDialogType("spend");
            record.setNumber(cost);
            record.setRegion(1);
            record.setUserId(SecurityUtils.getUserId());
            record.setTime(new Date());
            record.setRemark("记录图片账单");
            return record;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取金额
     *
     * @param jsonArray
     * @param prefixMoneyStr
     * @return
     */
    private static Double getCost(JSONArray jsonArray, String[] prefixMoneyStr) {
        for (int i = 0; i < jsonArray.size(); i++) {
            Object json = jsonArray.get(i);
            for (int j = 0; j < prefixMoneyStr.length; j++) {
                String moneyPrefix = prefixMoneyStr[j];
                if (json.toString().contains(moneyPrefix)) {
                    json = json.toString().replaceAll(":", ": ");
                    String moneyStr = json.toString().substring(json.toString().indexOf(moneyPrefix) + moneyPrefix.length());
                    // 如果它是空的话？
                    // 向下拿一个
                    if (StringUtils.isBlank(moneyStr)) {
                        json = jsonArray.get(i + 1);
                        moneyStr = json.toString();
                    }
                    // 判断monetStr里面都是数字
                    StringBuilder sb = new StringBuilder();
                    for (int c = 0; c < moneyStr.length(); c++) {
                        char charAt = moneyStr.charAt(c);
                        if (isNum(charAt) || charAt == '.') {
                            sb.append(charAt);
                        }
                    }
                    return Double.valueOf(sb.toString());
                }
            }
        }
        return null;
    }

    /**
     * 判断字符是否为数字
     *
     * @param msg
     * @return
     */
    public static boolean isNum(char msg) {

        if (msg >= '0' && msg <= '9') {
            return true;
        }
        return false;
    }
}
