package com.project.board.boardProject.service;

import com.project.board.boardProject.dto.UserDto;
import com.project.board.boardProject.entity.User;
import com.project.board.boardProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Long join(UserDto userDto) {
        userDto.setPassword(encoder.encode(userDto.getPassword()));
        return userRepository.save(userDto.toEntity()).getId();
    }

}


