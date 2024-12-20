package com.music.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.music.entity.Song;

public interface PlayRecordService {
    void recordPlay(Long userId, Long songId);
    IPage<Song> getUserRecentPlays(Long userId, int page, int size);
} 