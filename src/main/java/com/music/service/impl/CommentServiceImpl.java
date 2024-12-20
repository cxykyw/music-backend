package com.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.music.entity.Comment;
import com.music.mapper.CommentMapper;
import com.music.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    @Override
    public IPage<Comment> getSongComments(Long songId, int page, int size) {
        return commentMapper.selectPage(
            new Page<>(page, size),
            new QueryWrapper<Comment>()
                .eq("song_id", songId)
                .orderByDesc("create_time")
        );
    }

    @Override
    public void addComment(Comment comment) {
        comment.setCreateTime(LocalDateTime.now());
        commentMapper.insert(comment);
    }

    @Override
    public void deleteComment(Long id, Long userId) {
        commentMapper.delete(
            new QueryWrapper<Comment>()
                .eq("id", id)
                .eq("user_id", userId)
        );
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentMapper.selectById(id);
    }
} 