package com.zxd.report.service.impl;

import com.zxd.report.mapper.InfoDao;
import com.zxd.report.model.Info;
import com.zxd.report.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InfoServiceImpl implements InfoService {
    @Autowired
    private InfoDao infoDao;
    @Override
    public Info queryByName(String name) {
        return infoDao.queryByName(name);
    }
}
