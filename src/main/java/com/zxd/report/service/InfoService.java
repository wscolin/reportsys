package com.zxd.report.service;

import com.zxd.report.mapper.InfoDao;
import com.zxd.report.model.Info;

public interface InfoService {
    Info queryByName(String name);

}
