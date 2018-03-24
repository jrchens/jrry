package cn.jrry.sys.service;

import java.util.List;
import java.util.Map;

import cn.jrry.sys.domain.Permission;

public interface PermissionService {
    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    Permission selectByPrimaryKey(Long id);

    List<Permission> selectAll();

    int updateByPrimaryKey(Permission record);

    int removeByPrimaryKey(Permission record);

    int count(Map<String, Object> record);

    List<Permission> select(Map<String, Object> record);
}