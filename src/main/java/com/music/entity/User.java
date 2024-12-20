package com.music.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("用户实体")
@Data
@TableName("user")
public class User {
    @ApiModelProperty("用户ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty(value = "密码", hidden = true)
    private String password;
    @ApiModelProperty("昵称")
    private String nickname;
    private String avatar;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 