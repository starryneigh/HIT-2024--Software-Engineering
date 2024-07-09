package com.example.reserve.controller;

import com.example.reserve.entity.Clazz;
import com.example.reserve.service.ClazzService;
import com.example.reserve.service.StudentService;
import com.example.reserve.utils.R;
import com.example.reserve.utils.Data;
import com.example.reserve.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/clazz")
public class ClazzController {

  @Autowired
  private ClazzService clazzService;

  @GetMapping("/clazz_list")
  public String clazzList() {
    return "clazz/clazzList";
  }

  @PostMapping("/getClazzList")
  @ResponseBody
  public Object getClazzList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "rows", defaultValue = "100") Integer rows, String clazzName, String from) {
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("pageno", page);
    paramMap.put("pagesize", rows);
    if (!StringUtils.isEmpty(clazzName)) {
      paramMap.put("name", clazzName);
    }
    PageBean<Clazz> pageBean = clazzService.queryPage(paramMap);
    if (!StringUtils.isEmpty(from) && "combox".equals(from)) {
      return pageBean.getDatas();
    } else {
      Map<String, Object> result = new HashMap<>();
      result.put("total", pageBean.getTotalsize());
      result.put("rows", pageBean.getDatas());
      return result;
    }
  }

  @PostMapping("/addClazz")
  @ResponseBody
  public R<Boolean> addClazz(Clazz clazz) {
    int count = clazzService.add(clazz);
    if (count > 0) {
      return R.success();
    } else {
      return R.fail();
    }
  }

  @PostMapping("/deleteClazz")
  @ResponseBody
  public R<Boolean> deleteClazz(Data data) {
    List<Integer> ids = data.getIds();
    System.out.println(ids);
    int count = clazzService.deleteClazz(ids);
    if (count > 0) {
      return R.success();
    } else if (count == -1) {
      return R.fail("无法删除，专业下存在学生");
    } else if (count == -2) {
      return R.fail("无法删除，专业下存在教师");
    } else {
      return R.fail();
    }
  }

  @PostMapping("/editClazz")
  @ResponseBody
  public R<Boolean> editClazz(Clazz clazz) {
    int count = clazzService.editClazz(clazz);
    if (count > 0) {
      return R.success();
    } else {
      return R.fail();
    }
  }
}
