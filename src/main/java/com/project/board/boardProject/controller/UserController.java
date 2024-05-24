package com.project.board.boardProject.controller;

import com.project.board.boardProject.dto.MsgDto;
import com.project.board.boardProject.dto.UserDto;
import com.project.board.boardProject.service.MessageService;
import com.project.board.boardProject.service.UserService;
import com.project.board.boardProject.validator.CheckEmailValidator;
import com.project.board.boardProject.validator.CheckNicknameValidator;
import com.project.board.boardProject.validator.CheckUsernameValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final CheckEmailValidator checkEmailValidator;
    private final CheckNicknameValidator checkNicknameValidator;
    private final CheckUsernameValidator checkUsernameValidator;

    @InitBinder
    public void validatorBinder(WebDataBinder dataBinder) {
        dataBinder.addValidators(checkEmailValidator);
        dataBinder.addValidators(checkNicknameValidator);
        dataBinder.addValidators(checkUsernameValidator);
    }

    @GetMapping("/auth/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("auth/join")
    public String join(Model model) {
        model.addAttribute("userDto", new UserDto.Request());
        return "auth/join";
    }

    @PostMapping("auth/join")
    public String join(@Valid UserDto.Request userDto, Errors errors, Model model) {

        if(errors.hasErrors()) {
            model.addAttribute("userDto", userDto);
            Map<String, String> validatorResult = userService.validateHandling(errors);

            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            return "auth/join";
        }

        userService.join(userDto);
        MsgDto msgDto = new MsgDto("회원가입 완료", "/post/list", RequestMethod.GET);

        return new MessageService().showAlert(msgDto, model);
    }

}
