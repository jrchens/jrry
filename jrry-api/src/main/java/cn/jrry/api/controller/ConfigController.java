package cn.jrry.api.controller;

import cn.jrry.api.service.ConfigService;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ConfigController {


    private static final Logger logger = LoggerFactory.getLogger(ConfigController.class);

    @Autowired
    private ConfigService configService;

    @GetMapping(value = "configs")
    public Map<String, Object> query(@RequestParam  Map<String, String> params) {
        logger.info("--> ConfigController.query");
        Map<String, Object> result = Maps.newHashMap();
        result.put("success", true);
        result.put("message", "query configs success");
        try {
            Map<String, Object> data = Maps.newHashMap();
            data.put("total", configService.count(params));
            data.put("rows", configService.query(params));

            result.put("data", data);
        } catch (Exception e) {
            logger.error("query configs error", e);
            result.put("success", false);
            result.put("message", "query configs error");
        }
        logger.info("<-- ConfigController.query");
        return result;
    }
}
