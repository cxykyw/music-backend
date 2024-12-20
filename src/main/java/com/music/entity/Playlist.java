package com.music.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("playlist")
public class Playlist {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Long userId;
    private String cover;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 