package com.example.reserve.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.reserve.entity.Course;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CourseMapper extends BaseMapper<Course> {
  List<Course> queryList(Map<String, Object> paramMap);

  Integer queryCount(Map<String, Object> paramMap);

  List<Course> getCourseById(List<Integer> ids);

  List<Course> getCoursesByTeacherId(Integer id);
}
