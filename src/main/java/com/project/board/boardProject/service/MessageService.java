package com.project.board.boardProject.service;

import com.project.board.boardProject.dto.MsgDto;
import org.springframework.ui.Model;

public class MessageService {

    public String showAlert(final MsgDto msgDto, Model model) {
        model.addAttribute("msg", msgDto);
        return "layout/common/alert";
    }
}
