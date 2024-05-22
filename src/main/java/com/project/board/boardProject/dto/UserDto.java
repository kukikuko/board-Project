package com.project.board.boardProject.dto;

import com.project.board.boardProject.entity.Role;
import com.project.board.boardProject.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String username;
    private String password;
    private String nickname;
    private String email;
    private Role role;

    /* DTO -> Entity */
    public User toEntity() {
        return User.builder()
               .username(this.username)
               .password(this.password)
               .nickname(this.nickname)
               .email(this.email)
               .role(Role.USER)
               .build();
    }

}
