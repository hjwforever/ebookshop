package com.aruoxi.ebookshop.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;



@Tag(name = "用户实体类")
@Entity
@Table(name = "users",uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "用户id", required = true)
    private Long id;

    @Column(unique = true)
    @Schema(description = "用户名",example = "zhangsan", required = true)
    private String username;

    @Schema(description = "用户昵称",example = "张三")
    private String nickname;

    @Schema(description = "用户邮箱",example = "test@qq.com", required = true)
    private String email;

    @Schema(description = "用户密码", required = true)
    private String password;

    @Schema(description = "用户头像", defaultValue = "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif")
    private String avatar;

    @Schema(description = "用户简介")
    private String introduction;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    @Schema(description = "用户拥有的角色",example = "ROLE_USER", required = true)
    private Collection<Role> roles;

    public User() {
        this.avatar = "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif";
        this.introduction = "";
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.nickname = username;
        this.email = email;
        this.password = password;
        this.avatar = "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif";
        this.introduction = "";
    }

    public User(String username, String email, String password, Collection<Role> roles) {
        this(username, email, password);
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "{id:" + id +
                ", username:" + username +
                ", nickname:" + nickname +
                ", email:" + email +
                ", password:" + "*********" +
                ", roles:" + roles.stream().map(Role::getName) +
                "}";
    }
}
