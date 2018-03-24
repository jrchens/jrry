package cn.jrry.sys.mapper;

import java.util.List;
import java.util.Map;

import cn.jrry.sys.domain.Group;
import cn.jrry.sys.domain.Group;

public interface GroupMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Group record);

    Group selectByPrimaryKey(Long id);

    List<Group> selectAll();

    int updateByPrimaryKey(Group record);

    int removeByPrimaryKey(Group record);

    int count(Map<String, Object> record);

    List<Group> select(Map<String, Object> record);
}