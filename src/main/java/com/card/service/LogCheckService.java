package com.card.service;

import com.card.pojo.LogCheck;

import java.util.List;

/**
 * @author: yangPan
 * @date: 2019/10/11 19:40
 * @description:
 */
public interface LogCheckService {

    List<LogCheck> getCheckInfo();

    void add(LogCheck logCheck);
}
