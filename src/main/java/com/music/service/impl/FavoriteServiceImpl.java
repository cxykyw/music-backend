package com.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.music.entity.Favorite;
import com.music.entity.Song;
import com.music.mapper.FavoriteMapper;
import com.music.mapper.SongMapper;
import com.music.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteMapper favoriteMapper;
    private final SongMapper songMapper;

    @Override
    public IPage<Song> getUserFavorites(Long userId, int page, int size) {
        Page<Favorite> favoritePage = favoriteMapper.selectPage(
            new Page<>(page, size),
            new QueryWrapper<Favorite>().eq("user_id", userId)
        );

        List<Song> songs = favoritePage.getRecords().stream()
            .map(f -> songMapper.selectById(f.getSongId()))
            .collect(Collectors.toList());

        Page<Song> songPage = new Page<>(favoritePage.getCurrent(), favoritePage.getSize(), favoritePage.getTotal());
        songPage.setRecords(songs);
        return songPage;
    }

    @Override
    public void addFavorite(Long userId, Long songId) {
        if (!isFavorite(userId, songId)) {
            Favorite favorite = new Favorite();
            favorite.setUserId(userId);
            favorite.setSongId(songId);
            favorite.setCreateTime(LocalDateTime.now());
            favoriteMapper.insert(favorite);
        }
    }

    @Override
    public void removeFavorite(Long userId, Long songId) {
        favoriteMapper.delete(
            new QueryWrapper<Favorite>()
                .eq("user_id", userId)
                .eq("song_id", songId)
        );
    }

    @Override
    public boolean isFavorite(Long userId, Long songId) {
        return favoriteMapper.selectCount(
            new QueryWrapper<Favorite>()
                .eq("user_id", userId)
                .eq("song_id", songId)
        ) > 0;
    }
} 