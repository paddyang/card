package com.card.service.impl;

import com.card.mapper.CardUserMapper;
import com.card.pojo.wwj.CardUser;
import com.card.service.CardUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardUserServiceImpl implements CardUserService {

    @Autowired
    private CardUserMapper cardUserMapper;

    @Override
    public CardUser getCardUser(String deviceId) {
        List<CardUser> cardUsers = cardUserMapper.getCardUser(deviceId);
        return cardUsers.size()==0?null:cardUsers.get(0);
    }

    @Override
    public int insert(CardUser cardUser) {
        return cardUserMapper.addCardUser(cardUser);
    }
}
