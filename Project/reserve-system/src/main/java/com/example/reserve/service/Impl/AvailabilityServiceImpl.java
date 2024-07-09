package com.example.reserve.service.Impl;

import com.example.reserve.entity.Availability;
import com.example.reserve.entity.Reserve;
import com.example.reserve.mapper.AvailabilityMapper;
import com.example.reserve.mapper.ReserveMapper;
import com.example.reserve.service.AvailabilityService;
import com.example.reserve.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

  @Autowired
  private AvailabilityMapper availabilityMapper;
  @Autowired
  private ReserveMapper reserveMapper;

  @Override
  public PageBean<Availability> queryPage(Map<String, Object> paramMap) {
    PageBean<Availability> pageBean = new PageBean<>((Integer) paramMap.get("pageno"), (Integer) paramMap.get("pagesize"));
    Integer startIndex = pageBean.getStartIndex();
    paramMap.put("startIndex", startIndex);
    List<Availability> datas = availabilityMapper.queryList(paramMap);
    pageBean.setDatas(datas);

    Integer totalsize = availabilityMapper.queryCount(paramMap);
    pageBean.setTotalsize(totalsize);
    return pageBean;
  }

  @Override
  public int deleteAvailability(List<Integer> ids) {
    for (Integer id : ids) {
      System.out.println("id = " + id);
      List<Reserve> reserveList = reserveMapper.getReservesByAvailabilityId(id);
      System.out.println("reserveMapper.getReservesByAvailabilityId(id) = " + reserveList);
      if (!reserveList.isEmpty()) {
        System.out.println("可用时间下有预约，不能删除");
        return -1;
      }
    }
    return availabilityMapper.deleteBatchIds(ids);
  }

  @Override
  public int addAvailability(Availability availability) {
    return availabilityMapper.insert(availability);
  }

  @Override
  public int updateById(Availability availability) {
    return availabilityMapper.updateById(availability);
  }

  @Override
  public List<Integer> queryIdsByTeacherId(Integer id) {
    return availabilityMapper.queryIdsByTeacherId(id);
  }

  @Override
  public List<Availability> getAvailabilitiesByIds(Set<Integer> availabilityIds) {
    return availabilityMapper.getAvailabilitiesByIds(availabilityIds);
  }
}
