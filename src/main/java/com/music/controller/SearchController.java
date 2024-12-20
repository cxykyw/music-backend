package com.music.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.music.common.Result;
import com.music.entity.Song;
import com.music.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "搜索管理")
@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @ApiOperation("搜索歌曲")
    @GetMapping
    public Result<IPage<Song>> search(
            @ApiParam("搜索关键词") @RequestParam String keyword,
            @ApiParam("页码") @RequestParam(defaultValue = "1") int page,
            @ApiParam("每页数量") @RequestParam(defaultValue = "10") int size) {
        return Result.success(searchService.searchSongs(keyword, page, size));
    }
} 