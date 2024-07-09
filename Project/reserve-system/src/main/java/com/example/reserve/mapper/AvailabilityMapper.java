package com.example.reserve.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.reserve.entity.Availability;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
public interface AvailabilityMapper extends BaseMapper<Availability> {
  List<Availability> queryList(Map<String, Object> paramMap);

  Integer queryCount(Map<String, Object> paramMap);

  List<Integer> queryIdsByTeacherId(Integer id);

  List<Availability> getAvailabilitiesByIds(Set<Integer> availabilityIds);

  List<Availability> getAvailabilitiesByTeacherId(Integer id);
}
