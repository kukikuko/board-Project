package com.project.board.boardProject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestMethod;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MsgDto {

    private String msg;
    private String url;
    private RequestMethod method;

}
