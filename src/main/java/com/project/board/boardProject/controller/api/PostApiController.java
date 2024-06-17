package com.project.board.boardProject.controller.api;

import com.project.board.boardProject.dto.PostDto;
import com.project.board.boardProject.dto.UserDto;
import com.project.board.boardProject.ex.LoginUser;
import com.project.board.boardProject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;

    @PostMapping("/post")
    public ResponseEntity save(@RequestBody PostDto.Request dto, @LoginUser UserDto.Response user) {
        return ResponseEntity.ok(postService.save(dto, user.getNickname()));
    }

    @PutMapping("/post/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody PostDto.Request dto) {
        postService.update(id, dto);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.ok(id);
    }

}
