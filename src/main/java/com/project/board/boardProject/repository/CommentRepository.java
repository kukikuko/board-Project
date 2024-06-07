package com.project.board.boardProject.repository;

import com.project.board.boardProject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByPostIdAndId(Long postId, Long id);
}
