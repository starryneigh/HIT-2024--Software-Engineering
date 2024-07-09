package com.example.reserve.service;

import com.example.reserve.dto.LoginDTO;
import com.example.reserve.entity.Student;
import com.example.reserve.utils.PageBean;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface StudentService {
  PageBean<Student> queryPage(Map<String, Object> paramMap);

  int deleteStudent(List<Integer> ids);

  int addStudent(Student student);

  Student login(LoginDTO loginDTO);

  int editPswdByStudent(Student student);

  int updateById(Student student);

  List<Student> getStudentsByIds(Set<Integer> studentIds);
}
