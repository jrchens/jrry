package cn.jrry.admin.controller;

import cn.jrry.admin.domain.LoginUser;
import cn.jrry.admin.service.VersionService;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class LoginController {

  private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
  
  @Autowired
  private VersionService versionService;
  
  @GetMapping(value="login")
  public String login() {
    logger.info("--> LoginController.login");
    String forward = "login";
    try {
      
    } catch (Exception e) {
      
    }
    logger.info("<-- LoginController.login");
    return forward;
  }
  
  @PostMapping(value="login")
  public String login(LoginUser loginUser) {
    logger.info("--> LoginController.login: {}",loginUser);
    String forward = "index";
    try {
      
    } catch (Exception e) {
      forward = "login";
    }
    logger.info("<-- LoginController.login");
    return forward;
  }
  
  @GetMapping(value="logout")
  public String logout() {
    logger.info("--> LoginController.logout");
    String forward = "login";
    try {
      
    } catch (Exception e) {
    }
    logger.info("<-- LoginController.logout");
    return forward;
  }

  @GetMapping(value="docs")
  @ResponseBody
  public Map<String,Object> docs() {
    logger.info("--> LoginController.docs");
    Map<String,Object> result = Maps.newHashMap();
    try {
      result.put("version", versionService.getVersion());
    } catch (Exception e) {
      result.put("errcode", 1000);
    }
    logger.info("<-- LoginController.docs");
    return result;
  }
  
  @GetMapping(value="version")
  @ResponseBody
  public Map<String,Object> version(String version) {
    logger.info("--> LoginController.docs");
    Map<String,Object> result = Maps.newHashMap();
    try {
      result.put("version", versionService.updateVersion(version));
    } catch (Exception e) {
      result.put("errcode", 1000);
    }
    logger.info("<-- LoginController.docs");
    return result;
  }
}
