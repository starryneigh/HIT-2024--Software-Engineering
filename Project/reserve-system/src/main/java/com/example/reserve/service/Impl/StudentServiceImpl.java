package com.example.reserve.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.reserve.dto.LoginDTO;
import com.example.reserve.entity.Reserve;
import com.example.reserve.entity.Student;
import com.example.reserve.mapper.ReserveMapper;
import com.example.reserve.mapper.StudentMapper;
import com.example.reserve.service.StudentService;
import com.example.reserve.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class StudentServiceImpl implements StudentService {

  @Autowired
  private StudentMapper studentMapper;
  @Autowired
  private ReserveMapper reserveMapper;

  @Override
  public PageBean<Student> queryPage(Map<String, Object> paramMap) {
    PageBean<Student> pageBean = new PageBean<>((Integer) paramMap.get("pageno"), (Integer) paramMap.get("pagesize"));

    Integer startIndex = pageBean.getStartIndex();
    paramMap.put("startIndex", startIndex);
    List<Student> datas = studentMapper.queryList(paramMap);
    pageBean.setDatas(datas);

    Integer totalsize = studentMapper.queryCount(paramMap);
    pageBean.setTotalsize(totalsize);
    return pageBean;
  }

  @Override
  @Transactional
  public int deleteStudent(List<Integer> ids) {
    for (Integer id : ids) {
      System.out.println("id = " + id);
      List<Reserve> reserveList = reserveMapper.getReservesByStudentId(id);
      System.out.println("reserveList = " + reserveList);
      if (!reserveList.isEmpty()) {
        System.out.println("该学生有预约记录，不能删除");
        return -1;
      }
    }
    return studentMapper.deleteBatchIds(ids);
  }

  @Override
  public int addStudent(Student student) {
    return studentMapper.insert(student);
  }

  @Override
  public Student login(LoginDTO loginDTO) {
    LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<Student>()
            .eq(Student::getUsername, loginDTO.getUsername())
            .eq(Student::getPassword, loginDTO.getPassword());
    return studentMapper.selectOne(wrapper);
  }

  @Override
  public int editPswdByStudent(Student student) {
    return studentMapper.updateById(student);
  }

  @Override
  public int updateById(Student student) {
    return studentMapper.updateById(student);
  }

  @Override
  public List<Student> getStudentsByIds(Set<Integer> studentIds) {
    return studentMapper.getStudentsByIds(studentIds);
  }
}
