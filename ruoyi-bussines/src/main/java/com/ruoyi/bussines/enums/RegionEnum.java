package com.ruoyi.bussines.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum RegionEnum {
    WX_WALLET(0, "微信钱包"),
    WX_CHANGE_PASS(1, "微信零钱通"),
    ALIPAY_BALANCE(2, "支付宝余额"),
    ALIPAY_YUEBAO(3, "银行卡"),
    BANK_CARDS(4, "基金"),
    FUNDS(5, "支付宝余额宝");

    @EnumValue
    @JsonValue
    private Integer type;
    private String desc;

    RegionEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static RegionEnum of(Integer type) {
        if (type == null) {
            return null;
        }
        return Arrays.stream(values()).filter(procurementValue -> procurementValue.type.equals(type)).findFirst().orElse(null);
    }
}
