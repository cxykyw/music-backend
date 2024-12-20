package com.music.service;

import com.music.dto.LoginDTO;
import com.music.dto.RegisterDTO;
import com.music.dto.UserUpdateDTO;
import com.music.entity.User;

import java.util.List;

public interface UserService {
    String login(LoginDTO loginDTO);
    User register(RegisterDTO registerDTO);
    User getCurrentUser(String username);
    void updateUserInfo(Long userId, UserUpdateDTO userUpdateDTO);
    void updateAvatar(Long userId, String avatarUrl);
    void changePassword(Long userId, String oldPassword, String newPassword);
    void follow(Long followerId, Long followedId);
    void unfollow(Long followerId, Long followedId);
    List<User> getFollowers(Long userId, int page, int size);
    List<User> getFollowing(Long userId, int page, int size);
    boolean isFollowing(Long followerId, Long followedId);
} 