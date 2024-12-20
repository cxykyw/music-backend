package com.music.dto;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("注册请求")
@Data
public class RegisterDTO {
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("昵称")
    private String nickname;
    @ApiModelProperty(value = "头像URL", required = false)
    private String avatar;
} 