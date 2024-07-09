package com.example.reserve.controller;

import com.example.reserve.constants.UserConstant;
import com.example.reserve.dto.LoginDTO;
import com.example.reserve.entity.Admin;
import com.example.reserve.entity.Student;
import com.example.reserve.entity.Teacher;
import com.example.reserve.service.AdminService;
import com.example.reserve.service.StudentService;
import com.example.reserve.service.TeacherService;
import com.example.reserve.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.Objects;

import static com.example.reserve.constants.UserConstant.*;

@Controller
@RequestMapping("/system")
public class SystemController {

  @Autowired
  private AdminService adminService;
  @Autowired
  private StudentService studentService;
  @Autowired
  private TeacherService teacherService;

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @GetMapping("/index")
  public String index() {
    return "system/index";
  }

  @GetMapping("/personalView")
  public String personalView() {
    return "system/personalView";
  }

  @PostMapping("/login")
  @ResponseBody
  public R<Boolean> login(LoginDTO loginDTO, HttpSession session) {

    // 空值判断
    if (StringUtils.isEmpty(loginDTO.getUsername()) || StringUtils.isEmpty(loginDTO.getPassword())) {
      return R.fail("用户名或密码不能为空");
    }

    // 用户类型校验
    switch (loginDTO.getType()) {
      case ADMIN_CODE: {
        Admin admin = adminService.login(loginDTO);
        if (Objects.isNull(admin)) {
          return R.fail("用户名或密码错误");
        }
        session.setAttribute(UserConstant.ADMIN, admin);
        session.setAttribute(UserConstant.USER_TYPE, ADMIN_CODE);
        break;
      }
      case STUDENT_CODE: {
        Student student = studentService.login(loginDTO);
        if (Objects.isNull(student)) {
          return R.fail("用户名或密码错误");
        }
        session.setAttribute(UserConstant.STUDENT, student);
        session.setAttribute(UserConstant.USER_TYPE, STUDENT_CODE);
        break;
      }
      case TEACHER_CODE: {
        Teacher teacher = teacherService.login(loginDTO);
        if (Objects.isNull(teacher)) {
          return R.fail("用户名或密码错误");
        }
        session.setAttribute(UserConstant.TEACHER, teacher);
        session.setAttribute(UserConstant.USER_TYPE, TEACHER_CODE);
        break;
      }
      default:
        return R.fail();
    }
    return R.success("登录成功");
  }

  @PostMapping("/editPassword")
  @Transactional(rollbackFor = Exception.class)
  @ResponseBody
  public R<Boolean> editPassword(String password, String newPassword, HttpSession session) {

    // 从 session 中获取用户类型
    String userType = (String) session.getAttribute(UserConstant.USER_TYPE);
    if (Objects.isNull(userType)) {
      return R.fail("用户类型获取失败");
    }

    if (ADMIN_CODE.equals(userType)) {
      // 从 session 中获取管理员信息
      Admin admin = (Admin) session.getAttribute(UserConstant.ADMIN);
      if (Objects.nonNull(admin) && !password.equals(admin.getPassword())) {
        return R.fail("原密码错误");
      }
      admin.setPassword(newPassword);
      int count = adminService.editPswdByAdmin(admin);
      if (count > 0) {
        return R.success();
      } else {
        return R.fail();
      }
    }

    if (STUDENT_CODE.equals(userType)) {
      // 从 session 中获取学生信息
      Student student = (Student) session.getAttribute(UserConstant.STUDENT);
      if (Objects.nonNull(student) && !password.equals(student.getPassword())) {
        return R.fail("原密码错误");
      }
      student.setPassword(newPassword);
      int count = studentService.editPswdByStudent(student);
      if (count > 0) {
        return R.success();
      } else {
        return R.fail();
      }
    }

    if (TEACHER_CODE.equals(userType)) {
      // 从 session 中获取教师信息
      Teacher teacher = (Teacher) session.getAttribute(UserConstant.TEACHER);
      if (Objects.nonNull(teacher) && !password.equals(teacher.getPassword())) {
        return R.fail("原密码错误");
      }
      teacher.setPassword(newPassword);
      int count = teacherService.editPswdByTeacher(teacher);
      if (count > 0) {
        return R.success();
      } else {
        return R.fail();
      }
    }
    return null;
  }

  @GetMapping("/logout")
  public String logout(HttpSession session) {
    session.invalidate();
    return "login";
  }
}
