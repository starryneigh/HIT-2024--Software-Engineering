package com.example.reserve.interceptors;

import com.example.reserve.entity.Admin;
import com.example.reserve.entity.Student;
import com.example.reserve.entity.Teacher;
import com.example.reserve.constants.UserConstant;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class LoginInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

    Admin user = (Admin) request.getSession().getAttribute(UserConstant.ADMIN);
    Teacher teacher = (Teacher) request.getSession().getAttribute(UserConstant.TEACHER);
    Student student = (Student) request.getSession().getAttribute(UserConstant.STUDENT);

    if (Objects.nonNull(user) || Objects.nonNull(teacher) || Objects.nonNull(student)) {
      return true;
    } else {
      response.sendRedirect(request.getContextPath() + "/system/login");
      return false;
    }
    //return true;
  }

}
