package com.example.reserve.service;

import com.example.reserve.entity.Availability;
import com.example.reserve.utils.PageBean;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AvailabilityService {
  PageBean<Availability> queryPage(Map<String, Object> paramMap);

  int deleteAvailability(List<Integer> ids);

  int addAvailability(Availability availability);

  int updateById(Availability availability);

  List<Integer> queryIdsByTeacherId(Integer id);

  List<Availability> getAvailabilitiesByIds(Set<Integer> availabilityIds);
}
