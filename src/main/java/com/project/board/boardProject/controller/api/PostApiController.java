package com.project.board.boardProject.controller.api;

import com.project.board.boardProject.dto.PostDto;
import com.project.board.boardProject.dto.UserSessionDto;
import com.project.board.boardProject.entity.Post;
import com.project.board.boardProject.service.PostService;
import com.project.board.boardProject.vo.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api")
@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity save(@RequestBody PostDto.Request request, @LoginUser UserSessionDto userSessionDto) {
        return ResponseEntity.ok(postService.save(request, userSessionDto.getNickname()));
    }

}
