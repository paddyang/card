package com.card.service.impl;

import com.card.constant.RedisKey;
import com.card.constant.StatusEnum;
import com.card.mapper.CardTypeMapper;
import com.card.pojo.CardType;
import com.card.pojo.CardTypeExample;
import com.card.service.CardTypeService;
import com.card.utils.GsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
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

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Override
    public CardType getByCardType(String type) {
        String cardTypeStr= (String) redisTemplate.opsForHash().get(RedisKey.CARD_TYPE, type);
        CardType cardType = GsonUtils.GsonToBean(cardTypeStr, CardType.class);
        //CardType cardType = (CardType) redisTemplate.opsForHash().get(RedisKey.CARD_TYPE,type);
        if (null==cardType) {
            cardType = getCardType(type);
            List<CardType> allType = getAllType(StatusEnum.VALID.getCode());
            HashMap<String, String> map = new HashMap<>();
            for (CardType ct :allType) {
                map.put(ct.getCardType(),ct.toString());
            }
            redisTemplate.opsForHash().putAll(RedisKey.CARD_TYPE,map);
        }
        return cardType;
    }

    private CardType getCardType(String cardType) {
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
    public List<CardType> getAllType(String status) {
        CardTypeExample example=new CardTypeExample();
        CardTypeExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo(status);
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
        type.setStatus(StatusEnum.VALID.getCode());
        return cardTypeMapper.insertSelective(type);
    }

    @Override
    public int updateById(int id) {
        CardType type=new CardType();
        type.setId(id);
        type.setUpdateTime(new Date());
        type.setStatus(StatusEnum.INVALID.getCode());
        return cardTypeMapper.updateByPrimaryKeySelective(type);

    }


}
