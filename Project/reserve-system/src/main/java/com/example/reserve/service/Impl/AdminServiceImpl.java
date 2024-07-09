package com.example.reserve.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.reserve.dto.LoginDTO;
import com.example.reserve.entity.Admin;
import com.example.reserve.mapper.AdminMapper;
import com.example.reserve.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

  @Autowired
  private AdminMapper adminMapper;


  @Override
  public Admin login(LoginDTO loginDTO) {
    LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<Admin>()
            .eq(Admin::getUsername, loginDTO.getUsername())
            .eq(Admin::getPassword, loginDTO.getPassword());
    return adminMapper.selectOne(wrapper);
  }

  @Override
  public int editPswdByAdmin(Admin admin) {
    return adminMapper.updateById(admin);
  }

}
