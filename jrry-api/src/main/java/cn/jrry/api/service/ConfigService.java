package cn.jrry.api.service;

import java.util.List;
import java.util.Map;

public interface ConfigService {

    int count(Map<String,String> params);
    List<Map<String,Object>> query(Map<String,String> params);
}
