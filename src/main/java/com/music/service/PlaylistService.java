package com.music.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.music.entity.Playlist;
import com.music.entity.Song;

import java.util.List;

public interface PlaylistService {
    IPage<Playlist> getUserPlaylists(Long userId, int page, int size);
    Playlist getPlaylistById(Long id);
    void createPlaylist(Playlist playlist);
    void updatePlaylist(Playlist playlist);
    void deletePlaylist(Long id);
    void addSongToPlaylist(Long playlistId, Long songId);
    void removeSongFromPlaylist(Long playlistId, Long songId);
    List<Song> getPlaylistSongs(Long playlistId);
} 