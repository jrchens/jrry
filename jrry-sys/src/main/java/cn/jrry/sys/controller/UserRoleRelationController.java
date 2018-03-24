package cn.jrry.sys.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cn.jrry.sys.domain.User;
import cn.jrry.sys.domain.UserRoleRelation;
import cn.jrry.sys.service.RoleService;
import cn.jrry.sys.service.UserRoleRelationService;
import cn.jrry.sys.service.UserService;
import cn.jrry.util.ExceptionUtils;
import cn.jrry.validation.group.Get;
import cn.jrry.validation.group.Remove;

@Controller
@RequestMapping(path = "sys/user-role-relation")
public class UserRoleRelationController {
    private static final Logger logger = LoggerFactory.getLogger(UserRoleRelationController.class);
    private static final String CONTROLLER_CLASS_NAME = UserRoleRelationController.class.getName();

    @Autowired
    private UserRoleRelationService userRoleRelationService;

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

//    @RequestMapping(path = {"user/index"}, method = RequestMethod.GET)
//    public String index(@Validated Group record, BindingResult bindingResult, Model model) {
//        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "index", record);
//        try {
//            if (record == null) {
//                record = new Group();
//            }
//            model.addAttribute(record);
//        } catch (Exception ex) {
//            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
//        }
//        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "index");
//        return "sys/group/index";
//    }

    @RequestMapping(path = {"user/async-query"}, method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> query(@RequestParam(required = false) Map<String, Object> record) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "user/async-query", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        List<UserRoleRelation> rows = Lists.newArrayList();
        Map<String, Object> data = Maps.newLinkedHashMap();
        try {
            int total = userRoleRelationService.countRole(record);
            if (total > 0) {
                rows = userRoleRelationService.selectRole(record);
            }

            data.put("total", total);
            data.put("rows", rows);

            result.put("success", true);
            result.put("message", "");
            result.put("data", data);

        } catch (Exception ex) {
            data.put("total", 0);
            data.put("rows", rows);

            result.put("success", false);
            result.put("message", ExceptionUtils.getSimpleMessage(ex));
            result.put("data", data);
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "user/async-query");
        return result;
    }

    @RequestMapping(path = "user/create/{username}", method = RequestMethod.GET)
    public String create(@PathVariable(value = "username") String username, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "create", username);
        try {
//            List<Role> rows = Lists.newArrayList();
            User record = userService.selectByUsername(username);
//            List<Role> roleList = roleService.selectAll();
            List<UserRoleRelation> userRoleRelationList = userRoleRelationService.selectRoleByUsername(username);
            List<String> roleNames = Lists.newArrayList();
            for (UserRoleRelation urr:userRoleRelationList
                    ) {
                roleNames.add(urr.getRoleName());
            }

            model.addAttribute("roleNames", Joiner.on(",").join(roleNames));
            model.addAttribute(record);
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "create");
        return "sys/user/user_role_create";
    }

    @RequestMapping(path = "user/save/{username}", method = RequestMethod.POST)
    public String save(@PathVariable(value = "username") String username, @RequestParam(name = "roleNames") String roleNames, Model model) {
        logger.info("--> {}.{}({},{})", CONTROLLER_CLASS_NAME, "user/save", username, roleNames);
        User record = null;
        try {
            record = userService.selectByUsername(username);

            String[] roleNameArray = roleNames.split(",");
            List<UserRoleRelation> userRoleRelationList = Lists.newArrayList();
            for (String roleName : roleNameArray
                    ) {
                UserRoleRelation UserRoleRelation = new UserRoleRelation();
                UserRoleRelation.setUsername(username);
                UserRoleRelation.setRoleName(roleName);
                UserRoleRelation.setDef(Boolean.FALSE);

                userRoleRelationList.add(UserRoleRelation);
            }

            int aff = userRoleRelationService.insert(userRoleRelationList);

//            if (!bindingResult.hasErrors()) {
//                userRoleRelationService.insert(record);
//            } else {
//                return "sys/group/create";
//            }
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
            return "sys/user/user_role_create";
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "save");
        return "redirect:/sys/user/detail?id=" + record.getId();// detail(record, bindingResult, model);
    }

    @RequestMapping(path = "user/async-delete", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(@Validated(value = Remove.class) UserRoleRelation record, BindingResult bindingResult) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "async-remove", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        Map<String, Object> data = Maps.newLinkedHashMap();
        try {
            int aff = userRoleRelationService.deleteByPrimaryKey(record.getId());
            result.put("success", true);
            result.put("message", "");
            result.put("data", aff);
        } catch (Exception ex) {
            result.put("success", false);
            result.put("message", ExceptionUtils.getSimpleMessage(ex));
            result.put("data", 0);
        }

        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "async-remove");
        return result;
    }

    @RequestMapping(path = "user/async-update-def", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateDef(@Validated(value = Get.class) UserRoleRelation record, BindingResult bindingResult) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "async-remove", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        Map<String, Object> data = Maps.newLinkedHashMap();
        try {
            int aff = userRoleRelationService.updateDefByPrimaryKey(record.getId());
            result.put("success", true);
            result.put("message", "");
            result.put("data", aff);
        } catch (Exception ex) {
            result.put("success", false);
            result.put("message", ExceptionUtils.getSimpleMessage(ex));
            result.put("data", 0);
        }

        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "async-remove");
        return result;
    }



    @RequestMapping(path = {"user/async-user-query"}, method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryUser(@RequestParam(required = false) Map<String, Object> record) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "user/async-query", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        List<UserRoleRelation> rows = Lists.newArrayList();
        Map<String, Object> data = Maps.newLinkedHashMap();
        try {

            int total = userRoleRelationService.countUser(record);
            if (total > 0) {
                rows = userRoleRelationService.selectUser(record);
            }

            data.put("total", total);
            data.put("rows", rows);

            result.put("success", true);
            result.put("message", "");
            result.put("data", data);

        } catch (Exception ex) {
            data.put("total", 0);
            data.put("rows", rows);

            result.put("success", false);
            result.put("message", ExceptionUtils.getSimpleMessage(ex));
            result.put("data", data);
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "user/async-query");
        return result;
    }

