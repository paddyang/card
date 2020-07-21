package com.card.service.impl;

import com.card.mapper.DiyRespMapper;
import com.card.pojo.DiyResp;
import com.card.service.DiyRespService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiyRespServiceImpl implements DiyRespService {

    @Autowired
    private DiyRespMapper diyRespMapper;

    @Override
    public DiyResp getByPath(String path){
        return diyRespMapper.getByPath(path);
    }
}
