package com.example.reserve.service.Impl;

import com.example.reserve.entity.Reserve;
import com.example.reserve.mapper.ReserveMapper;
import com.example.reserve.service.ReserveService;
import com.example.reserve.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReserveServiceImpl implements ReserveService {

  @Autowired
  private ReserveMapper reserveMapper;

  @Override
  public PageBean<Reserve> queryPage(Map<String, Object> paramMap) {
    PageBean<Reserve> pageBean = new PageBean<>((Integer) paramMap.get("pageno"), (Integer) paramMap.get("pagesize"));
    Integer startIndex = pageBean.getStartIndex();
    paramMap.put("startIndex", startIndex);
    List<Reserve> datas = reserveMapper.queryList(paramMap);
    pageBean.setDatas(datas);

    Integer totalsize = reserveMapper.queryCount(paramMap);
    pageBean.setTotalsize(totalsize);
    return pageBean;
  }

  @Override
  public int deleteReserve(List<Integer> ids) {
    return reserveMapper.deleteBatchIds(ids);
  }

  @Override
  public int addReserve(Reserve reserve) {
    return reserveMapper.insert(reserve);
  }

  @Override
  public int updateById(Reserve reserve) {
    return reserveMapper.updateById(reserve);
  }

  @Override
  public int checkReserve(Reserve reserve) {
    return reserveMapper.checkReserve(reserve);
  }
}
