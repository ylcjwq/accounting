package com.ruoyi.bussines.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.bussines.dto.RecordDTO;
import com.ruoyi.bussines.model.Record;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RecordMapper extends BaseMapper<Record> {
    List<RecordDTO> selectAll(@Param("dialogType") String dialogType, @Param("region") Integer region, @Param("userId") Long userId);

    List<RecordDTO> queryInfoByDate(@Param("dialogType") String dialogType, @Param("region") Integer region, @Param("type") Integer type, @Param("year") String year, @Param("month") String month, @Param("day") String day, @Param("userId") Long userId);
}
