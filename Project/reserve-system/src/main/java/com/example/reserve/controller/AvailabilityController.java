package com.example.reserve.controller;

import com.example.reserve.constants.UserConstant;
import com.example.reserve.entity.Availability;
import com.example.reserve.entity.Teacher;
import com.example.reserve.service.AvailabilityService;
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

@RequestMapping("/availability")
@Controller
public class AvailabilityController {

  @Autowired
  private AvailabilityService availabilityService;
  @Autowired
  private TeacherService teacherService;

  @RequestMapping("/availability_list")
  public String availabilityList() {
    return "availability/availabilityList";
  }

  @RequestMapping("/getAvailabilityList")
  @ResponseBody
  public Object getAvailabilityList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                    @RequestParam(value = "rows", defaultValue = "100") Integer rows,
                                    @RequestParam(value = "teacherid", defaultValue = "0") String teacherid,
                                    String from, HttpSession session) {
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("pageno", page);
    paramMap.put("pagesize", rows);
    if (!"0".equals(teacherid)) {
      paramMap.put("teacherid", teacherid);
    }

    // 判断是否是教师
    Teacher teacher = (Teacher) session.getAttribute(UserConstant.TEACHER);
    if (!StringUtils.isEmpty(teacher)) {
      // 是教师权限，只能查询自己的信息
      paramMap.put("teacherid", teacher.getId());
    }

    PageBean<Availability> pageBean = availabilityService.queryPage(paramMap);
    List<Availability> availabilities = pageBean.getDatas();

    // Fetch all teacher IDs from the availabilities
    Set<Integer> teacherIds = availabilities.stream()
            .map(Availability::getTeacherId)
            .collect(Collectors.toSet());

    // Fetch all teacher details
    List<Teacher> teachers = teacherService.getTeachersByIds(teacherIds);
    Map<Integer, Teacher> teacherMap = teachers.stream()
            .collect(Collectors.toMap(Teacher::getId, t -> t));

    // Combine availability and teacher data
    List<Map<String, Object>> combinedList = new ArrayList<>();
    for (Availability availability : availabilities) {
      Map<String, Object> combinedData = new HashMap<>();
      combinedData.put("id", availability.getId());
      combinedData.put("teacherId", availability.getTeacherId());
      combinedData.put("startTime", availability.getStartTime());
      combinedData.put("endTime", availability.getEndTime());

      Teacher associatedTeacher = teacherMap.get(availability.getTeacherId());
      if (associatedTeacher != null) {
        combinedData.put("teacherName", associatedTeacher.getUsername());
        combinedData.put("teacherSex", associatedTeacher.getSex());
        combinedData.put("teacherPhone", associatedTeacher.getMobile());
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

  @PostMapping("/deleteAvailability")
  @ResponseBody
  public R<Boolean> deleteAvailability(Data data) {
    List<Integer> ids = data.getIds();
    int count = availabilityService.deleteAvailability(ids);
    if (count > 0) {
      return R.success();
    } else if (count == -1) {
      return R.fail("该空闲时间段下有预约，不能删除");
    } else {
      return R.fail();
    }
  }

  @RequestMapping("/addAvailability")
  @ResponseBody
  public R<Boolean> addAvailability(@RequestParam("teacherId") Integer teacherId,
                                    @RequestParam("startTime") String startTimeStr,
                                    @RequestParam("endTime") String endTimeStr) {
    Availability availability = new Availability();
    availability.setTeacherId(teacherId);
    if (startTimeStr != null && endTimeStr != null) {
      try {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = input.parse(startTimeStr);
        Date endTime = input.parse(endTimeStr);
        availability.setStartTime(startTime);
        availability.setEndTime(endTime);
      } catch (Exception e) {
        return R.fail("Invalid start time format");
      }
    }
    int count = availabilityService.addAvailability(availability);
    if (count > 0) {
      return R.success();
    } else {
      return R.fail();
    }
  }

  @PutMapping("/editAvailability")
  @ResponseBody
  public R<Boolean> editAvailability(@RequestParam("id") Integer id,
                                     @RequestParam("teacherId") Integer teacherId,
                                     @RequestParam("startTime") String startTimeStr,
                                     @RequestParam("endTime") String endTimeStr) {
    Availability availability = new Availability();
    availability.setTeacherId(teacherId);
    availability.setId(id);
    if (startTimeStr != null && endTimeStr != null) {
      try {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = input.parse(startTimeStr);
        Date endTime = input.parse(endTimeStr);
        availability.setStartTime(startTime);
        availability.setEndTime(endTime);
      } catch (Exception e) {
        return R.fail("Invalid start time format");
      }
    }
    int count = availabilityService.updateById(availability);
    if (count > 0) {

      return R.success();
    } else {
      return R.fail();
    }
  }
}

//  @PutMapping("/editAvailability")
//  @ResponseBody
//  public R<Boolean> editAvailability(Availability availability) {
//    int i = availabilityService.updateById(availability);
//    if (i > 0) {
//      return R.success();
//    } else {
//      return R.fail();
//    }
//  }
//}
