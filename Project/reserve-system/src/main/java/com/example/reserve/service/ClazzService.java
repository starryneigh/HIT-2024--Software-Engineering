package com.example.reserve.service;

import com.example.reserve.entity.Clazz;
import com.example.reserve.utils.PageBean;

import java.util.List;
import java.util.Map;

public interface ClazzService {

  PageBean<Clazz> queryPage(Map<String, Object> paramMap);

  int deleteClazz(List<Integer> ids);

  int editClazz(Clazz clazz);

  int add(Clazz clazz);
}
