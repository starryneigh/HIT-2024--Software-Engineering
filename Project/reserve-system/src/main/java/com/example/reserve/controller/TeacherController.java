package com.example.reserve.controller;

import com.example.reserve.constants.UserConstant;
import com.example.reserve.entity.Teacher;
import com.example.reserve.service.TeacherService;
import com.example.reserve.utils.R;
import com.example.reserve.utils.Data;
import com.example.reserve.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

  @Autowired
  private TeacherService teacherService;

  @RequestMapping("/teacher_list")
  public String teacherList() {
    return "teacher/teacherList";
  }

  @PostMapping("/getTeacherList")
  @ResponseBody
  public Object getTeacherList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                               @RequestParam(value = "rows", defaultValue = "100") Integer rows,
                               String teacherName,
                               @RequestParam(value = "clazzid", defaultValue = "0") String clazzid, String from, HttpSession session) {
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("pageno", page);
    paramMap.put("pagesize", rows);
    if (!StringUtils.isEmpty(teacherName)) {
      paramMap.put("username", teacherName);
    }
    if (!"0".equals(clazzid)) {
      paramMap.put("clazzid", clazzid);
    }

    //判断是老师还是管理员权限
    Teacher teacher = (Teacher) session.getAttribute(UserConstant.TEACHER);
    if (!StringUtils.isEmpty(teacher)) {
      //是老师权限，只能查询自己的信息
      paramMap.put("teacherid", teacher.getId());
    }

    PageBean<Teacher> pageBean = teacherService.queryPage(paramMap);
    if (!StringUtils.isEmpty(from) && "combox".equals(from)) {
      return pageBean.getDatas();
    } else {
      Map<String, Object> result = new HashMap<>();
      result.put("total", pageBean.getTotalsize());
      result.put("rows", pageBean.getDatas());
      return result;
    }
  }

  @PostMapping("/deleteTeacher")
  @ResponseBody
  public R<Boolean> deleteTeacher(Data data) {
    int count = teacherService.deleteTeacher(data.getIds());
    if (count > 0) {
      return R.success();
    } else if (count == -1) {
      return R.fail("无法删除，教师下存在课程");
    } else if (count == -2) {
      return R.fail("无法删除，教师下存在空闲时间");
    } else {
      return R.fail();
    }
  }

  @RequestMapping("/addTeacher")
  @ResponseBody
  public R<Boolean> addTeacher(Teacher teacher) {
    int count = teacherService.addTeacher(teacher);
    if (count > 0) {
      return R.success();
    } else {
      return R.fail();
    }
  }

  @PostMapping("/editTeacher")
  @ResponseBody
  public R<Boolean> editTeacher(Teacher teacher) {
    int count = teacherService.editTeacher(teacher);
    System.out.println(count);
    if (count > 0) {
      return R.success();
    } else {
      return R.fail();
    }
  }
}
