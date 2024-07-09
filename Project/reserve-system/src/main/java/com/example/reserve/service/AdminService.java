package com.example.reserve.service;

import com.example.reserve.dto.LoginDTO;
import com.example.reserve.entity.Admin;

public interface AdminService {

  Admin login(LoginDTO loginDTO);

  int editPswdByAdmin(Admin admin);
}
