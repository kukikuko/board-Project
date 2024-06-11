package com.project.board.boardProject.dto;

import com.project.board.boardProject.entity.Comment;
import com.project.board.boardProject.entity.Post;
import com.project.board.boardProject.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommentDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private Long id;
        private String comment;
        private String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        private String modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        private User user;
        private Post post;

        /* Dto -> Entity */
        public Comment toEntity() {
            return Comment.builder()
                    .id(id)
                    .comment(comment)
                    .createdDate(createdDate)
                    .modifiedDate(modifiedDate)
                    .user(user)
                    .post(post)
                    .build();
        }
    }

    @Getter
    public static class Response {
        private Long id;
        private String comment;
        private String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        private String modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        private String nickname;
        private Long postId;
        private Long userId;

        /* Entity -> Dto */
        public Response(Comment comment) {
            this.id = comment.getId();
            this.comment = comment.getComment();
            this.modifiedDate = comment.getModifiedDate();
            this.createdDate = comment.getCreatedDate();
            this.nickname = comment.getUser().getNickname();
            this.postId = comment.getPost().getId();
            this.userId = comment.getUser().getId();
        }
    }
}
