package com.card.mapper;

import com.card.pojo.CardType;
import com.card.pojo.CardTypeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CardTypeMapper {
    int countByExample(CardTypeExample example);

    int deleteByExample(CardTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CardType record);

    int insertSelective(CardType record);

    List<CardType> selectByExample(CardTypeExample example);

    CardType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CardType record, @Param("example") CardTypeExample example);

    int updateByExample(@Param("record") CardType record, @Param("example") CardTypeExample example);

    int updateByPrimaryKeySelective(CardType record);

    int updateByPrimaryKey(CardType record);
}