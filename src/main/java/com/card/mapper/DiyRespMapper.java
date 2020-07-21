package com.card.mapper;

import com.card.pojo.Card;
import com.card.pojo.CardExample;
import com.card.pojo.DiyResp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DiyRespMapper {

    DiyResp getByPath(String path);

}