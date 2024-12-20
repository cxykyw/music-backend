package com.music.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.music.entity.Favorite;
import com.music.entity.Song;

public interface FavoriteService {
    IPage<Song> getUserFavorites(Long userId, int page, int size);
    void addFavorite(Long userId, Long songId);
    void removeFavorite(Long userId, Long songId);
    boolean isFavorite(Long userId, Long songId);
} 