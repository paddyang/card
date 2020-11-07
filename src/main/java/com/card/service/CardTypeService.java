package com.card.service;

import com.card.pojo.CardType;

import java.util.List;

/**
 * @author: yangPan
 * @date: 2019/10/23 21:42
 * @description:
 */
public interface CardTypeService {

    CardType getByCardType(String cardType);

    CardType getById(int id);

    List<CardType> getAllType(String status);

    int addCardType(String cardType,String name);

    int updateById(int id);
}
