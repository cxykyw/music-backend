package com.music.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.music.common.Result;
import com.music.entity.Song;
import com.music.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 歌曲管理控制器
 */
@Api(tags = "歌曲管理")
@RestController
@RequestMapping("/api/songs")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @ApiOperation("获取歌曲列表")
    @GetMapping
    public Result<IPage<Song>> getSongList(
            @ApiParam("页码") @RequestParam(defaultValue = "1") int page,
            @ApiParam("每页数量") @RequestParam(defaultValue = "10") int size) {
        return Result.success(songService.getSongList(page, size));
    }

    @ApiOperation("获取歌曲详情")
    @GetMapping("/{id}")
    public Result<Song> getSongById(
            @ApiParam("歌曲ID") @PathVariable Long id) {
        return Result.success(songService.getSongById(id));
    }

    @ApiOperation("添加歌曲")
    @PostMapping
    public Result<?> addSong(
            @ApiParam("歌曲信息") @RequestBody Song song) {
        songService.addSong(song);
        return Result.success(null);
    }

    @ApiOperation("更新歌曲")
    @PutMapping("/{id}")
    public Result<?> updateSong(
            @ApiParam("歌曲ID") @PathVariable Long id,
            @ApiParam("歌曲信息") @RequestBody Song song) {
        song.setId(id);
        songService.updateSong(song);
        return Result.success(null);
    }

    @ApiOperation("删除歌曲")
    @DeleteMapping("/{id}")
    public Result<?> deleteSong(
            @ApiParam("歌曲ID") @PathVariable Long id) {
        songService.deleteSong(id);
        return Result.success(null);
    }
} 