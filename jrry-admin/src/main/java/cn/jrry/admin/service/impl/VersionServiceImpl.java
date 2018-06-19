package cn.jrry.admin.service.impl;

import javax.sql.DataSource;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cn.jrry.admin.service.VersionService;

@Service
public class VersionServiceImpl implements VersionService {
  
  private static final Logger logger = LoggerFactory.getLogger(VersionServiceImpl.class);
  
  private JdbcTemplate jdbcTemplate;
  
  @Autowired
  public void setDataSource(DataSource dataSource){
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  @Override
  public String getVersion() {
    logger.info("--> VersionServiceImpl.getVersion");
    String result = null;
    try {
      result = jdbcTemplate.queryForObject("select current_version from sys_version order by id desc limit 1", String.class);
    } catch (Exception e) {
      logger.error("VersionServiceImpl.getVersion.error",e);
      result = "0.0.0";
    }
    logger.info("<-- VersionServiceImpl.getVersion");
    return result;
  }

  @Override
  public int updateVersion(String version) {
    logger.info("--> VersionServiceImpl.updateVersion");
    int result = 0;
    try {
      result = jdbcTemplate.update("insert into sys_version (current_version,crtime) values (?,?) ", version,DateTime.now().toDate());
      if(result == 1){
        result = 0;
      }
    } catch (Exception e) {
      logger.error("VersionServiceImpl.updateVersion.error",e);
      result = 1000;
    }
    logger.info("<-- VersionServiceImpl.updateVersion");
    return result;
  }

}
