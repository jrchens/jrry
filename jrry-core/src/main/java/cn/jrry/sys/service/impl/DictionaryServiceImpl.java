package cn.jrry.sys.service.impl;

import cn.jrry.common.exception.ServiceException;
import cn.jrry.sys.domain.Dictionary;
import cn.jrry.sys.mapper.DictionaryMapper;
import cn.jrry.sys.service.DictionaryService;
import com.google.common.collect.Lists;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
public class DictionaryServiceImpl implements DictionaryService {
    private static final Logger logger = LoggerFactory.getLogger(DictionaryService.class);

    @Autowired
    private DictionaryMapper dictionaryMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        try {
            return dictionaryMapper.deleteByPrimaryKey(id);
        } catch (Exception ex) {
            logger.error("deleteByPrimaryKey error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int insert(Dictionary record) {
        try {
            String user = SecurityUtils.getSubject().getPrincipal().toString();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setCruser(user);
            record.setCrtime(now);

            if(!StringUtils.hasText(record.getParentCode())){
                record.setParentCode(null);
            }

            int aff = dictionaryMapper.insert(record);

            return aff;
        } catch (Exception ex) {
            logger.error("insert error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public Dictionary selectByPrimaryKey(Long id) {
        try {
            return dictionaryMapper.selectByPrimaryKey(id);
        } catch (Exception ex) {
            logger.error("selectByPrimaryKey error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<Dictionary> selectAll() {
        try {
            return dictionaryMapper.selectAll();
        } catch (Exception ex) {
            logger.error("selectAll error {}", ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int updateByPrimaryKey(Dictionary record) {
        try {
            String user = SecurityUtils.getSubject().getPrincipal().toString();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setMduser(user);
            record.setMdtime(now);

            if(!StringUtils.hasText(record.getParentCode())){
                record.setParentCode(null);
            }
            
            return dictionaryMapper.updateByPrimaryKey(record);
        } catch (Exception ex) {
            logger.error("updateByPrimaryKey error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int removeByPrimaryKey(Dictionary record) {
        try {
            String user = SecurityUtils.getSubject().getPrincipal().toString();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setMduser(user);
            record.setMdtime(now);
            return dictionaryMapper.removeByPrimaryKey(record);
        } catch (Exception ex) {
            logger.error("removeByPrimaryKey error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int count(Map<String, Object> record) {
        try {
            return dictionaryMapper.count(record);
        } catch (Exception ex) {
            logger.error("count error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<Dictionary> select(Map<String, Object> record) {
        try {
            int page = Integer.parseInt(ObjectUtils.getDisplayString(record.get("page")));
            int rows = Integer.parseInt(ObjectUtils.getDisplayString(record.get("rows")));
            record.put("offset", (page - 1) * rows);
            return dictionaryMapper.select(record);

        } catch (Exception ex) {
            logger.error("select error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public Dictionary selectByCode(String code) {
        try {
            return dictionaryMapper.selectByCode(code);
        } catch (Exception ex) {
            logger.error("selectByCode error {}{}{}", code, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    @Cacheable(cacheManager = "cacheManager",cacheNames = "sys_dictionarys")
    public List<Dictionary> selectByParentCode(String parentCode) {
        try {
            return dictionaryMapper.selectByParentCode(parentCode);
        } catch (Exception ex) {
            logger.error("selectByParentCode error {}", ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    @Cacheable(cacheManager = "cacheManager",cacheNames = "sys_dictionarys")
    public List<Dictionary> selectByParentCode(String parentCode, boolean filterDisabled) {
        try {
            List<Dictionary> list = Lists.newArrayList();
            List<Dictionary> dictionarys = dictionaryMapper.selectByParentCode(parentCode);
            if(filterDisabled){
                for (Dictionary dictionary : dictionarys) {
                    if(!dictionary.getDisabled()){
                        list.add(dictionary);
                    }
                }
            }else{
                list.addAll(dictionarys);
            }
            return list;
        } catch (Exception ex) {
            logger.error("selectByParentCode error parentCode:{},filterDisabled{},ex:{}",parentCode,filterDisabled, ex);
            throw new ServiceException(ex.getCause());
        }
    }
    
    
}
