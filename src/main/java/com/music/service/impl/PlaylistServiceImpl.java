package com.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.music.entity.Playlist;
import com.music.entity.PlaylistSong;
import com.music.entity.Song;
import com.music.mapper.PlaylistMapper;
import com.music.mapper.PlaylistSongMapper;
import com.music.mapper.SongMapper;
import com.music.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistMapper playlistMapper;
    private final PlaylistSongMapper playlistSongMapper;
    private final SongMapper songMapper;

    @Override
    public IPage<Playlist> getUserPlaylists(Long userId, int page, int size) {
        return playlistMapper.selectPage(
            new Page<>(page, size),
            new QueryWrapper<Playlist>().eq("user_id", userId)
        );
    }

    @Override
    public Playlist getPlaylistById(Long id) {
        return playlistMapper.selectById(id);
    }

    @Override
    public void createPlaylist(Playlist playlist) {
        playlistMapper.insert(playlist);
    }

    @Override
    public void updatePlaylist(Playlist playlist) {
        playlistMapper.updateById(playlist);
    }

    @Override
    @Transactional
    public void deletePlaylist(Long id) {
        playlistMapper.deleteById(id);
        playlistSongMapper.delete(new QueryWrapper<PlaylistSong>().eq("playlist_id", id));
    }

    @Override
    public void addSongToPlaylist(Long playlistId, Long songId) {
        PlaylistSong playlistSong = new PlaylistSong();
        playlistSong.setPlaylistId(playlistId);
        playlistSong.setSongId(songId);
        playlistSongMapper.insert(playlistSong);
    }

    @Override
    public void removeSongFromPlaylist(Long playlistId, Long songId) {
        playlistSongMapper.delete(
            new QueryWrapper<PlaylistSong>()
                .eq("playlist_id", playlistId)
                .eq("song_id", songId)
        );
    }

    @Override
    public List<Song> getPlaylistSongs(Long playlistId) {
        List<PlaylistSong> playlistSongs = playlistSongMapper.selectList(
            new QueryWrapper<PlaylistSong>().eq("playlist_id", playlistId)
        );
        
        return playlistSongs.stream()
            .map(ps -> songMapper.selectById(ps.getSongId()))
            .collect(Collectors.toList());
    }
} 