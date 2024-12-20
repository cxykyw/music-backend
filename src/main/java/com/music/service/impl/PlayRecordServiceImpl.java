package com.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.music.entity.PlayRecord;
import com.music.entity.Song;
import com.music.mapper.PlayRecordMapper;
import com.music.mapper.SongMapper;
import com.music.service.PlayRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayRecordServiceImpl implements PlayRecordService {

    private final PlayRecordMapper playRecordMapper;
    private final SongMapper songMapper;

    @Override
    public void recordPlay(Long userId, Long songId) {
        PlayRecord record = new PlayRecord();
        record.setUserId(userId);
        record.setSongId(songId);
        record.setPlayTime(LocalDateTime.now());
        playRecordMapper.insert(record);
    }

    @Override
    public IPage<Song> getUserRecentPlays(Long userId, int page, int size) {
        Page<PlayRecord> recordPage = playRecordMapper.selectPage(
            new Page<>(page, size),
            new QueryWrapper<PlayRecord>()
                .eq("user_id", userId)
                .orderByDesc("play_time")
        );

        List<Song> songs = recordPage.getRecords().stream()
            .map(r -> songMapper.selectById(r.getSongId()))
            .collect(Collectors.toList());

        Page<Song> songPage = new Page<>(recordPage.getCurrent(), recordPage.getSize(), recordPage.getTotal());
        songPage.setRecords(songs);
        return songPage;
    }
} 