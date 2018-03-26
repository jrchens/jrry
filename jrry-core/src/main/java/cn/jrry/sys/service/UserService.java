package cn.jrry.sys.service;

import cn.jrry.sys.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    User selectByPrimaryKey(Long id);

    User selectByUsername(String username);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    int removeByPrimaryKey(User record);

    int count(Map<String, Object> record);

    List<User> select(Map<String, Object> record);
}
