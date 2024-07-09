package com.example.reserve.service;


import com.example.reserve.entity.Reserve;
import com.example.reserve.utils.PageBean;

import java.util.List;
import java.util.Map;

public interface ReserveService {

  PageBean<Reserve> queryPage(Map<String, Object> paramMap);

  int deleteReserve(List<Integer> ids);

  int addReserve(Reserve reserve);

  int updateById(Reserve reserve);

  int checkReserve(Reserve reserve);
}
