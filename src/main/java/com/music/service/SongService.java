package com.music.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.music.entity.Song;

public interface SongService {
    IPage<Song> getSongList(int page, int size);
    Song getSongById(Long id);
    void addSong(Song song);
    void updateSong(Song song);
    void deleteSong(Long id);
} 