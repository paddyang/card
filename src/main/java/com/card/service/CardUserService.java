package com.card.service;

import com.card.pojo.wwj.CardUser;


public interface CardUserService {

    CardUser getCardUser(String deviceId);

    int insert(CardUser cardUser);

}
