package cn.jrry.sys.service;

import cn.jrry.sys.domain.Config;

import java.util.List;

public interface ConfigService {
    int deleteByPrimaryKey(String cfgCode);

    int insert(Config record);

    Config selectByPrimaryKey(String cfgCode);

    List<Config> selectAll();

    int updateByPrimaryKey(Config record);
    int removeByPrimaryKey(Config record);

}
