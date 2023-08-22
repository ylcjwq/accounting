package com.ruoyi.bussines.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RecordDTO {
    private Long userId;
    private String dialogType;
    private Integer region;
    private Integer number;
    private String remark;
}
