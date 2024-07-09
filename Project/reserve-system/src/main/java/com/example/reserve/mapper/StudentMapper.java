package com.example.reserve.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.reserve.entity.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
  List<Student> queryList(Map<String, Object> paramMap);

  Integer queryCount(Map<String, Object> paramMap);

  List<Student> isStudentByClazzId(Integer id);

  List<Student> getStudentsByIds(Set<Integer> studentIds);
}
