package com.project.board.boardProject.validator;

import com.project.board.boardProject.dto.UserDto;
import com.project.board.boardProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckEmailValidator extends AbstractValidator<UserDto.Request> {

    private final UserRepository repository;

    @Override
    protected void doValidate(UserDto.Request userDto, Errors errors) {
        if(repository.existsByEmail(userDto.toEntity().getEmail())) {
            errors.rejectValue("email", "이메일 중복 오류", "이미 존재하는 이메일입니다.");
        }
    }
}
