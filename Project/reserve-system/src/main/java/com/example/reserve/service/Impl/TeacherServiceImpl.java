package com.example.reserve.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.reserve.dto.LoginDTO;
import com.example.reserve.entity.Availability;
import com.example.reserve.entity.Course;
import com.example.reserve.entity.Teacher;
import com.example.reserve.mapper.AvailabilityMapper;
import com.example.reserve.mapper.CourseMapper;
import com.example.reserve.mapper.TeacherMapper;
import com.example.reserve.service.TeacherService;
import com.example.reserve.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class TeacherServiceImpl implements TeacherService {

  @Autowired
  private TeacherMapper teacherMapper;
  @Autowired
  private CourseMapper courseMapper;
  @Autowired
  private AvailabilityMapper availabilityMapper;

  @Override
  public PageBean<Teacher> queryPage(Map<String, Object> paramMap) {
    PageBean<Teacher> pageBean = new PageBean<>((Integer) paramMap.get("pageno"), (Integer) paramMap.get("pagesize"));

    Integer startIndex = pageBean.getStartIndex();
    paramMap.put("startIndex", startIndex);
    List<Teacher> datas = teacherMapper.queryList(paramMap);
    pageBean.setDatas(datas);

    Integer totalsize = teacherMapper.queryCount(paramMap);
    pageBean.setTotalsize(totalsize);
    return pageBean;
  }

  @Override
  @Transactional
  public int deleteTeacher(List<Integer> ids) {
    for (Integer id : ids) {
      System.out.println("id = " + id);
      List<Course> courseList = courseMapper.getCoursesByTeacherId(id);
      List<Availability> availabilityList = availabilityMapper.getAvailabilitiesByTeacherId(id);
      System.out.println("courseMapper.getCoursesByTeacherIds(id) = " + courseList);
      System.out.println("availabilityMapper.getAvailabilitiesByTeacherIds(id) = " + availabilityList);
      if (!courseList.isEmpty()) {
        System.out.println("教师下有课程，不能删除");
        return -1;
      }
      if (!availabilityList.isEmpty()) {
        System.out.println("教师下有可用时间，不能删除");
        return -2;
      }
    }
    return teacherMapper.deleteBatchIds(ids);
  }

  @Override
  public int addTeacher(Teacher teacher) {
    return teacherMapper.insert(teacher);
  }

  @Override
  public int editTeacher(Teacher teacher) {
    return teacherMapper.updateById(teacher);
  }

  @Override
  public Teacher login(LoginDTO loginDTO) {
    LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<Teacher>()
            .eq(Teacher::getUsername, loginDTO.getUsername())
            .eq(Teacher::getPassword, loginDTO.getPassword());
    return teacherMapper.selectOne(wrapper);
  }

  @Override
  public int editPswdByTeacher(Teacher teacher) {
    return teacherMapper.updateById(teacher);
  }

  @Override
  public List<Teacher> getTeachersByIds(Set<Integer> teacherIds) {
    return teacherMapper.getTeachersByIds(teacherIds);
  }
}
