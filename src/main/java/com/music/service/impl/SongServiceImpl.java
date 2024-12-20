package com.music.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.music.entity.Song;
import com.music.mapper.SongMapper;
import com.music.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {

    private final SongMapper songMapper;

    @Override
    public IPage<Song> getSongList(int page, int size) {
        return songMapper.selectPage(new Page<>(page, size), null);
    }

    @Override
    public Song getSongById(Long id) {
        return songMapper.selectById(id);
    }

    @Override
    public void addSong(Song song) {
        songMapper.insert(song);
    }

    @Override
    public void updateSong(Song song) {
        songMapper.updateById(song);
    }

    @Override
    public void deleteSong(Long id) {
        songMapper.deleteById(id);
    }
} 