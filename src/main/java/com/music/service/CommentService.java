package com.music.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.music.entity.Comment;

public interface CommentService {
    IPage<Comment> getSongComments(Long songId, int page, int size);
    void addComment(Comment comment);
    void deleteComment(Long id, Long userId);
    Comment getCommentById(Long id);
} 