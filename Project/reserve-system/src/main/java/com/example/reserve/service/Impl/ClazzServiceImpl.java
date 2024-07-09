package com.example.reserve.service.Impl;

import com.example.reserve.entity.Clazz;
import com.example.reserve.entity.Student;
import com.example.reserve.entity.Teacher;
import com.example.reserve.mapper.ClazzMapper;
import com.example.reserve.mapper.StudentMapper;
import com.example.reserve.mapper.TeacherMapper;
import com.example.reserve.service.ClazzService;
import com.example.reserve.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class ClazzServiceImpl implements ClazzService {

  @Autowired
  private ClazzMapper clazzMapper;
  @Autowired
  private StudentMapper studentMapper;
  @Autowired
  private TeacherMapper teacherMapper;

  @Override
  public PageBean<Clazz> queryPage(Map<String, Object> paramMap) {
    PageBean<Clazz> pageBean = new PageBean<>((Integer) paramMap.get("pageno"), (Integer) paramMap.get("pagesize"));

    Integer startIndex = pageBean.getStartIndex();
    paramMap.put("startIndex", startIndex);
    List<Clazz> datas = clazzMapper.queryList(paramMap);
    pageBean.setDatas(datas);

    Integer totalsize = clazzMapper.queryCount(paramMap);
    pageBean.setTotalsize(totalsize);
    return pageBean;
  }

  @Override
  @Transactional
  public int deleteClazz(List<Integer> ids) {
    for (Integer id : ids) {
      System.out.println("id = " + id);
      List<Student> studentList = studentMapper.isStudentByClazzId(id);
      List<Teacher> teacherList = teacherMapper.getTeacherByClazzId(id);
      System.out.println("studentMapper.isStudentByClazzId(id) = " + studentList);
      System.out.println("teacherMapper.getTeacherByClazzId(id) = " + teacherList);
      if (!studentList.isEmpty()) {
        System.out.println("专业下有学生，不能删除");
        return -1;
      }
      if (!teacherList.isEmpty()) {
        System.out.println("专业下有教师，不能删除");
        return -2;
      }
    }
    return clazzMapper.deleteClazz(ids);
  }

  @Override
  public int editClazz(Clazz clazz) {
    return clazzMapper.updateById(clazz);
  }

  @Override
  public int add(Clazz clazz) {
    return clazzMapper.insert(clazz);
  }

}
