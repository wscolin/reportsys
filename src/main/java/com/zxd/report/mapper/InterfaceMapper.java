package com.zxd.report.mapper;

import java.util.List;
import java.util.Map;

public interface InterfaceMapper {
    List<Map> getqqfiles();
    int SetqqYtq(String qqdh);
    int savefeedback(Map map);
}