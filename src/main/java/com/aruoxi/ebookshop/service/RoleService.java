package com.aruoxi.ebookshop.service;


import com.aruoxi.ebookshop.controller.dto.RoleDto;
import com.aruoxi.ebookshop.domain.Role;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoleService {
   Role save(RoleDto roleDto);
   void deleteById(Long id);
   Role findById(Long id);
   Role findByName(String name);
   List<Role> findAll();
}
