package com.ruoyi.bussines.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@TableName("record")
public class Record {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String dialogType;
    private Integer region;
    private Double number;
    private String remark;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;
    private String projectName;
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
