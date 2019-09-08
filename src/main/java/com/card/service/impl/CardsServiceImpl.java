package com.card.service.impl;

import com.card.constant.CardStatusEnum;
import com.card.mapper.CardMapper;
import com.card.pojo.Card;
import com.card.pojo.CardExample;
import com.card.service.CardsService;
import com.card.utils.IDUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author: yangPan
 * @date: 2019/7/3 20:30
 * @description:
 */
@Service
public class CardsServiceImpl implements CardsService {
    @Autowired
    private CardMapper cardMapper;

    @Override
    public Card getByCardNo(String cardNo) {
        CardExample example = new CardExample();
        CardExample.Criteria criteria = example.createCriteria();
        criteria.andCardNoEqualTo(cardNo);
        List<Card> cards = cardMapper.selectByExample(example);
        return cards.size() == 0 ? null : cards.get(0);
    }

    @Override
    public void activate(int cardId, String b) {
        Card card = new Card();
        card.setId(cardId);
        card.setUid(b);
        card.setStatus(CardStatusEnum.ACTIVATED.getCode());
        card.setUseTime(new Date());
        cardMapper.updateByPrimaryKeySelective(card);
    }

    @Override
    public int getNoUsedCardNum(int userId) {
        CardExample example = new CardExample();
        CardExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andStatusEqualTo(CardStatusEnum.NOT_ACTIVE.getCode());
        criteria.andIsOkEqualTo(0);
        return cardMapper.countByExample(example);
    }

    @Override
    public void batchAddCard(int userId, int num, int days, String safeCode) {
        Date date = new Date();
        for (int i = 0; i < num; i++) {
            //生成12位卡密
            String cardNo = IDUtils.getPassword(12).toUpperCase();
            Card card = new Card(cardNo, 0, date, userId, 0, days);
            card.setSafeCode(safeCode);
            cardMapper.insertSelective(card);
        }
    }

    @Override
    public List<Card> getActiveCard(int userId) {
        CardExample example = new CardExample();
        example.setOrderByClause("create_time desc");
        CardExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andStatusEqualTo(CardStatusEnum.ACTIVATED.getCode());
        return cardMapper.selectByExample(example);
    }

    @Override
    public Card getByCardId(int cardId) {
        return cardMapper.selectByPrimaryKey(cardId);
    }

    @Override
    public void lock(int cardId) {
        Card card = new Card();
        card.setId(cardId);
        card.setStatus(CardStatusEnum.LOCKED.getCode());
        cardMapper.updateByPrimaryKeySelective(card);
    }

    @Override
    public void unLock(int cardId) {
        Card card = new Card();
        card.setId(cardId);
        card.setStatus(CardStatusEnum.ACTIVATED.getCode());
        cardMapper.updateByPrimaryKeySelective(card);
    }

    @Override
    public List<Card> getLockCard(int userId) {
        CardExample example = new CardExample();
        example.setOrderByClause("create_time desc");
        CardExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andStatusEqualTo(CardStatusEnum.LOCKED.getCode());
        return cardMapper.selectByExample(example);
    }

    @Override
    public List<Card> getNotUsedCards(int userId) {
        CardExample example = new CardExample();
        example.setOrderByClause("create_time desc");
        CardExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andStatusEqualTo(CardStatusEnum.NOT_ACTIVE.getCode());
        criteria.andIsOkEqualTo(0);
        return cardMapper.selectByExample(example);
    }
}
