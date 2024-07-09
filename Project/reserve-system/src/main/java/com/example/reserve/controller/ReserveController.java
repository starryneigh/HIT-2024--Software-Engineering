package com.example.reserve.controller;

import com.example.reserve.constants.UserConstant;
import com.example.reserve.entity.Availability;
import com.example.reserve.entity.Reserve;
import com.example.reserve.entity.Student;
import com.example.reserve.entity.Teacher;
import com.example.reserve.service.AvailabilityService;
import com.example.reserve.service.ReserveService;
import com.example.reserve.service.StudentService;
import com.example.reserve.service.TeacherService;
import com.example.reserve.utils.Data;
import com.example.reserve.utils.PageBean;
import com.example.reserve.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/reserve")
public class ReserveController {
  @Autowired
  private ReserveService reserveService;
  @Autowired
  private AvailabilityService availabilityService;
  @Autowired
  private StudentService studentService;
  @Autowired
  private TeacherService teacherService;

  @RequestMapping("/reserve_list")
  public String reserveList() {
    return "reserve/reserveList";
  }

  @RequestMapping("/getReserveList")
  @ResponseBody
  public Object getReserveList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                               @RequestParam(value = "rows", defaultValue = "100") Integer rows,
                               @RequestParam(value = "studentid", defaultValue = "0") String studentid, String from, HttpSession session) {
    Map<String, Object> paramMap = new HashMap<>();
    System.out.println(page);
    System.out.println(rows);
    paramMap.put("pageno", page);
    paramMap.put("pagesize", rows);
    if (!"0".equals(studentid)) {
      paramMap.put("studentid", studentid);
    }

    //判断是老师还是学生权限
    Student student = (Student) session.getAttribute(UserConstant.STUDENT);
    if (!StringUtils.isEmpty(student)) {
      //是学生权限，只能查询自己的信息
      paramMap.put("studentid", student.getId());
    }
    //判断是否是教师
    Teacher teacher = (Teacher) session.getAttribute(UserConstant.TEACHER);
    if (!StringUtils.isEmpty(teacher)) {
      //是教师权限，只能查询自己的空闲时间
      List<Integer> availabilityIds = availabilityService.queryIdsByTeacherId(teacher.getId());
      paramMap.put("availabilityids", availabilityIds);
    }
    //List<Integer> availabilityIds = availabilityService.queryIdsByTeacherId(12);
    //paramMap.put("availabilityids", availabilityIds);
    //System.out.println(availabilityIds);

    PageBean<Reserve> pageBean = reserveService.queryPage(paramMap);
    List<Reserve> reserves = pageBean.getDatas();

    Set<Integer> availabilityIds = reserves.stream()
            .map(Reserve::getAvailabilityId)
            .collect(Collectors.toSet());
    List<Availability> availabilities = availabilityService.getAvailabilitiesByIds(availabilityIds);
    Map<Integer, Availability> availabilityMap = availabilities.stream()
            .collect(Collectors.toMap(Availability::getId, a -> a));

    Set<Integer> StudentIds = reserves.stream()
            .map(Reserve::getStudentId)
            .collect(Collectors.toSet());
    List<Student> students = studentService.getStudentsByIds(StudentIds);
    Map<Integer, Student> studentMap = students.stream()
            .collect(Collectors.toMap(Student::getId, s -> s));

    Set<Integer> TeacherIds = availabilities.stream()
            .map(Availability::getTeacherId)
            .collect(Collectors.toSet());
    List<Teacher> teachers = teacherService.getTeachersByIds(TeacherIds);
    Map<Integer, Teacher> teacherMap = teachers.stream()
            .collect(Collectors.toMap(Teacher::getId, t -> t));

    List<Map<String, Object>> combinedList = new ArrayList<>();
    for (Reserve reserve : reserves) {
      Map<String, Object> combinedData = new HashMap<>();
      combinedData.put("id", reserve.getId());
      combinedData.put("studentId", reserve.getStudentId());
      combinedData.put("availabilityId", reserve.getAvailabilityId());
      combinedData.put("reserveTime", reserve.getReservationTime());
      combinedData.put("status", reserve.getStatus());
      Availability associatedAvailability = availabilityMap.get(reserve.getAvailabilityId());
      Student associatedStudent = studentMap.get(reserve.getStudentId());
      if (associatedAvailability != null) {
        combinedData.put("teacherId", associatedAvailability.getTeacherId());
        combinedData.put("startTime", associatedAvailability.getStartTime());
        combinedData.put("endTime", associatedAvailability.getEndTime());
        Teacher associatedTeacher = teacherMap.get(associatedAvailability.getTeacherId());
        if (associatedTeacher != null) {
          combinedData.put("teacherName", associatedTeacher.getUsername());
        }
      }
      if (associatedStudent != null) {
        combinedData.put("studentName", associatedStudent.getNickName());
      }
      combinedList.add(combinedData);
    }

    if (!StringUtils.isEmpty(from) && "combox".equals(from)) {
      return combinedList;
    } else {
      Map<String, Object> result = new HashMap<>();
      result.put("total", pageBean.getTotalsize());
      result.put("rows", combinedList);
      return result;
    }
  }

  @PostMapping("/deleteReserve")
  @ResponseBody
  public R<Boolean> deleteReserve(Data data) {
    List<Integer> ids = data.getIds();
    System.out.println(ids);
    int count = reserveService.deleteReserve(ids);
    if (count > 0) {
      return R.success();
    } else {
      return R.fail();
    }
  }

  @RequestMapping("/addReserve")
  @ResponseBody
  public R<Boolean> addReserve(Reserve reserve) {
    reserve.setStatus("PENDING");
    reserve.setReservationTime(new Date());
    int count = reserveService.addReserve(reserve);
    if (count > 0) {
      return R.success();
    } else {
      return R.fail();
    }
  }

  @RequestMapping("/editReserve")
  @ResponseBody
  public R<Boolean> editReserve(Reserve reserve) {
    int count = reserveService.updateById(reserve);
    if (count > 0) {
      return R.success();
    } else {
      return R.fail();
    }
  }


  @PostMapping("/checkReserve")
  @ResponseBody
  public R<Boolean> checkReserve(Reserve reserve) {
    System.out.println(reserve);

    int count = reserveService.checkReserve(reserve);
    if (count > 0) {
      return R.success();
    } else {
      return R.fail();
    }
  }

//  @PostMapping("/getAvailabilityDetails")
//  @ResponseBody
//  public R<Map<String, Object>> getAvailabilityDetails(@RequestParam("id") Integer id) {
//    Set<Integer> ids = new HashSet<>();
//    ids.add(id);
//    List<Availability> availabilities = availabilityService.getAvailabilitiesByIds(ids);
//    if (availabilities != null && !availabilities.isEmpty()) {
//      Availability availability = availabilities.get(0);
//      Map<String, Object> result = new HashMap<>();
//      result.put("startTime", availability.getStartTime());
//      result.put("endTime", availability.getEndTime());
//      return R.success(result);
//    } else {
//      return R.fail("Invalid availability ID");
//    }
//  }
}