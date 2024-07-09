package com.example.reserve.service;

import com.example.reserve.entity.Course;
import com.example.reserve.utils.PageBean;

import java.util.List;
import java.util.Map;

public interface CourseService {
  PageBean<Course> queryPage(Map<String, Object> paramMap);

  int addCourse(Course course);

  int editCourse(Course course);

  int deleteCourse(List<Integer> ids);

  List<Course> getCourseById(List<Integer> ids);
}
