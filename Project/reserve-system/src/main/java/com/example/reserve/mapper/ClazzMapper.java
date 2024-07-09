package com.example.reserve.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.reserve.entity.Clazz;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ClazzMapper extends BaseMapper<Clazz> {
  List<Clazz> queryList(Map<String, Object> paramMap);

  Integer queryCount(Map<String, Object> paramMap);

  int deleteClazz(List<Integer> ids);
}
