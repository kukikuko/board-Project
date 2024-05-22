package com.project.board.boardProject.dto;

import com.project.board.boardProject.entity.Role;
import com.project.board.boardProject.entity.User;
import lombok.Getter;

@Getter
public class UserSessionDto {
    private String username;
    private String password;
    private String nickname;
    private String email;
    private Role role;

    public UserSessionDto(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.role = user.getRole();
    }

}
