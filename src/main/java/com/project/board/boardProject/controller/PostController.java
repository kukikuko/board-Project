package com.project.board.boardProject.controller;

import com.project.board.boardProject.dto.*;
import com.project.board.boardProject.entity.Post;
import com.project.board.boardProject.service.MessageService;
import com.project.board.boardProject.service.PostService;
import com.project.board.boardProject.vo.LoginUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/post")
@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable, @LoginUser UserSessionDto user) {

        if (user != null) {
            model.addAttribute("user", user);
        }

        Page<Post> postPage = postService.pageList(pageable);
        model.addAttribute("posts", postPage.getContent());
        model.addAttribute("page", postPage);
        return "post/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, @LoginUser UserSessionDto user, Model model) {

        PostDto.Response dto = postService.findById(id);
        List<CommentDto.Response> comments = dto.getComments();

        log.info("comments : {}",comments);

        if(comments != null && !comments.isEmpty()) {
            model.addAttribute("comments", comments);
        }

        if (user != null) {
            model.addAttribute("user", user);

            if(dto.getUserId().equals(user.getId())) {
                model.addAttribute("writer", true);
            }
        }

        postService.updateView(id);
        model.addAttribute("post", dto);

        return "post/detail";
    }

    @GetMapping("/search")
    public String search(Model model, String keyword, @PageableDefault(sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable, @LoginUser UserSessionDto user) {

        if (user != null) {
            model.addAttribute("user", user);
        }

        Page<Post> postPage = postService.findByTitleContaining(keyword, pageable);
        model.addAttribute("keyword", keyword);
        model.addAttribute("posts", postPage.getContent());
        model.addAttribute("page", postPage);

        return "post/search";
    }

    @GetMapping("/write")
    public String save(Model model, @LoginUser UserSessionDto user) {

        if (user != null) {
            model.addAttribute("user", user);
        }

        return "post/write";
    }

    @PostMapping("/write")
    public String save(PostDto.Request postDto, Model model, @LoginUser UserSessionDto user) {

        if (user != null) {
            model.addAttribute("user", user);
        }

        postService.save(postDto, user.getNickname());
        MsgDto msgDto = new MsgDto("글쓰기 완료", "/post/list", RequestMethod.GET);

        return new MessageService().showAlert(msgDto, model);
    }

}
