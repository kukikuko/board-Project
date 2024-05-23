package com.project.board.boardProject.controller;

import com.project.board.boardProject.dto.MsgDto;
import com.project.board.boardProject.dto.UserDto;
import com.project.board.boardProject.service.MessageService;
import com.project.board.boardProject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/auth/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("auth/join")
    public String join() {
        return "auth/join";
    }

    @PostMapping("auth/join")
    public String join(UserDto userDto, Model model) {

        Long join = userService.join(userDto);
        MsgDto msgDto = new MsgDto("회원가입 완료", "/post/list", RequestMethod.GET);
        return new MessageService().showAlert(msgDto, model);

    }

}
