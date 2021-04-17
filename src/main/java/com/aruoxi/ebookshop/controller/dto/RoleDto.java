package com.aruoxi.ebookshop.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;


@Data
@Schema(description = "角色信息Dto")
@Tag(name = "DTO")
public class RoleDto {
  @Schema(description = "角色名", required = true)
  private String roleName;

  @Schema(description = "备注")
  private String note;

  public RoleDto(String roleName, String note) {
    this.roleName = roleName != null ? roleName : "ROLE_USER";
    this.note = note != null ? note : "";
  }
}
