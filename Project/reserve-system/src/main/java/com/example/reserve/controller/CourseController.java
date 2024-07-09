package com.example.reserve.controller;

import com.example.reserve.entity.Course;
import com.example.reserve.service.CourseService;
import com.example.reserve.utils.R;
import com.example.reserve.utils.Data;
import com.example.reserve.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/course")
public class CourseController {

  @Autowired
  private CourseService courseService;

  @GetMapping("/course_list")
  public String courseList() {
    return "course/courseList";
  }

  @PostMapping("/getCourseList")
  @ResponseBody
  public Object getClazzList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "rows", defaultValue = "100") Integer rows,
                             String name,
                             @RequestParam(value = "teacherid", defaultValue = "0") String teacherId, String from) {
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("pageno", page);
    paramMap.put("pagesize", rows);
    if (!StringUtils.isEmpty(name)) {
      paramMap.put("name", name);
    }
    if (!"0".equals(teacherId)) {
      paramMap.put("teacherId", teacherId);
    }
    PageBean<Course> pageBean = courseService.queryPage(paramMap);
    if (!StringUtils.isEmpty(from) && "combox".equals(from)) {
      return pageBean.getDatas();
    } else {
      Map<String, Object> result = new HashMap<>();
      result.put("total", pageBean.getTotalsize());
      result.put("rows", pageBean.getDatas());
      return result;
    }
  }

  @PostMapping("/addCourse")
  @ResponseBody
  public R<Boolean> addCourse(Course course) {
    int count = courseService.addCourse(course);
    if (count > 0) {
      return R.success();
    } else {
      return R.fail();
    }
  }

  @PostMapping("/editCourse")
  @ResponseBody
  public R<Boolean> editCourse(Course course) {
    int count = courseService.editCourse(course);
    if (count > 0) {
      return R.success();
    } else {
      return R.fail();
    }
  }

  @PostMapping("/deleteCourse")
  @ResponseBody
  public R<Boolean> deleteCourse(Data data) {
    int count = courseService.deleteCourse(data.getIds());
    if (count > 0) {
      return R.success();
    } else {
      return R.fail();
    }
  }
}
