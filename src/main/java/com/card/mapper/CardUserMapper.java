package com.card.mapper;

import com.card.pojo.wwj.CardUser;

import java.util.List;

public interface CardUserMapper {
        int addCardUser(CardUser cardUser);
        List<CardUser> getCardUser(String deviceId);
}