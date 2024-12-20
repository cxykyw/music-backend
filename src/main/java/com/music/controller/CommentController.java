package com.music.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.music.common.Result;
import com.music.entity.Comment;
import com.music.service.CommentService;
import com.music.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "评论管理")
@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final SecurityUtil securityUtil;

    @ApiOperation("获取歌曲评论列表")
    @GetMapping("/song/{songId}")
    public Result<IPage<Comment>> getSongComments(
            @ApiParam("歌曲ID") @PathVariable Long songId,
            @ApiParam("页码") @RequestParam(defaultValue = "1") int page,
            @ApiParam("每页数量") @RequestParam(defaultValue = "10") int size) {
        return Result.success(commentService.getSongComments(songId, page, size));
    }

    @ApiOperation("添加评论")
    @PostMapping
    public Result<?> addComment(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody Comment comment) {
        comment.setUserId(securityUtil.getCurrentUserId());
        commentService.addComment(comment);
        return Result.success(null);
    }

    @ApiOperation("删除评论")
    @DeleteMapping("/{id}")
    public Result<?> deleteComment(
            @AuthenticationPrincipal UserDetails userDetails,
            @ApiParam("评论ID") @PathVariable Long id) {
        commentService.deleteComment(id, securityUtil.getCurrentUserId());
        return Result.success(null);
    }
} 