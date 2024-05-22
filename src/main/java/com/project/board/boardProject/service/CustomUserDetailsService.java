package com.project.board.boardProject.service;

import com.project.board.boardProject.dto.UserDto;
import com.project.board.boardProject.dto.UserSessionDto;
import com.project.board.boardProject.entity.User;
import com.project.board.boardProject.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final HttpSession httpSession;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        httpSession.setAttribute("user", new UserSessionDto(user));

        return new CustomerDetails(user);
    }
}
