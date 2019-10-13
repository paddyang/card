package com.card.service.impl;

import com.card.mapper.LogCheckMapper;
import com.card.pojo.LogCheck;
import com.card.pojo.LogCheckExample;
import com.card.service.LogCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: yangPan
 * @date: 2019/10/11 19:40
 * @description:
 */
@Service
public class LogCheckServiceImpl implements LogCheckService {

    @Autowired
    private LogCheckMapper logCheckMapper;

    @Override
    public List<LogCheck> getCheckInfo() {
        LogCheckExample example=new LogCheckExample();
        example.setOrderByClause("id desc");
        example.setDistinct(true);
        return logCheckMapper.selectByExample(example);
    }

    @Override
    public void add(LogCheck logCheck){
        logCheckMapper.insertSelective(logCheck);
    }
}
