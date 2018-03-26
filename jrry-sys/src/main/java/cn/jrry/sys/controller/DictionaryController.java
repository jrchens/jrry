package cn.jrry.sys.controller;

import cn.jrry.sys.domain.Dictionary;
import cn.jrry.sys.service.DictionaryService;
import cn.jrry.util.ExceptionUtils;
import cn.jrry.validation.group.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(path = "sys/dictionary")
public class DictionaryController {
    private static final Logger logger = LoggerFactory.getLogger(DictionaryController.class);
    private static final String CONTROLLER_CLASS_NAME = DictionaryController.class.getName();

    @Autowired
    private DictionaryService dictionaryService;

    @RequestMapping(path = {"index"}, method = RequestMethod.GET)
    public String index(@Validated Dictionary record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "index", record);
        try {
            model.addAttribute(record);
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "index");
        return "sys/dictionary/index";
    }

    @RequestMapping(path = {"async-query"}, method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> query(@RequestParam(required = false) Map<String, Object> record) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "async-query", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        List<Dictionary> rows = Lists.newArrayList();
        Map<String, Object> data = Maps.newLinkedHashMap();
        try {
            int total = dictionaryService.count(record);
            if (total > 0) {
                rows = dictionaryService.select(record);
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
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "async-query");
        return result;
    }

    @RequestMapping(path = "create", method = RequestMethod.GET)
    public String create(@Validated(value = Create.class) Dictionary record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "create", record);
        try {
            record.setDisabled(false);
            record.setSrt(1);
            if(StringUtils.hasText(record.getParentCode())){
                record.setCode(UUID.randomUUID().toString());
            }
            model.addAttribute(record);
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "create");
        return "sys/dictionary/create";
    }

    @RequestMapping(path = "save", method = RequestMethod.POST)
    public String save(@Validated(value = Save.class) Dictionary record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "save", record);
        Long id = null;
        try {
            if (!bindingResult.hasErrors()) {
                dictionaryService.insert(record);
                if(StringUtils.hasText(record.getParentCode())){
                    id = dictionaryService.selectByCode(record.getParentCode()).getId();
                }else{
                    id = record.getId();
                }
            } else {
                return "sys/dictionary/create";
            }
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
            return "sys/dictionary/create";
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "save");
        return "redirect:detail?id=" + id;// detail(record, bindingResult, model);
    }


    @RequestMapping(path = "edit", method = RequestMethod.GET)
    public String edit(@Validated(value = Edit.class) Dictionary record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "edit", record);
        try {
            model.addAttribute(dictionaryService.selectByPrimaryKey(record.getId()));
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "edit");
        return "sys/dictionary/edit";
    }

    @RequestMapping(path = "update", method = RequestMethod.POST)
    public String update(@Validated(value = Update.class) Dictionary record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "update", record);
        Long id = null;
        try {
            if (!bindingResult.hasErrors()) {
                dictionaryService.updateByPrimaryKey(record);
                if(StringUtils.hasText(record.getParentCode())){
                    id = dictionaryService.selectByCode(record.getParentCode()).getId();
                } else {
                    id = record.getId();
                }
            } else {
                return "sys/dictionary/edit";
            }
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
            return "sys/dictionary/edit";
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "update");
        return "redirect:detail?id=" + id;// detail(record, bindingResult, model);
    }

    @RequestMapping(path = "detail", method = RequestMethod.GET)
    public String detail(@Validated(value = Detail.class) Dictionary record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "detail", record);
        try {
            model.addAttribute(dictionaryService.selectByPrimaryKey(record.getId()));
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "detail");
        return "sys/dictionary/detail";
    }

    @RequestMapping(path = "async-remove", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> remove(@Validated(value = Remove.class) Dictionary record, BindingResult bindingResult) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "async-remove", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        try {
            int aff = dictionaryService.removeByPrimaryKey(record);
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
    
    @RequestMapping(path = {"back"}, method = RequestMethod.GET)
    public String back(@Validated Dictionary record, BindingResult bindingResult, Model model) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "index", record);
        try {
            if(StringUtils.hasText(record.getParentCode())){
                Long id = dictionaryService.selectByCode(record.getParentCode()).getId();
                return "redirect:detail?id=" + id;
            }
            model.addAttribute(record);
        } catch (Exception ex) {
            model.addAttribute("controller_error", ExceptionUtils.getSimpleMessage(ex));
        }
        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "index");
        return "sys/dictionary/index";
    }


    @RequestMapping(path = "async-get", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> asyncGet(@Validated(value = Query.class) Dictionary record, BindingResult bindingResult) {
        logger.info("--> {}.{}({})", CONTROLLER_CLASS_NAME, "async-get", record);
        Map<String, Object> result = Maps.newLinkedHashMap();
        try {
            result.put("success", true);
            result.put("message", "");
            result.put("data", dictionaryService.selectByParentCode(record.getParentCode()));
        } catch (Exception ex) {
            result.put("success", false);
            result.put("message", ExceptionUtils.getSimpleMessage(ex));
            result.put("data", 0);
        }

        logger.info("<-- {}.{}", CONTROLLER_CLASS_NAME, "async-get");
        return result;
    }
}
