package com.music.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.music.entity.Song;

public interface SearchService {
    IPage<Song> searchSongs(String keyword, int page, int size);
} 