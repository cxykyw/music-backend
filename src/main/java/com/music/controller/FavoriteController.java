package com.music.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.music.common.Result;
import com.music.entity.Song;
import com.music.service.FavoriteService;
import com.music.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "收藏管理")
@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final SecurityUtil securityUtil;

    @ApiOperation("获取用户收藏列表")
    @GetMapping
    public Result<IPage<Song>> getUserFavorites(
            @AuthenticationPrincipal UserDetails userDetails,
            @ApiParam("页码") @RequestParam(defaultValue = "1") int page,
            @ApiParam("每页数量") @RequestParam(defaultValue = "10") int size) {
        return Result.success(favoriteService.getUserFavorites(securityUtil.getCurrentUserId(), page, size));
    }

    @ApiOperation("添加收藏")
    @PostMapping("/{songId}")
    public Result<?> addFavorite(
            @AuthenticationPrincipal UserDetails userDetails,
            @ApiParam("歌曲ID") @PathVariable Long songId) {
        favoriteService.addFavorite(securityUtil.getCurrentUserId(), songId);
        return Result.success(null);
    }

    @ApiOperation("取消收藏")
    @DeleteMapping("/{songId}")
    public Result<?> removeFavorite(
            @AuthenticationPrincipal UserDetails userDetails,
            @ApiParam("歌曲ID") @PathVariable Long songId) {
        favoriteService.removeFavorite(securityUtil.getCurrentUserId(), songId);
        return Result.success(null);
    }

    @ApiOperation("检查收藏状态")
    @GetMapping("/{songId}/status")
    public Result<Boolean> checkFavoriteStatus(
            @AuthenticationPrincipal UserDetails userDetails,
            @ApiParam("歌曲ID") @PathVariable Long songId) {
        return Result.success(favoriteService.isFavorite(securityUtil.getCurrentUserId(), songId));
    }
} 