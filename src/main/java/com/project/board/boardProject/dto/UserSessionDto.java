package com.project.board.boardProject.dto;

import com.project.board.boardProject.entity.Role;
import com.project.board.boardProject.entity.User;
import lombok.Getter;

@Getter
public class UserSessionDto {
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private Role role;
    private String modifiedDate;

    public UserSessionDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.modifiedDate = user.getModifiedDate();
    }

}
