package com.card.service.impl;

import com.card.mapper.CardTypeMapper;
import com.card.pojo.CardType;
import com.card.pojo.CardTypeExample;
import com.card.service.CardTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author: yangPan
 * @date: 2019/10/23 21:43
 * @description:
 */
@Service
public class CardTypeServiceImpl implements CardTypeService {

    @Autowired
    private CardTypeMapper cardTypeMapper;


    @Override
    public CardType getByCardType(String cardType) {
        CardTypeExample example=new CardTypeExample();
        CardTypeExample.Criteria criteria = example.createCriteria();
        criteria.andCardTypeEqualTo(cardType);
        return cardTypeMapper.selectByExample(example).get(0);
    }

    @Override
    public CardType getById(int id) {
        return cardTypeMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<CardType> getAllType() {
        CardTypeExample example=new CardTypeExample();
        CardTypeExample.Criteria criteria = example.createCriteria();
        criteria.andIdNotEqualTo(0);
        return cardTypeMapper.selectByExample(example);
    }

    @Override
    public int addCardType(String cardType,String name) {
        CardType type=new CardType();
        type.setCardType(cardType);
        type.setName(name);
        Date date = new Date();
        type.setCreateTime(date);
        type.setUpdateTime(date);
        return cardTypeMapper.insertSelective(type);
    }
}
