package cn.jrry.mpms.service;

import java.util.List;
import java.util.Map;

import cn.jrry.mpms.domain.Category;
import cn.jrry.common.domain.Node;
import cn.jrry.mpms.domain.Category;

public interface CategoryService {
    int deleteByPrimaryKey(Long id);

    int insert(Category record);

    Category selectByPrimaryKey(Long id);

    List<Category> selectAll();

    int updateByPrimaryKey(Category record);

    int removeByPrimaryKey(Category record);

    int count(Map<String, Object> record);

    List<Category> select(Map<String, Object> record);

    List<Category> selectEnabledByParentId(Long parentId);
    
    List<Node> selectAsTree(Long parentId);
}
