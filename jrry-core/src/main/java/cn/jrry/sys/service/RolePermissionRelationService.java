package cn.jrry.sys.service;

import java.util.List;
import java.util.Map;

import cn.jrry.sys.domain.RolePermissionRelation;
import cn.jrry.sys.domain.RolePermissionRelation;

public interface RolePermissionRelationService {
    int deleteByPrimaryKey(Long id);

    int insert(RolePermissionRelation record);

    RolePermissionRelation selectByPrimaryKey(Long id);

    List<RolePermissionRelation> selectAll();

    int updateByPrimaryKey(RolePermissionRelation record);

    int countPermission(Map<String, Object> record);

    List<RolePermissionRelation> selectPermission(Map<String, Object> record);

    int insert(List<RolePermissionRelation> record);

    List<RolePermissionRelation> selectByRoleName(String roleName);

}
