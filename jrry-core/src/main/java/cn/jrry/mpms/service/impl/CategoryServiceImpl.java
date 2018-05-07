package cn.jrry.mpms.service.impl;

import cn.jrry.common.domain.Node;
import cn.jrry.common.exception.ServiceException;
import cn.jrry.mpms.domain.Category;
import cn.jrry.mpms.mapper.CategoryMapper;
import cn.jrry.mpms.service.CategoryService;
import cn.jrry.sys.service.GroupService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(GroupService.class);

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        try {
            return categoryMapper.deleteByPrimaryKey(id);
        } catch (Exception ex) {
            logger.error("deleteByPrimaryKey error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int insert(Category record) {
        try {
            String user = SecurityUtils.getSubject().getPrincipal().toString();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setCruser(user);
            record.setCrtime(now);
            record.setCode(UUID.randomUUID().toString());

            int aff = categoryMapper.insert(record);

            return aff;
        } catch (Exception ex) {
            logger.error("insert error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public Category selectByPrimaryKey(Long id) {
        try {
            return categoryMapper.selectByPrimaryKey(id);
        } catch (Exception ex) {
            logger.error("selectByPrimaryKey error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<Category> selectAll() {
        try {
            return categoryMapper.selectAll();
        } catch (Exception ex) {
            logger.error("selectAll error {}", ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int updateByPrimaryKey(Category record) {
        try {
            String user = SecurityUtils.getSubject().getPrincipal().toString();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setMduser(user);
            record.setMdtime(now);
            return categoryMapper.updateByPrimaryKey(record);
        } catch (Exception ex) {
            logger.error("updateByPrimaryKey error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int removeByPrimaryKey(Category record) {
        try {
            String user = SecurityUtils.getSubject().getPrincipal().toString();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setMduser(user);
            record.setMdtime(now);
            return categoryMapper.removeByPrimaryKey(record);
        } catch (Exception ex) {
            logger.error("removeByPrimaryKey error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int count(Map<String, Object> record) {
        try {
            return categoryMapper.count(record);
        } catch (Exception ex) {
            logger.error("count error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<Category> select(Map<String, Object> record) {
        try {
            int page = Integer.parseInt(ObjectUtils.getDisplayString(record.get("page")));
            int rows = Integer.parseInt(ObjectUtils.getDisplayString(record.get("rows")));
            record.put("offset", (page - 1) * rows);
            return categoryMapper.select(record);

        } catch (Exception ex) {
            logger.error("select error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<Category> selectEnabledByParentId(Long parentId) {
        try {
            Map<String, Object> record = Maps.newHashMap();
            record.put("offset", 0);
            record.put("rows", 100);
            record.put("parentId", parentId);
            record.put("disabled", false);
            record.put("sort", "srt");
            record.put("order", "asc");
            return categoryMapper.selectEnabledByParentId(parentId);

        } catch (Exception ex) {
            logger.error("selectEnabledByParentId error {}{}{}", parentId, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    public List<Category> selectByParentId(Long parentId) {
        try {
            return categoryMapper.selectByParentId(parentId);
        } catch (Exception ex) {
            logger.error("selectEnabledByParentId error {}{}{}", parentId, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    private void getChildren(Node node) {
        List<Node> nodes = node.getChildren();
        if (nodes.isEmpty()) {
            List<Category> categorys = selectByParentId(node.getId());
            if(categorys.isEmpty()){
                return;
            }
            for (Category category : categorys) {
                Node n = new Node();
                n.setId(category.getId());
                n.setText(category.getName());
                
                Map<String,Object> attributes = Maps.newHashMap();
                attributes.put("code",category.getCode());
                attributes.put("fullName",category.getFullName());
                attributes.put("level",category.getLevel());
                attributes.put("srt",category.getSrt());
                attributes.put("viewable",category.getViewable());
                attributes.put("disabled",category.getDisabled());
                n.setAttributes(attributes);
                
                nodes.add(n);
            }
            node.setChildren(nodes);
            getChildren(node);
        } else {
            for (Node n : nodes) {
                getChildren(n);
            }
        }
    }

    private Node getRoot() {
        Category category = categoryMapper.selectRoot();
        Node node = new Node();
        node.setId(category.getId());
        node.setText(category.getName());
        Map<String,Object> attributes = Maps.newHashMap();
        attributes.put("code",category.getCode());
        attributes.put("fullName",category.getFullName());
        attributes.put("level",category.getLevel());
        attributes.put("srt",category.getSrt());
        attributes.put("viewable",category.getViewable());
        attributes.put("disabled",category.getDisabled());
        node.setAttributes(attributes);
        return node;
    }
    

    private Node getByParentId(Long parentId) {
        Category category = categoryMapper.selectByPrimaryKey(parentId);
        Node node = new Node();
        node.setId(category.getId());
        node.setText(category.getName());
        Map<String,Object> attributes = Maps.newHashMap();
        attributes.put("code",category.getCode());
        attributes.put("fullName",category.getFullName());
        attributes.put("level",category.getLevel());
        attributes.put("srt",category.getSrt());
        attributes.put("viewable",category.getViewable());
        attributes.put("disabled",category.getDisabled());
        node.setAttributes(attributes);
        return node;
    }

    @Override
    public List<Node> selectAsTree(Long parentId) {
        try {

            Node node = null;
            if (parentId == 0l) {
                node = getRoot();
            } else {
                node = getByParentId(parentId);
            }
            getChildren(node);
            return parentId == 0l ? Lists.newArrayList(node) : node.getChildren();
        } catch (Exception ex) {
            logger.error("selectAsTree error {}{}", System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }
}
