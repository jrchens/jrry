package cn.jrry.mpms.mapper;

import java.util.List;
import java.util.Map;

import cn.jrry.mpms.domain.Category;
import org.apache.ibatis.annotations.Param;

import cn.jrry.mpms.domain.Category;

public interface CategoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Category record);

    Category selectByPrimaryKey(Long id);

    List<Category> selectAll();

    int updateByPrimaryKey(Category record);
    
    int removeByPrimaryKey(Category record);

    int count(Map<String, Object> record);

    List<Category> select(Map<String, Object> record);

    List<Category> selectEnabledByParentId(@Param(value="parentId") Long parentId);
    List<Category> selectByParentId(@Param(value="parentId") Long parentId);
    Category selectRoot();
}