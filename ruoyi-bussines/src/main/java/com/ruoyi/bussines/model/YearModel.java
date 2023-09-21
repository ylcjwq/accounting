package com.ruoyi.bussines.model;

import lombok.Data;

import java.util.List;

@Data
public class YearModel {
    private String number;
    private List<MonthModel> month;
}
