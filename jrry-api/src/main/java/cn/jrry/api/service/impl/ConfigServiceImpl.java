package cn.jrry.api.service.impl;

import cn.jrry.api.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Service
public class ConfigServiceImpl implements ConfigService {

    private String condition(Map<String, String> params){
        return null;
    }

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int count(Map<String, String> params) {
        Object[] args = new Object[]{};
        return jdbcTemplate.queryForObject("select count(1) from sys_config",args,Integer.class).intValue();
    }

    @Override
    public List<Map<String, Object>> query(Map<String, String> params) {
        int page = Integer.parseInt(params.get("page"));
        int start = Integer.parseInt(params.get("start"));
        int limit = Integer.parseInt(params.get("limit"));
        // page=1&start=0&limit=2
        // page=1&start=0&limit=2
        return jdbcTemplate.queryForList("select config_label as username,config_name as viewname,config_value as phone from sys_config order by id asc limit ?,?",start,limit);
    }
}
