package com.aruoxi.ebookshop.service.impl;

import com.aruoxi.ebookshop.controller.dto.RoleDto;
import com.aruoxi.ebookshop.domain.Role;
import com.aruoxi.ebookshop.repository.RoleRepository;
import com.aruoxi.ebookshop.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

  @Resource
  private RoleRepository roleRepository;

  @Override
  public Role save(RoleDto roleDto) {
    String roleName = translateRoleName(roleDto.getRoleName());
    if (roleRepository.findByName(roleName) != null) {
      return null;
    }
    return roleRepository.save(new Role(roleName,roleDto.getNote()));
  }

  @Override
  public void deleteById(Long id) {
    roleRepository.deleteById(id);
  }

  @Override
  public Role findById(Long id) {
    return roleRepository.findById(id).orElse(null);
  }

  @Override
  public Role findByName(String name) {
    return roleRepository.findByName(translateRoleName(name)).orElse(null);
  }

  @Override
  public List<Role> findAll() {
    return roleRepository.findAll();
  }

  public String translateRoleName(String name) {
    name = name.trim().toUpperCase();
    return name.startsWith("ROLE_") ? name : "ROLE_" + name;
  }
}
