package com.ruoyi.bussines.model;

import com.ruoyi.bussines.dto.RecordDTO;
import lombok.Data;

import java.util.List;

@Data
public class DayModel {
    private String number;
    private List<RecordDTO> pay;
}
