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
@TableName("account_pic")
public class AccountPic {
    @TableId(type = IdType.AUTO)
    private Long id;
    // @TableField(typeHandler = JSONArrayTypeHandler.class)
    private String parsedFormat;
    private Boolean isExisted;
    private String path;
    private Date createTime;
    private Long createBy;
    private Date updateTime;
    private Long updateBy;
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
