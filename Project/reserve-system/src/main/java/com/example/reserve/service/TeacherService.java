package com.example.reserve.service;

import com.example.reserve.dto.LoginDTO;
import com.example.reserve.entity.Teacher;
import com.example.reserve.utils.PageBean;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface TeacherService {
  PageBean<Teacher> queryPage(Map<String, Object> paramMap);

  int deleteTeacher(List<Integer> ids);

  int addTeacher(Teacher teacher);

  int editTeacher(Teacher teacher);

  Teacher login(LoginDTO loginDTO);

  int editPswdByTeacher(Teacher teacher);

  List<Teacher> getTeachersByIds(Set<Integer> teacherIds);
}