//    @RequestMapping(path = "edit", method = RequestMethod.GET)
//    public String edit(@Validated(value = Edit.class) Group record, BindingResult bindingResult, Model model) {
//        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "edit", record);
//        try {
//            model.addAttribute(userRoleRelationService.selectByPrimaryKey(record.getId()));
//        } catch (Exception ex) {
//            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
//        }
//        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "edit");
//        return "sys/group/edit";
//    }
//
//    @RequestMapping(path = "update", method = RequestMethod.POST)
//    public String update(@Validated(value = Update.class) Group record, BindingResult bindingResult, Model model) {
//        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "update", record);
//        try {
//            if (!bindingResult.hasErrors()) {
//                userRoleRelationService.updateByPrimaryKey(record);
//            } else {
//                return "sys/group/edit";
//            }
//        } catch (Exception ex) {
//            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
//            return "sys/group/edit";
//        }
//        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "update");
//        return "redirect:detail?id=" + record.getId();// detail(record, bindingResult, model);
//    }
//
//    @RequestMapping(path = "detail", method = RequestMethod.GET)
//    public String detail(@Validated(value = Detail.class) Group record, BindingResult bindingResult, Model model) {
//        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "detail",record);
//        try {
//            model.addAttribute(userRoleRelationService.selectByPrimaryKey(record.getId()));
//        } catch (Exception ex) {
//            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
//        }
//        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "detail");
//        return "sys/group/detail";
//    }
//
//    @RequestMapping(path = "async-remove", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> remove(@Validated(value = Remove.class) Group record, BindingResult bindingResult) {
//        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "async-remove", record);
//        Map<String, Object> result = Maps.newLinkedHashMap();
//        Map<String, Object> data = Maps.newLinkedHashMap();
//        try {
//            int aff = userRoleRelationService.removeByPrimaryKey(record);
//            result.put("success", true);
//            result.put("message", "");
//            result.put("data", aff);
//        } catch (Exception ex) {
//            result.put("success", false);
//            result.put("message", ExceptionUtils.getSimpleMessage(ex));
//            result.put("data", 0);
//        }
//
//        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "async-remove");
//        return result;
//    }
}
