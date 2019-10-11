package com.card.mapper;

import com.card.pojo.LogCheck;
import com.card.pojo.LogCheckExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LogCheckMapper {
    int countByExample(LogCheckExample example);

    int deleteByExample(LogCheckExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LogCheck record);

    int insertSelective(LogCheck record);

    List<LogCheck> selectByExample(LogCheckExample example);

    LogCheck selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LogCheck record, @Param("example") LogCheckExample example);

    int updateByExample(@Param("record") LogCheck record, @Param("example") LogCheckExample example);

    int updateByPrimaryKeySelective(LogCheck record);

    int updateByPrimaryKey(LogCheck record);
}