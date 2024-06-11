package com.project.board.boardProject.controller.api;

import com.project.board.boardProject.dto.CommentDto;
import com.project.board.boardProject.dto.UserDto;
import com.project.board.boardProject.service.CommentService;
import com.project.board.boardProject.ex.LoginUser;
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
                    , @LoginUser UserDto.Response user) {
        return ResponseEntity.ok(commentService.commentSave(user.getNickname(), postId, dto));
    }

    @PutMapping("/post/{postId}/comment/{commentId}")
    public ResponseEntity<Long> commentUpdate(@PathVariable Long postId, @PathVariable Long commentId
                    , @RequestBody CommentDto.Request dto) {
        commentService.commentUpdate(postId, commentId, dto);
        return ResponseEntity.ok(commentId);
    }

    @DeleteMapping("post/{postId}/comment/{commentId}")
    public ResponseEntity<Long> commentDelete(@PathVariable Long postId, @PathVariable Long commentId) {
        commentService.commentDelete(postId, commentId);
        return ResponseEntity.ok(commentId);
    }
}
