package com.ruoyi.business.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@TableName(value = "qr_code")
public class QrCode implements Serializable {
    /**
     * 主键，雪花算法
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String content;

    private Integer count;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
