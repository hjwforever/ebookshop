package com.aruoxi.ebookshop.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


/**
 * 角色Dto
 */
@Data
@Schema
public class RoleDto {
  private String roleName;
  private String note;

  public RoleDto(String roleName, String note) {
    this.roleName = roleName != null ? roleName : "ROLE_USER";
    this.note = note != null ? note : "";
  }
}
