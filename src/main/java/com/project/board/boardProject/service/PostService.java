package com.project.board.boardProject.service;

import com.project.board.boardProject.dto.PostDto;
import com.project.board.boardProject.entity.Post;
import com.project.board.boardProject.entity.User;
import com.project.board.boardProject.exception.DataNotFoundException;
import com.project.board.boardProject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;


    @Transactional(readOnly = true)
    public Page<Post> pageList(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Transactional
    public PostDto.Response findById(Long id) {
        Post posts = postRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Data Not Found"));
        return new PostDto.Response(posts);
    }

    @Transactional
    public Page<Post> findByTitleContaining(String keyword, Pageable pageable) {
        return postRepository.findByTitleContaining(keyword, pageable);
    }

    public void save(PostDto.Request postDto) {
        User user = userService.findById(1L);
        postDto.setUser(user);
        postDto.setWriter(user.getNickname());

        postRepository.save(postDto.toEntity());
    }

}

