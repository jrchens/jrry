package cn.jrry.sys.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.jrry.common.domain.LoginUser;
import cn.jrry.util.ExceptionUtils;

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private static final String CONTROLLER_CLASS_NAME = LoginController.class.getName();

    @RequestMapping(path = {"login"}, method = RequestMethod.GET)
    public String login(@RequestParam(required = false) String relogin, Model model) {
        logger.info("--> {}.{}", CONTROLLER_CLASS_NAME, "login");
        try {
            LoginUser user = new LoginUser();
            user.setUsername("sys");
            model.addAttribute(user);
            model.addAttribute("relogin", relogin);
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "login");
        return "login";
    }

    @RequestMapping(path = "login", method = RequestMethod.POST)
    public String login(@Validated LoginUser record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "login", record.toString());
        try {
            if (!bindingResult.hasErrors()) {
                UsernamePasswordToken token = new UsernamePasswordToken(record.getUsername(), record.getPassword());
                SecurityUtils.getSubject().login(token);
            } else {
                return "login";
            }
        } catch (Exception ex) {
            logger.error("", ex);
            if (ex instanceof UnknownAccountException) {
                return "redirect:/login?t=1";
//                model.addAttribute("controller_error", "username wasn't in the system");
            } else if (ex instanceof IncorrectCredentialsException) {
                return "redirect:/login?t=2";
//                model.addAttribute("controller_error", "password didn't match");
            } else if (ex instanceof LockedAccountException) {
                return "redirect:/login?t=3";
//                model.addAttribute("controller_error", "account for that username is locked");
            } else if (ex instanceof AuthenticationException) {
                return "redirect:/login?t=4";
//                model.addAttribute("controller_error", "unexpected condition");
            } else {
                return "redirect:/login?t=5";
//                model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
            }

        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "login");
        return "redirect:/index";
    }

    @RequestMapping(path = {"index", ""}, method = RequestMethod.GET)
    public String index(Model model) {
        logger.info("--> {}.{}", CONTROLLER_CLASS_NAME, "index");
        Subject subject = SecurityUtils.getSubject();
        model.addAttribute("username", subject.getPrincipal());
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "index");
        return "index";
    }


    @RequestMapping(path = "logout", method = RequestMethod.GET)
    public String logout(Model model) {
        logger.info("--> {}.{}", CONTROLLER_CLASS_NAME, "logout");

        SecurityUtils.getSubject().logout();

        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "logout");
        return "redirect:/login?relogin=1";
    }

    @RequestMapping(path = "unauthorized", method = RequestMethod.GET)
    public String unauthorized(Model model) {
        logger.info("--> {}.{}", CONTROLLER_CLASS_NAME, "unauthorized");

        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "unauthorized");
        return "/unauthorized";
    }


}
