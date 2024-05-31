package com.project.board.boardProject.service;

import com.project.board.boardProject.dto.UserDto;
import com.project.board.boardProject.entity.User;
import com.project.board.boardProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public User findById(Long id) {
        Optional<User> users = userRepository.findById(id);
        return users.orElse(null);
    }

    @Transactional
    public Long join(UserDto.Request userDto) {
        userDto.setPassword(encoder.encode(userDto.getPassword()));
        return userRepository.save(userDto.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        for(FieldError error : errors.getFieldErrors()) {
            String key = String.format("valid_%s", error.getField());
            validatorResult.put(key, error.getDefaultMessage());
        }

        return validatorResult;
    }

    @Transactional
    public void modify(UserDto.Request userDto) {
        User user = userRepository.findById(userDto.toEntity().getId()).orElseThrow(() ->
                new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        String encPwd = encoder.encode(userDto.getPassword());
        user.modify(userDto.getNickname(), encPwd);

    }

}


