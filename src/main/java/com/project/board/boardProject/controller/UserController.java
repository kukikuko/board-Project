package com.project.board.boardProject.controller;

import com.project.board.boardProject.dto.MsgDto;
import com.project.board.boardProject.dto.UserDto;
import com.project.board.boardProject.dto.UserSessionDto;
import com.project.board.boardProject.service.MessageService;
import com.project.board.boardProject.service.UserService;
import com.project.board.boardProject.validator.CheckEmailValidator;
import com.project.board.boardProject.validator.CheckNicknameValidator;
import com.project.board.boardProject.validator.CheckUsernameValidator;
import com.project.board.boardProject.vo.LoginUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

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
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception, Model model) {

        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "auth/login";
    }

    @GetMapping("/auth/join")
    public String join(Model model) {
        model.addAttribute("userDto", new UserDto.Request());
        return "auth/join";
    }

    @PostMapping("/auth/join")
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

    @GetMapping("/user/modify")
    public String modify(@LoginUser UserSessionDto user, Model model) {
        if(user != null) {
            model.addAttribute("user", user);
            model.addAttribute("nickName", user.getNickname());
        }
        return "auth/modify";
    }

    @GetMapping("/auth/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {new SecurityContextLogoutHandler().logout(request, response, auth);
            }
        return "redirect:/post/list";}
}
