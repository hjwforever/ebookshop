package com.aruoxi.ebookshop.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;



@Tag(name = "用户实体类")
@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "角色id", required = true)
    private Long id;

    @Schema(description = "角色称呼", required = true)
    @Column(name = "role_name", unique = true)
    private String name;

    @Schema(description = "备注")
    private String note;

    public Role() {
    }

    public Role(String name) {
        this(name, "");
    }

    public Role(String name, String note) {
        this.name = name;
        this.note = note;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + 
                ", notes='" + note +
                '\'' +
                '}';
    }
}
