package cn.jrry.sys.service;

import java.util.List;
import java.util.Map;

import cn.jrry.sys.domain.UserGroupRelation;

public interface UserGroupRelationService {
    int deleteByPrimaryKey(Long id);

    int insert(UserGroupRelation record);
    int insert(List<UserGroupRelation> records);

    UserGroupRelation selectByPrimaryKey(Long id);

    List<UserGroupRelation> selectAll();

    int updateByPrimaryKey(UserGroupRelation record);

    int countUser(Map<String,Object> record);
    List<UserGroupRelation> selectUser(Map<String,Object> record);
    
    int countGroup(Map<String,Object> record);
    List<UserGroupRelation> selectGroup(Map<String,Object> record);

    List<UserGroupRelation> selectGroupByUsername(String username);

    int updateDefByPrimaryKey(Long id);
}
