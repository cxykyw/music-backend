package com.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.music.entity.Song;
import com.music.mapper.SongMapper;
import com.music.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final SongMapper songMapper;

    @Override
    public IPage<Song> searchSongs(String keyword, int page, int size) {
        return songMapper.selectPage(
            new Page<>(page, size),
            new QueryWrapper<Song>()
                .like("name", keyword)
                .or()
                .like("artist", keyword)
                .or()
                .like("album", keyword)
                .orderByDesc("create_time")
        );
    }
} 