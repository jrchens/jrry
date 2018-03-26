package cn.jrry.sys.service.impl;

import cn.jrry.common.exception.ServiceException;
import cn.jrry.sys.domain.UserRoleRelation;
import cn.jrry.sys.mapper.UserRoleRelationMapper;
import cn.jrry.sys.service.GroupService;
import cn.jrry.sys.service.UserRoleRelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

@Service
public class UserRoleRelationServiceImpl implements UserRoleRelationService {
    private static final Logger logger = LoggerFactory.getLogger(GroupService.class);

    @Autowired
    private UserRoleRelationMapper userRoleRelationMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        try {
            return userRoleRelationMapper.deleteByPrimaryKey(id);
        } catch (Exception ex) {
            logger.error("deleteByPrimaryKey error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int insert(UserRoleRelation record) {
        try {
//            String user = SecurityUtils.getSubject().getPrincipal().toString();
//            Timestamp now = new Timestamp(System.currentTimeMillis());
//            record.setCruser(user);
//            record.setCrtime(now);

            int aff = userRoleRelationMapper.insert(record);

            return aff;
        } catch (Exception ex) {
            logger.error("insert error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int insert(List<UserRoleRelation> records) {
        try {
//            String user = SecurityUtils.getSubject().getPrincipal().toString();
//            Timestamp now = new Timestamp(System.currentTimeMillis());
//            record.setCruser(user);
//            record.setCrtime(now);
            int aff = 0;
            for (UserRoleRelation userRoleRelation : records
                    ) {
                aff += userRoleRelationMapper.insert(userRoleRelation);
            }


            return aff;
        } catch (Exception ex) {
            logger.error("insert error {}{}{}", records, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public UserRoleRelation selectByPrimaryKey(Long id) {
        try {
            return userRoleRelationMapper.selectByPrimaryKey(id);
        } catch (Exception ex) {
            logger.error("selectByPrimaryKey error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<UserRoleRelation> selectAll() {
        try {
            return userRoleRelationMapper.selectAll();
        } catch (Exception ex) {
            logger.error("selectAll error {}", ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int updateByPrimaryKey(UserRoleRelation record) {
        try {
//            String user = SecurityUtils.getSubject().getPrincipal().toString();
//            Timestamp now = new Timestamp(System.currentTimeMillis());
//            record.setMduser(user);
//            record.setMdtime(now);
            return userRoleRelationMapper.updateByPrimaryKey(record);
        } catch (Exception ex) {
            logger.error("updateByPrimaryKey error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<UserRoleRelation> selectRoleByUsername(String username) {
        try {
            return userRoleRelationMapper.selectRoleByUsername(username);
        } catch (Exception ex) {
            logger.error("selectUserByRolename error {}", ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int countRole(Map<String,Object> record) {
        try {
            return userRoleRelationMapper.countRole(record);
        } catch (Exception ex) {
            logger.error("countRole error {}", ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<UserRoleRelation> selectRole(Map<String,Object> record) {
        try {
            int page = Integer.parseInt(ObjectUtils.getDisplayString(record.get("page")));
            int rows = Integer.parseInt(ObjectUtils.getDisplayString(record.get("rows")));
            record.put("offset", (page - 1) * rows);
            return userRoleRelationMapper.selectRole(record);
        } catch (Exception ex) {
            logger.error("selectRole error {}", ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int countUser(Map<String,Object> record) {
        try {
            return userRoleRelationMapper.countUser(record);
        } catch (Exception ex) {
            logger.error("countUser error {}", ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<UserRoleRelation> selectUser(Map<String,Object> record) {
        try {
            int page = Integer.parseInt(ObjectUtils.getDisplayString(record.get("page")));
            int rows = Integer.parseInt(ObjectUtils.getDisplayString(record.get("rows")));
            record.put("offset", (page - 1) * rows);
            return userRoleRelationMapper.selectUser(record);
        } catch (Exception ex) {
            logger.error("selectUser error {}", ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int updateDefByPrimaryKey(Long id) {
        try {
//            String user = SecurityUtils.getSubject().getPrincipal().toString();
//            Timestamp now = new Timestamp(System.currentTimeMillis());
//            record.setMduser(user);
//            record.setMdtime(now);

            UserRoleRelation userRoleRelation = selectByPrimaryKey(id);
            int aff = userRoleRelationMapper.updateDefByUsername(userRoleRelation.getUsername());
            aff = userRoleRelationMapper.updateDefByPrimaryKey(id);

            return aff;
        } catch (Exception ex) {
            logger.error("updateByPrimaryKey error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }



}
