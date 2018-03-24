package cn.jrry.sys.service;

import java.util.List;
import java.util.Map;

import cn.jrry.sys.domain.UserRoleRelation;

public interface UserRoleRelationService {
    int deleteByPrimaryKey(Long id);

    int insert(UserRoleRelation record);
    int insert(List<UserRoleRelation> records);

    UserRoleRelation selectByPrimaryKey(Long id);

    List<UserRoleRelation> selectAll();

    int updateByPrimaryKey(UserRoleRelation record);

    int countRole(Map<String,Object> record);
    List<UserRoleRelation> selectRole(Map<String,Object> record);
    int countUser(Map<String,Object> record);
    List<UserRoleRelation> selectUser(Map<String,Object> record);

    List<UserRoleRelation> selectRoleByUsername(String username);
    int updateDefByPrimaryKey(Long id);
}
