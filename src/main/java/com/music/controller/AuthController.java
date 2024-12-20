package com.music.controller;

import com.music.common.Result;
import com.music.dto.LoginDTO;
import com.music.dto.RegisterDTO;
import com.music.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "认证管理")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final UserService userService;
    
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result<String> login(
            @ApiParam("登录信息") @RequestBody LoginDTO loginDTO) {
        String token = userService.login(loginDTO);
        return Result.success(token);
    }
    
    @ApiOperation("用户注册")
    @PostMapping("/register")
    public Result<?> register(
            @ApiParam("注册信息") @RequestBody RegisterDTO registerDTO) {
        userService.register(registerDTO);
        return Result.success(null);
    }
} 