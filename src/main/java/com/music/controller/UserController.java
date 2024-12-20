package com.music.controller;

import com.music.common.Result;
import com.music.dto.UserUpdateDTO;
import com.music.entity.User;
import com.music.service.UserService;
import com.music.util.SecurityUtil;
import com.music.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 用户管理控制器
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SecurityUtil securityUtil;
    private final FileUtil fileUtil;

    @ApiOperation("获取当前用户信息")
    @GetMapping("/me")
    public Result<User> getCurrentUser() {
        return Result.success(securityUtil.getCurrentUser());
    }

    @ApiOperation("更新用户信息")
    @PutMapping("/me")
    public Result<?> updateUserInfo(
            @ApiParam("用户信息") @RequestBody UserUpdateDTO userUpdateDTO) {
        userService.updateUserInfo(securityUtil.getCurrentUserId(), userUpdateDTO);
        return Result.success(null);
    }

    @ApiOperation("更新用户头像")
    @PostMapping("/me/avatar")
    public Result<?> updateAvatar(
            @ApiParam("头像文件") @RequestParam("file") MultipartFile file) {
        try {
            String avatarUrl = fileUtil.uploadFile(file, "avatars");
            userService.updateAvatar(securityUtil.getCurrentUserId(), avatarUrl);
            return Result.success(null);
        } catch (IOException e) {
            return Result.error("文件上传失败：" + e.getMessage());
        }
    }

    @ApiOperation("修改密码")
    @PutMapping("/me/password")
    public Result<?> changePassword(
            @ApiParam("密码信息") @RequestBody UserUpdateDTO userUpdateDTO) {
        userService.changePassword(
            securityUtil.getCurrentUserId(),
            userUpdateDTO.getOldPassword(),
            userUpdateDTO.getNewPassword()
        );
        return Result.success(null);
    }

    @ApiOperation("关注用户")
    @PostMapping("/follow/{userId}")
    public Result<?> follow(
            @ApiParam("被关注用户ID") @PathVariable Long userId) {
        userService.follow(securityUtil.getCurrentUserId(), userId);
        return Result.success(null);
    }

    @ApiOperation("取消关注")
    @DeleteMapping("/follow/{userId}")
    public Result<?> unfollow(
            @ApiParam("取消关注用户ID") @PathVariable Long userId) {
        userService.unfollow(securityUtil.getCurrentUserId(), userId);
        return Result.success(null);
    }

    @ApiOperation("获取用户粉丝列表")
    @GetMapping("/{userId}/followers")
    public Result<List<User>> getFollowers(
            @ApiParam("用户ID") @PathVariable Long userId,
            @ApiParam("页码") @RequestParam(defaultValue = "1") int page,
            @ApiParam("每页数量") @RequestParam(defaultValue = "10") int size) {
        return Result.success(userService.getFollowers(userId, page, size));
    }

    @ApiOperation("获取用户关注列表")
    @GetMapping("/{userId}/following")
    public Result<List<User>> getFollowing(
            @ApiParam("用户ID") @PathVariable Long userId,
            @ApiParam("页码") @RequestParam(defaultValue = "1") int page,
            @ApiParam("每页数量") @RequestParam(defaultValue = "10") int size) {
        return Result.success(userService.getFollowing(userId, page, size));
    }

    @ApiOperation("检查关注状态")
    @GetMapping("/{userId}/following/status")
    public Result<Boolean> checkFollowStatus(
            @ApiParam("目标用户ID") @PathVariable Long userId) {
        return Result.success(userService.isFollowing(securityUtil.getCurrentUserId(), userId));
    }
} 