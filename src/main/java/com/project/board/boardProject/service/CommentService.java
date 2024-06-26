package com.project.board.boardProject.service;

import com.project.board.boardProject.dto.CommentDto;
import com.project.board.boardProject.entity.Comment;
import com.project.board.boardProject.entity.Post;
import com.project.board.boardProject.entity.User;
import com.project.board.boardProject.repository.CommentRepository;
import com.project.board.boardProject.repository.PostRepository;
import com.project.board.boardProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long commentSave(String nickname, Long postId, CommentDto.Request dto) {
        User user = userRepository.findByNickname(nickname);
        Post post = postRepository.findById(postId).orElseThrow(()-> new IllegalArgumentException("댓글 쓰기 실패" + postId));

        dto.setUser(user);
        dto.setPost(post);

        Comment comment = dto.toEntity();
        commentRepository.save(comment);

        return comment.getId();
    }

    @Transactional
    public void commentUpdate(Long postId, Long commentId, CommentDto.Request dto) {
        Comment comment = commentRepository.findByPostIdAndId(postId, commentId)
                .orElseThrow(()-> new IllegalArgumentException("해당 댓글이 존재하지 않습니다." + commentId));

        comment.update(dto.getComment());
    }

    @Transactional
    public void commentDelete(Long postId, Long commentId) {
         Comment comment = commentRepository.findByPostIdAndId(postId, commentId)
                 .orElseThrow(()-> new IllegalArgumentException("해당 댓글이 존재하지 않습니다." + commentId));
         commentRepository.delete(comment);
    }

}

