package cn.jrry.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public class ConfigLoader implements ServletContextAware  {

    private static final Logger logger = LoggerFactory.getLogger(ConfigLoader.class);
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    @Override
    public void setServletContext(ServletContext servletContext) {
        List<Map<String,Object>> configs = jdbcTemplate.queryForList("SELECT cfg_code,cfg_value FROM sys_config WHERE cfg_type = 1");
        for (Map<String, Object> config : configs) {
            servletContext.setAttribute(config.get("cfg_code").toString(), config.get("cfg_value"));
        }
        logger.info("init loader sys_config size:{}",configs.size());
    }

//    static class KeyValuePair implements java.io.Serializable {
//        private String key;
//        private String value;
//        public String getKey() {
//            return key;
//        }
//        public void setKey(String key) {
//            this.key = key;
//        }
//        public String getValue() {
//            return value;
//        }
//        public void setValue(String value) {
//            this.value = value;
//        }
//        @Override
//        public int hashCode() {
//            final int prime = 31;
//            int result = 1;
//            result = prime * result + ((key == null) ? 0 : key.hashCode());
//            result = prime * result + ((value == null) ? 0 : value.hashCode());
//            return result;
//        }
//        @Override
//        public boolean equals(Object obj) {
//            if (this == obj)
//                return true;
//            if (obj == null)
//                return false;
//            if (getClass() != obj.getClass())
//                return false;
//            KeyValuePair other = (KeyValuePair) obj;
//            if (key == null) {
//                if (other.key != null)
//                    return false;
//            } else if (!key.equals(other.key))
//                return false;
//            if (value == null) {
//                if (other.value != null)
//                    return false;
//            } else if (!value.equals(other.value))
//                return false;
//            return true;
//        }
//        @Override
//        public String toString() {
//            return "KeyValuePair [key=" + key + ", value=" + value + "]";
//        }
//        
//    }
    
}
