package com.example.reserve.controller;

import com.example.reserve.constants.UserConstant;
import com.example.reserve.entity.Student;
import com.example.reserve.service.StudentService;
import com.example.reserve.utils.Data;
import com.example.reserve.utils.PageBean;
import com.example.reserve.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/student")
public class StudentController {

  @Autowired
  private StudentService studentService;

  @GetMapping("/student_list")
  public String studentList() {
    return "student/studentList";
  }

  @RequestMapping("/getStudentList")
  @ResponseBody
  public Object getStudentList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                               @RequestParam(value = "rows", defaultValue = "100") Integer rows,
                               String studentName,
                               @RequestParam(value = "clazzid", defaultValue = "0") String clazzid, String from, HttpSession session) {
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("pageno", page);
    paramMap.put("pagesize", rows);
    if (!StringUtils.isEmpty(studentName)) {
      paramMap.put("username", studentName);
    }
    if (!"0".equals(clazzid)) {
      paramMap.put("clazzid", clazzid);
    }

    //判断是老师还是学生权限
    Student student = (Student) session.getAttribute(UserConstant.STUDENT);
    if (!StringUtils.isEmpty(student)) {
      //是学生权限，只能查询自己的信息
      paramMap.put("studentid", student.getId());
    }

    PageBean<Student> pageBean = studentService.queryPage(paramMap);
    if (!StringUtils.isEmpty(from) && "combox".equals(from)) {
      return pageBean.getDatas();
    } else {
      Map<String, Object> result = new HashMap<>();
      result.put("total", pageBean.getTotalsize());
      result.put("rows", pageBean.getDatas());
      return result;
    }
  }

  @PostMapping("/deleteStudent")
  @ResponseBody
  public R<Boolean> deleteStudent(Data data) {
    List<Integer> ids = data.getIds();
    int count = studentService.deleteStudent(ids);
    if (count > 0) {
      return R.success();
    } else if (count == -1) {
      return R.fail("该学生下有预约，不能删除！");
    } else {
      return R.fail();
    }
  }

  @RequestMapping("/addStudent")
  @ResponseBody
  public R<Boolean> addStudent(Student student) {
    int count = studentService.addStudent(student);
    if (count > 0) {
      return R.success();
    } else {
      return R.fail();
    }
  }

  @PutMapping("/editStudent")
  @ResponseBody
  public R<Boolean> editStudent(Student student) {
    int i = studentService.updateById(student);
    if (i > 0) {
      return R.success();
    } else {
      return R.fail();
    }
  }
}
