package com.project.board.boardProject.controller.api;

import com.project.board.boardProject.dto.CommentDto;
import com.project.board.boardProject.dto.UserSessionDto;
import com.project.board.boardProject.service.CommentService;
import com.project.board.boardProject.vo.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/post/{postId}/comment")
    public ResponseEntity commentSave(@PathVariable Long postId, @RequestBody CommentDto.Request dto
                    , @LoginUser UserSessionDto user) {
        return ResponseEntity.ok(commentService.commentSave(user.getNickname(), postId, dto));
    }
}