package com.music.dto;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("登录请求")
@Data
public class LoginDTO {
    @ApiModelProperty(value = "用户名", required = true, example = "admin")
    private String username;
    @ApiModelProperty(value = "密码", required = true, example = "123456")
    private String password;
} 