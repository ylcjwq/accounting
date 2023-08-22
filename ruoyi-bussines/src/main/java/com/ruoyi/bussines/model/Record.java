package com.ruoyi.bussines.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    private Integer number;
    private String remark;
    private Date time;
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
