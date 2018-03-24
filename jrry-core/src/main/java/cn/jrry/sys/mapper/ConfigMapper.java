package cn.jrry.sys.mapper;

import java.util.List;

import cn.jrry.sys.domain.Config;
import cn.jrry.sys.domain.Config;

public interface ConfigMapper {
    int deleteByPrimaryKey(String cfgCode);

    int insert(Config record);

    Config selectByPrimaryKey(String cfgCode);

    List<Config> selectAll();

    int updateByPrimaryKey(Config record);
    int removeByPrimaryKey(Config record);
}