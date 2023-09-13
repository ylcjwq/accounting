package com.ruoyi.bussines.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.bussines.dto.EmailsDTO;
import com.ruoyi.bussines.model.Emails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmailsMapper extends BaseMapper<Emails> {

    List<EmailsDTO> getList(@Param("userId") Long userId);

    EmailsDTO selectExpenseByTopic(@Param("topic") String topic, @Param("userId") Long userId);
}
