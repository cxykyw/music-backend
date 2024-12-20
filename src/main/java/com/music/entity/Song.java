package com.music.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("song")
public class Song {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String artist;
    private String album;
    private Integer duration;
    private String url;
    private String cover;
    private String lyric;
} 