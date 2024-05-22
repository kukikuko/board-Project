package com.project.board.boardProject.controller;

import com.project.board.boardProject.dto.MsgDto;
import com.project.board.boardProject.dto.PostDto;
import com.project.board.boardProject.entity.Post;
import com.project.board.boardProject.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/post")
@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable) {
        Page<Post> postPage = postService.pageList(pageable);
        model.addAttribute("posts", postPage.getContent());
        model.addAttribute("page", postPage);
        return "post/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Long id) {

        PostDto.Response post = postService.findById(id);
        model.addAttribute("post", post);

        return "post/detail";
    }

    @GetMapping("/search")
    public String search(Model model, String keyword, @PageableDefault(sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable) {

        Page<Post> postPage = postService.findByTitleContaining(keyword, pageable);
        model.addAttribute("keyword", keyword);
        model.addAttribute("posts", postPage.getContent());
        model.addAttribute("page", postPage);

        return "post/search";
    }

    @GetMapping("/write")
    public String save() {
        return "post/write";
    }

    @PostMapping("/write")
    public String save(PostDto.Request postDto, Model model) {
        postService.save(postDto);
        MsgDto msgDto = new MsgDto("글쓰기 완료", "/post/list", RequestMethod.GET);

        return showAlert(msgDto, model);
    }

    private String showAlert(final MsgDto msgDto, Model model) {
        model.addAttribute("msg", msgDto);
        return "common/alert";
    }

}
