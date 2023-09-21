package com.ruoyi.bussines.model;

import lombok.Data;

import java.util.List;

@Data
public class MonthModel {
    private String number;
    private List<DayModel> day;
}
