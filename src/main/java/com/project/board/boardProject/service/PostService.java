package com.project.board.boardProject.service;

import com.project.board.boardProject.dto.PostDto;
import com.project.board.boardProject.entity.Post;
import com.project.board.boardProject.entity.User;
import com.project.board.boardProject.exception.DataNotFoundException;
import com.project.board.boardProject.repository.PostRepository;
import com.project.board.boardProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;


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

    @Transactional
    public int updateView(Long id) {
        return postRepository.updateView(id);
    }

    public Long save(PostDto.Request postDto, String nickname) {
        User user = userRepository.findByNickname(nickname);
        postDto.setUser(user);
        postDto.setWriter(user.getNickname());

        log.info("postDto {}", postDto);

        Post post = postDto.toEntity();
        postRepository.save(post);
        return post.getId();
    }



}

