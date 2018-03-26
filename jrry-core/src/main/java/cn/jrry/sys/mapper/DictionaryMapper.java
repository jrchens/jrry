package cn.jrry.sys.mapper;

import cn.jrry.sys.domain.Dictionary;

import java.util.List;
import java.util.Map;

public interface DictionaryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Dictionary record);

    Dictionary selectByPrimaryKey(Long id);

    List<Dictionary> selectAll();

    int updateByPrimaryKey(Dictionary record);

    int removeByPrimaryKey(Dictionary record);

    int count(Map<String, Object> record);

    List<Dictionary> select(Map<String, Object> record);

    Dictionary selectByCode(String code);

    List<Dictionary> selectByParentCode(String parentCode);
}