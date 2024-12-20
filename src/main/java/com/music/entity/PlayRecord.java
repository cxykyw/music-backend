package com.music.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("play_record")
public class PlayRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long songId;
    private LocalDateTime playTime;
} 