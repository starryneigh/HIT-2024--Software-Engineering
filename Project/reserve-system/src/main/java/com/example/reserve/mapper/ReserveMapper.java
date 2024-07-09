package com.example.reserve.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.reserve.entity.Reserve;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReserveMapper extends BaseMapper<Reserve> {
  List<Reserve> queryList(Map<String, Object> paramMap);

  Integer queryCount(Map<String, Object> paramMap);

  List<Reserve> getReservesByStudentId(Integer id);

  List<Reserve> getReservesByAvailabilityId(Integer id);

  int checkReserve(Reserve reserve);
}
