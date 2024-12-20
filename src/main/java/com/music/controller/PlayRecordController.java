package com.music.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.music.common.Result;
import com.music.entity.Song;
import com.music.service.PlayRecordService;
import com.music.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "播放记录管理")
@RestController
@RequestMapping("/api/play-records")
@RequiredArgsConstructor
public class PlayRecordController {

    private final PlayRecordService playRecordService;
    private final SecurityUtil securityUtil;

    @ApiOperation("记录播放")
    @PostMapping("/{songId}")
    public Result<?> recordPlay(@ApiParam("歌曲ID") @PathVariable Long songId) {
        playRecordService.recordPlay(securityUtil.getCurrentUserId(), songId);
        return Result.success(null);
    }

    @ApiOperation("获取最近播放")
    @GetMapping("/recent")
    public Result<IPage<Song>> getRecentPlays(
            @ApiParam("页码") @RequestParam(defaultValue = "1") int page,
            @ApiParam("每页数量") @RequestParam(defaultValue = "10") int size) {
        return Result.success(
            playRecordService.getUserRecentPlays(securityUtil.getCurrentUserId(), page, size)
        );
    }
} 