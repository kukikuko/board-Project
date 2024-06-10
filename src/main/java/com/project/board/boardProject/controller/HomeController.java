package com.project.board.boardProject.controller;

import com.project.board.boardProject.dto.UserSessionDto;
import com.project.board.boardProject.vo.LoginUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model, @LoginUser UserSessionDto user) {

        if (user != null) {
            model.addAttribute("user", user);
        }

        return "layout/common/home";
    }

}
