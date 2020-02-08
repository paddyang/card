package com.card.service;

import com.card.pojo.Card;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * @author: yangPan
 * @date: 2019/7/3 20:29
 * @description:
 */
public interface CardsService {

    Card getByCardNo(String cardNo);

    /**
     * 激活
     * @param id
     * @param b
     */
    void activate(int id, String b);

    /**
     * 查未使用卡密数量
     * @param userId
     * @return
     */
    int getNoUsedCardNum(int userId);

    /**
     * 批量新增
     * @param userId
     * @param num
     * @param days
     * @param safeCode
     */
    void batchAddCard(int userId, int num, int days, String safeCode);

    /**
     * 查已激活的卡
     * @param userId
     * @return
     */
    List<Card> getActiveCard(int userId);

    /**
     * 用id查卡
     * @param cardId
     * @return
     */
    Card getByCardId(int cardId);

    /**
     * 锁卡
     * @param cardId
     */
    void lock(int cardId);

    /**
     * 解锁
     * @param cardId
     */
    void unLock(int cardId);

    /**
     * 查用户已锁定卡密
     * @param userId
     * @return
     */
    List<Card> getLockCard(int userId);

    /**
     * 获取未用卡密
     * @param userId
     * @return
     */
    List<Card> getNotUsedCards(int userId);

    Card getByCardNoAndType(String cardNo, String type);

    /**
     * 更新uid
     * @param id
     * @param b
     */
    void updateUid(Integer id, String b);
}
