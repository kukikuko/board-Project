package com.project.board.boardProject.controller;

import com.project.board.boardProject.dto.UserDto;
import com.project.board.boardProject.ex.LoginUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model, @LoginUser UserDto.Response user) {

        if (user != null) {
            model.addAttribute("user", user);
        }

        return "layout/common/home";
    }

}
